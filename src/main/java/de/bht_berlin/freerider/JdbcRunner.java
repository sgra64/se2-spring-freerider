package de.bht_berlin.freerider;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;


/**
 * Class with factory method of a {@link CommandLineRunner} {@code Bean} object,
 * a pattern often chosen for entry points in <i>Spring Boot</i> applications.
 */
@Component
public class JdbcRunner {

    /**
     * Formatter for {@link LocalDateTime} date and time.
     */
    final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm");

    /**
     * Auto-wired variable initialized with the {@link JdbcTemplate} bean offering {@code JDBC} methods.
     */
    @Autowired
    JdbcTemplate jdbcTemplate;


    /**
     * Factory method of a {@link CommandLineRunner} {@code Bean} that creates an instance
     * of class {@link CommandLineRunner} and invokes the lambda {@code args -> { ... }}.
     * @param customerTableFormatter injected {@link JdbcTemplate} bean object retrieved
     *          from a factory method in class {@link TableFormatterFactory}.
     * @param customerReservationsTableFormatter injected {@link JdbcTemplate} bean object
     *          retrieved from a factory method in class {@link TableFormatterFactory}.
     * @return {@link CommandLineRunner} {@code Bean} at which the lambda expression
     *          {@code args -> { ... }} method is invoked.
     */
    @Bean
    public CommandLineRunner entry(
        TableFormatter customerTableFormatter,
        TableFormatter customerReservationsTableFormatter
    ) {
        return args -> {
            System.out.println(String.format("...customers --> %2d", queryRowCount("CUSTOMER")));
            System.out.println(String.format("....vehicles --> %2d", queryRowCount("VEHICLE")));
            System.out.println(String.format("reservations --> %2d", queryRowCount("RESERVATION")));
            // 
            customerTableFormatter.header();
            customerReservationsTableFormatter.header();

            /** 
             * Traditional, iterative JDBC-Query producing table using {@code customerTableFormatter}.
             * <pre>
             * +-----+-----------+-----------+---------------------+----------------+------------------+
             * | ID  | NAME      | FIRSTNAME | CONTACT             | STATUS         | STATUS_CHANGE    |
             * +-----+-----------+-----------+---------------------+----------------+------------------+
             * | 101 | Sommer    | Tina      | +49 030 22458 29425 | Active         | 07.10.2025 10:28 |
             * | 102 | Schulze   | Tim       | +49 171 2358124     | Active         | 28.12.2024 06:00 |
             * | 103 | Brinkmann | Tobias    | +49 030 662465724   | InRegistration | 28.11.2025 12:18 |
             * +-----+-----------+-----------+---------------------+----------------+------------------+
             * </pre>
             */
            jdbcTemplate.query(
                // query is sent to the database
                "SELECT * FROM CUSTOMER",
                    // database returns ResultSet as iterator 'rs'
                    (RowCallbackHandler) rs -> {
                        while (rs.next()) {
                            /*
                             * insert row into 'customerTableFormatter'-table from values extracted
                             * from {@link ResultSet} {@code 'rs'} passed by {@link RowCallbackHandler}
                             */
                            customerTableFormatter.row(
                                String.format("%s", rs.getObject("ID")),
                                rs.getString("NAME"),
                                rs.getString("FIRSTNAME"),
                                rs.getString("CONTACT"),
                                rs.getString("STATUS"),
                                rs.getTimestamp("STATUS_CHANGE").toLocalDateTime().format(dateTimeFormatter)
                            );
                        }
                    }
            );
            customerTableFormatter.footer().print(System.out);

            // /** 
            //  * JDBC-Query with row-mapper pattern without iterator
            //  */
            // customerTableFormatter.header();
            // List<String[]> resultSet = jdbcTemplate.query(
            //     // query is sent to the database
            //     "SELECT * FROM CUSTOMER",
            //         // row-mapper returns a mapped value for each row
            //         (rs, rowNum) -> new String[] {
            //             String.format("%s", rs.getObject("ID")),
            //             rs.getString("NAME"),
            //             rs.getString("FIRSTNAME"),
            //             rs.getString("CONTACT"),
            //             rs.getString("STATUS"),
            //             rs.getTimestamp("STATUS_CHANGE").toLocalDateTime().format(dateTimeFormatter)
            //         }
            // );
            // resultSet.forEach(mappedRow -> customerTableFormatter.row(mappedRow));
            // // 
            // customerTableFormatter.footer().print(System.out);
        };
    }

    private long queryRowCount(String table) {
        List<Long> counts = jdbcTemplate.query(
            String.format("SELECT COUNT(*) FROM %s", table),
                (rs, rowNum) -> rs.getLong(1)
            );
        return counts.size() > 0? counts.get(0) : 0L;
    }
}
