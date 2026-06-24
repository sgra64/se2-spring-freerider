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

import de.bht_berlin.freerider_ep.model.Vehicle;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/vehicles")
@Tag(name = "Vehicle Endpoint", description = "Endpoint to manage Vehicles in the database")
public class VehicleController {

    private final VehicleRepository repository;

    public VehicleController(VehicleRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @Operation(summary = "Return all Vehicles", description = "Return all Vehicles found in the database.")
    public List<Vehicle> getAllVehicles() {
        return repository.findAll();
    }

    @GetMapping("/search")
    @Operation(summary = "Search vehicles by make", description = "Search for vehicles by their make in the database.")
    public List<Vehicle> searchByMake(@RequestParam String make) {
        return repository.findByMake(make);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get vehicle by id", description = "Return a single vehicle by ID from the database.")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id) {
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create vehicle", description = "Create a new vehicle in the database.")
    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) {
        // Ensure POST always creates a new entity instead of merging an existing one.
        vehicle.setId(null);
        Vehicle saved = repository.save(vehicle);
        return ResponseEntity.created(URI.create("/api/vehicles/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update vehicle", description = "Update an existing vehicle by ID in the database.")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id, @RequestBody Vehicle vehicleDetails) {
        return repository.findById(id)
            .map(vehicle -> {
                vehicle.setMake(vehicleDetails.getMake());
                vehicle.setModel(vehicleDetails.getModel());
                vehicle.setSeats(vehicleDetails.getSeats());
                Vehicle updated = repository.save(vehicle);
                return ResponseEntity.ok(updated);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete vehicle", description = "Delete a vehicle by ID from the database.")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
