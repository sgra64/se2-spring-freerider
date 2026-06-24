package de.bht_berlin.freerider_ep.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import de.bht_berlin.freerider_ep.controller.CustomerRepository;
import de.bht_berlin.freerider_ep.controller.ReservationRepository;
import de.bht_berlin.freerider_ep.controller.VehicleRepository;
import de.bht_berlin.freerider_ep.model.Customer;


@SpringBootApplication(scanBasePackages = {"de.bht_berlin.freerider_ep"})
@EnableJpaRepositories(basePackages = {"de.bht_berlin.freerider_ep.controller"})
@EntityScan(basePackages = {"de.bht_berlin.freerider_ep.model"})
// 
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }

    @Bean
    public CommandLineRunner initDatabase(
        JdbcTemplate jdbcTemplate,
        CustomerRepository customerRepository,
        VehicleRepository vehicleRepository,
        ReservationRepository reservationRepository
    ) {
        return args -> {
            long customerCount = customerRepository.count();
            if (customerCount == 0) {
                // System.out.println("--> Datenbank ist leer. Generiere Beispieldaten...");
                // customerRepository.save(new Customer("Max Mustermann", "max@example.com"));
                // customerRepository.save(new Customer("Sven Schmidt", "sven@example.com"));
                // customerRepository.save(new Customer("Anna Müller", "anna@example.com"));
                // System.out.println("--> 3 Kunden erfolgreich angelegt.");
            } else {
                // 2. Falls bereits Daten existieren, wird die Initialisierung übersprungen
                System.out.println("--> Datenbank enthält bereits " + customerCount + " Kunden. Initialisierung übersprungen.");
            }

            jdbcTemplate.query(
                    "SELECT ID, NAME, FIRSTNAME, CONTACT, STATUS, STATUS_CHANGE FROM CUSTOMER",
                    (RowCallbackHandler) rs -> {
                        while (rs.next()) {
                            // System.out.println("Customer row: {"
                            //         + "id=" + rs.getObject("ID")
                            //         + ", name='" + rs.getString("NAME") + "'"
                            //         + ", firstName='" + rs.getString("FIRSTNAME") + "'"
                            //         + ", contact='" + rs.getString("CONTACT") + "'"
                            //         + ", status='" + rs.getString("STATUS") + "'"
                            //         + ", statusChange='" + rs.getString("STATUS_CHANGE") + "'"
                            //         + "}");
                            // 
                            // System.out.println("{"
                            //         + "" + rs.getObject("ID")
                            //         + ", '" + rs.getString("NAME") + "'"
                            //         // + ", '" + rs.getString("FIRSTNAME") + "'"
                            //         + ", '" + rs.getString("CONTACT") + "'"
                            //         + ", '" + rs.getString("STATUS") + "'"
                            //         + ", '" + rs.getString("STATUS_CHANGE") + "'"
                            //         + "}");
                        }
                    }
            );
            // 
            // JDBC query: read all customers from the CUSTOMER table
            var customers = jdbcTemplate.query(
                    "SELECT ID, NAME, FIRSTNAME, CONTACT, STATUS, STATUS_CHANGE FROM CUSTOMER",
                    BeanPropertyRowMapper.newInstance(Customer.class)
            );
            // customers.forEach(System.out::println);
            // 
            customerRepository.findAll().forEach(System.out::println);
            vehicleRepository.findAll().forEach(System.out::println);
            reservationRepository.findAll().forEach(System.out::println);
        };
    }
}
