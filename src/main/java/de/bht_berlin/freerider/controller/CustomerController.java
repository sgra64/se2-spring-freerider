package de.bht_berlin.freerider.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import de.bht_berlin.freerider.datamodel.Customer;
import de.bht_berlin.freerider.logic.CustomerStore;


/**
 * REST-Controller for the '/customers' endpoint supporting {@literal CRUD}
 * operations for objects stored in the underlying {@link CustomerStore}.
 * <p>
 * The controller handles the interactions with the REST-endpoint obtaining
 * input from path variables from the URL or objects deserialized from JSON
 * from the incoming {@link RequestBody}.
 * <p>
 * The controller returns responses using {@link ResponseEntity} passing
 * objects for JSON-serialization to the HTTP Response message. It also
 * includes response {@link HttpStatus} codes.
 * <p>
 * The controller relies on the underlying {@link CustomerStore} to that
 * provides {@literal CRUD} operations for objects.
 * <p>
 * Use:
 * - http://localhost:8080/customers
 * - http://localhost:8080/customers/100
 * 
 * References:
 * - https://www.baeldung.com/spring-boot-json
 * - https://www.baeldung.com/spring-boot-get-all-endpoints
 * - https://swagger.io
 */
@RestController
@RequestMapping("/customers")
class CustomerController {

    final Logger log = LoggerFactory.getLogger(CustomerController.class);

    final CustomerStore customerStore;

    /**
     * Constructor with injected dependencies.
     * @param customerStore
     */
    CustomerController(CustomerStore customerStore) {
        this.customerStore = customerStore;
    }


    /**
     * {@literal GET: /customers}
     * Return all {@link Customer} objects stored in {@link CustomerStore}.
     * @return JSON-array with all {@link Customer} objects.
     */
    @GetMapping("")
    ResponseEntity<Iterable<Customer>> findAll() {
        long count = customerStore.count();
        log.info(String.format("GET: %d Customer objects found", count));
        return new ResponseEntity<>(customerStore.findAll(), HttpStatus.OK);
    }


    /**
     * {@literal GET: /customers/{:id}}
     * <p>
     * Return {@link Customer} object associated with the {@code id}.
     * @param id key of the requested {@link Customer} object.
     * @return JSON-object associated with the {@code id} or {@code HttpStatus.NOT_FOUND}.
     */
    @GetMapping(path = "/{id}", produces = "application/json")
    // 
    ResponseEntity<Customer> findById(@PathVariable(name = "id") Long id) {
        // 
        Optional<Customer> customer = Optional.empty();
        try {
            long idl = id;//Long.valueOf(id);
            // 
            customer = customerStore.findById(idl);
            // 
            if(customer.isPresent()) {
                log.info(String.format("GET: Customer with id: '%d' found", idl));
            } else {
                log.warn(String.format("GET: Customer not found for id: '%d'", id));
            }
        } catch(NumberFormatException nfx) {
            log.warn(String.format("GET: Customer illegal id: '%s'", id));
        };
        return customer.map(c -> new ResponseEntity<>(c, HttpStatus.OK))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    /**
     * {@literal POST /customers}.
     * <p>
     * Create new {@link Customer} object and save to {@link CustomerStore}.
     * @param customer {@link Customer} objects de-serialized from JSON
     * from the {@code RequestBody}.
     * @return created object or {@code HttpStatus.CONFLICT} if object
     * with same {@code id} was present.
     */
    @PostMapping(path = "", produces = "application/json")
    // 
    ResponseEntity<Customer> create(@RequestBody Customer customer) {
        // 
        var id = customer.id();
        // 
        if( ! customerStore.contains(id)) {
            customerStore.save(customer);
            // 
            log.info(String.format("POST: Customer with id: '%d' created", id));
        } else {
            log.warn(String.format("POST: attempt to create Customer with already present id: '%d'", id));
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

}
