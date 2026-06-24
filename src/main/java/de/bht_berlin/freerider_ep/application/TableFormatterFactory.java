package de.bht_berlin.freerider_ep.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Class with {@code Bean} factory methods of {@link TableFormatter} bean objetcs.
 */
@Configuration
public class TableFormatterFactory {

    /**
     * Factory method of a {@link TableFormatter} for a {@code Customer} table.
     * <pre>
     * +-----+----------------+------------------+----------------------+----------------+------------------+
     * | ID  | NAME           | FIRSTNAME        | CONTACT              | STATUS         | STATUS_CHANGE    |
     * +-----+----------------+------------------+----------------------+----------------+------------------+
     * | 101 | Sommer         | Tina             | +49 030 22458 29425  | Active         | 07.10.2025 10:28 |
     * | 102 | Schulze        | Tim              | +49 171 2358124      | Active         | 28.12.2024 06:00 |
     * | 103 | Brinkmann      | Tobias           | +49 030 662465724    | InRegistration | 28.11.2025 12:18 |
     * +-----+----------------+------------------+----------------------+----------------+------------------+
     * </pre>
     * @return {@link TableFormatter} {@code Bean} that formats a {@code Customer} table.
     */
    @Bean
    TableFormatter customerTableFormatter() {
        return TableFormatter.builder()
            .columns("| ID | NAME | FIRSTNAME | CONTACT | STATUS | STATUS_CHANGE |")
            .widths(5, 16, 18, 22, 16, 18)  // column widths
            .alignments("L")                // column alignments
            .build();
    }

    /**
     * Factory method of a {@link TableFormatter} to format results from a <i>Join</i> query.
     * <pre>
     * +----------------------+------------------+----------------------+-----------+-----------+-----------+
     * | NAME                 | BEGIN            | END                  | MAKE      | MODEL     | STATUS    |
     * +----------------------+------------------+----------------------+-----------+-----------+-----------+
     * | Schulze, Tim         | 18.11.2025 08:00 | 20.11.2025 08:00     | Tesla     | Model S   | Booked    |
     * | Brinkmann, Tobias    | 17.11.2025 10:00 | 17.11.2025 06:00     | VW        | Golf      | Booked    |
     * | Schulze, Tim         | 14.11.2025 10:00 | 17.11.2025 04:30     | Tesla     | Model 3   | Cancelled |
     * | Schulze, Tim         | 16.11.2025 09:00 | 17.11.2025 09:00     | Mercedes  | EQS       | Inquired  |
     * | Schulze, Tim         | 15.11.2025 10:00 | 16.11.2025 08:00     | Tesla     | Model 3   | Booked    |
     * +----------------------+------------------+----------------------+-----------+-----------+-----------+
     * </pre>
     * @return {@link TableFormatter} {@code Bean} that formats results from a <i>Join</i> query.
     */
    @Bean
    TableFormatter customerReservationsTableFormatter() {
        return TableFormatter.builder()
            .columns("| NAME | BEGIN | END | MAKE | MODEL | STATUS |")
            .widths(22, 18, 22, 11, 11, 11)  // column widths
            .alignments("L")                // column alignments
            .build();
    }
}
