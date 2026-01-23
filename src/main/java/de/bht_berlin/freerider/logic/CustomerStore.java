package de.bht_berlin.freerider.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import de.bht_berlin.freerider.datamodel.Customer;

/**
 * Component class that provides basic {@literal CRUD} operations for
 * {@link Customer} objects.
 */
@Component
public class CustomerStore {

    /**
     * Objects are stored as {@link Map}.
     */
    private final Map<Long, Customer> store = new HashMap<>();

    /**
     * Constructor that creates sample objects and inserts into store.
     */
    CustomerStore() {
        Customer eric = new Customer(100L, "Meyer", "Eric", "eme24@gmail.com");
        Customer anne = new Customer(101L, "Blumenfeld", "Anne", "+49 030 239531265");
        Customer tim = new Customer(102L, "Weimer", "Tim", "tim@gmail.com");
        // 
        save(eric);
        save(anne);
        save(tim);
    }

    /**
     * Saves a given entity. Use the returned instance for further operations as
     * the save operation might have changed the entity instance completely.
     * @param customer must not be {@literal null}.
     * @return the previous {@literal entity} associated with the {@code key} or
     * the saved {@literal entity} if there was no mapping for {@code key}.
     * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}.
     */
    public Customer save(Customer customer) {
        if(customer==null)
            throw new IllegalArgumentException("customer argument is null");
        // 
        return Optional.ofNullable(
                store.put(customer.id(), customer)
            ).orElse(customer);
    }

    public Iterable<Customer> findAll() {
        return store.values();
    }

    public Optional<Customer> findById(long id) {
        return Optional.ofNullable(store.get(id));
    }

    public long count() {
        return store.size();
    }

    public boolean contains(long id) {
        return store.containsKey(id);
    }
}
