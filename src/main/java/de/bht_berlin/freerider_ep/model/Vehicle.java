package de.bht_berlin.freerider_ep.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
// import lombok.Data;

/**
 * Class of a {@link Vehicle} entity matching the {@code 'VEHICLE'}-table
 * of the DB-Schema:
 * <pre>
 * CREATE TABLE if not exists VEHICLE (
 *   ID BIGINT AUTO_INCREMENT not null,
 *   MAKE VARCHAR(60) not null,
 *   MODEL VARCHAR(60) not null,
 *   SEATS INT not null,
 *   CATEGORY enum('Sedan', 'SUV', 'Convertible', 'Van', 'Bike') not null,
 *   POWER enum('Gasoline', 'Diesel', 'Electric', 'Hybrid', 'Hydrogen') default null,
 *   STATUS enum('Active', 'Serviced', 'Terminated') not null,
 *   -- 
 *   PRIMARY KEY (ID)
 * );
 * </pre>
 */
@Entity
@Table(name="VEHICLE")
// @Data
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "MAKE")
    private String make;

    @Column(name = "MODEL")
    private String model;

    @Column(name = "SEATS")
    private Integer seats;

    enum Category { Sedan, SUV, Convertible, Van, Bike };
    // 
    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY")
    private Category category;

    enum Power { Gasoline, Diesel, Electric, Hybrid, Hydrogen };
    // 
    @Enumerated(EnumType.STRING)
    @Column(name = "POWER")
    private Power power;

    enum Status { Active, Serviced, Terminated }
    // 
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;

    @Override
    public String toString() {
        return String.format(
            "{ \"id\": %d, \"make\": \"%s\", \"model\": \"%s\", \"seats\": %d, " +
                "\"category\": %s, \"power\": %s, \"status\": %s }",
                id, make, model, seats, category.toString(), power.toString(), status.toString()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Power getPower() {
        return power;
    }

    public void setPower(Power power) {
        this.power = power;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
