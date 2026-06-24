package de.bht_berlin.freerider_ep.controller;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.bht_berlin.freerider_ep.model.Reservation;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // JpaRepository bringt automatisch alle CRUD-Operationen (findAll, save etc.) mit

    // creates query: SELECT * FROM RESERVATION WHERE CUSTOMER = ?
    List<Reservation> findByCustomer(Long customer);

    // creates query: SELECT * FROM RESERVATION WHERE VEHICLE = ?
    List<Reservation> findByVehicle(Long vehicle);
}
