package de.bht_berlin.freerider_ep.model;

import java.time.LocalDateTime;
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
 * Class of a {@link Customer} entity matching the {@code 'CUSTOMER'}-table
 * of the DB-Schema:
 * <pre>
 * CREATE TABLE if not exists RESERVATION (
 *   ID BIGINT AUTO_INCREMENT not null,
 *   CUSTOMER_ID BIGINT not null,
 *   VEHICLE_ID BIGINT not null,
 *   TIME_BEGIN TIMESTAMP not null,
 *   TIME_END TIMESTAMP not null,
 *   PICKUP VARCHAR(48) not null,
 *   DROPOFF VARCHAR(48) not null,
 *   STATUS enum('Inquired', 'InquiryConfirmed', 'Booked', 'Cancelled') not null,
 *   -- 
 *   PRIMARY KEY (ID),
 *   CONSTRAINT CUSTOMER_ID FOREIGN KEY (CUSTOMER_ID) references CUSTOMER (ID),
 *   CONSTRAINT VEHICLE_ID FOREIGN KEY (VEHICLE_ID) references VEHICLE (ID)
 * );
 * </pre>
 */
@Entity
@Table(name="RESERVATION")
// @Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CUSTOMER_ID")
    private Long customer;

    @Column(name = "VEHICLE_ID")
    private Long vehicle;

    @Column(name = "TIME_BEGIN")
    private LocalDateTime timeBegin;

    @Column(name = "TIME_END")
    private LocalDateTime timeEnd;

    @Column(name = "PICKUP")
    private String pickup;

    @Column(name = "DROPOFF")
    private String dropoff;

    enum Status { Inquired, InquiryConfirmed, Booked, Cancelled }
    // 
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;

    @Override
    public String toString() {
        return String.format(
            "{ \"id\": %d, \"customer_ID\": %d, \"vehicle_ID\": %d, " +
                "\"timeBegin\": \"%s\", \"timeEnd\": \"%s\", " +
                "\"pickup\": \"%s\", \"dropoff\": \"%s\", " +
                "\"status\": %s }",
                id, customer, vehicle, timeBegin, timeEnd, pickup, dropoff, status.toString()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomer() {
        return customer;
    }

    public void setCustomer(Long customer_ID) {
        this.customer = customer_ID;
    }

    public Long getVehicle() {
        return vehicle;
    }

    public void setVehicle(Long vehicle_ID) {
        this.vehicle = vehicle_ID;
    }

    public LocalDateTime getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(LocalDateTime timeBegin) {
        this.timeBegin = timeBegin;
    }

    public LocalDateTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalDateTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getDropoff() {
        return dropoff;
    }

    public void setDropoff(String dropoff) {
        this.dropoff = dropoff;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
