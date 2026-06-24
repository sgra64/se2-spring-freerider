package de.bht_berlin.freerider_ep.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.bht_berlin.freerider_ep.model.Customer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customer Endpoint", description = "Endpoint to manage Customers in the database")
public class CustomerController {

    private final CustomerRepository repository;

    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Return all Customers", description = "Return all Customers found in the database.")
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    @GetMapping("/search")
    @Operation(summary = "Search customers by name", description = "Search for customers by their name in the database.")
    public List<Customer> searchByName(@RequestParam String name) {
        return repository.findByName(name);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by id", description = "Return a single customer by ID from the database.")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update customer", description = "Update an existing customer by ID in the database.")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customerDetails) {
        return repository.findById(id)
            .map(customer -> {
                customer.setName(customerDetails.getName());
                customer.setFirstName(customerDetails.getFirstName());
                customer.setContact(customerDetails.getContact());
                Customer updated = repository.save(customer);
                return ResponseEntity.ok(updated);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create customer", description = "Create a new customer in the database.")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        // Ensure POST always creates a new entity instead of merging an existing one.
        customer.setId(null);
        Customer saved = repository.save(customer);
        return ResponseEntity.created(URI.create("/api/customers/" + saved.getId())).body(saved);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete customer", description = "Delete a customer by ID from the database.")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
