package de.bht_berlin.freerider_ep.controller;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.bht_berlin.freerider_ep.model.Customer;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // JpaRepository bringt automatisch alle CRUD-Operationen (findAll, save etc.) mit

    // creates query: SELECT * FROM CUSTOMER WHERE NAME = ? ;
    List<Customer> findByName(String name);
}
