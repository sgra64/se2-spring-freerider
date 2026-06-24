package de.bht_berlin.freerider_ep.controller;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.bht_berlin.freerider_ep.model.Vehicle;


@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    // JpaRepository bringt automatisch alle CRUD-Operationen (findAll, save etc.) mit

    // creates query: SELECT * FROM VEHICLE WHERE MAKE = ?
    List<Vehicle> findByMake(String make);
}
