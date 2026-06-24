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
import org.springframework.web.bind.annotation.RestController;

import de.bht_berlin.freerider_ep.model.Reservation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/reservations")
@Tag(name = "Reservation Endpoint", description = "Endpoint to manage Reservations in the database")
public class ReservationController {

    private final ReservationRepository repository;

    public ReservationController(ReservationRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Return all Reservations", description = "Return all Reservations found in the database.")
    public List<Reservation> getAllReservations() {
        return repository.findAll();
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get reservations by customer", description = "Return all reservations for a specific customer from the database.")
    public List<Reservation> getReservationsByCustomer(@PathVariable Long customerId) {
        return repository.findByCustomer(customerId);
    }

    @GetMapping("/vehicle/{vehicleId}")
    @Operation(summary = "Get reservations by vehicle", description = "Return all reservations for a specific vehicle from the database.")
    public List<Reservation> getReservationsByVehicle(@PathVariable Long vehicleId) {
        return repository.findByVehicle(vehicleId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get reservation by id", description = "Return a single reservation by ID from the database.")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create reservation", description = "Create a new reservation in the database.")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        // Ensure POST always creates a new entity instead of merging an existing one.
        reservation.setId(null);
        Reservation saved = repository.save(reservation);
        return ResponseEntity.created(URI.create("/api/reservations/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update reservation", description = "Update an existing reservation by ID in the database.")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Reservation reservationDetails) {
        return repository.findById(id)
            .map(reservation -> {
                reservation.setCustomer(reservationDetails.getCustomer());
                reservation.setVehicle(reservationDetails.getVehicle());
                reservation.setTimeBegin(reservationDetails.getTimeBegin());
                reservation.setTimeEnd(reservationDetails.getTimeEnd());
                reservation.setPickup(reservationDetails.getPickup());
                reservation.setDropoff(reservationDetails.getDropoff());
                reservation.setStatus(reservationDetails.getStatus());
                Reservation updated = repository.save(reservation);
                return ResponseEntity.ok(updated);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete reservation", description = "Delete a reservation by ID from the database.")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
