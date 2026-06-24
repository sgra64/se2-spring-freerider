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
// import lombok.AccessLevel;
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.RequiredArgsConstructor;
// import lombok.experimental.Accessors;
// import lombok.Data;

/**
 * Class of a {@link Customer} entity matching the {@code 'CUSTOMER'}-table
 * of the DB-Schema:
 * <pre>
 * CREATE TABLE if not exists CUSTOMER (
 *   ID BIGINT AUTO_INCREMENT not null,            -- use AUTO_INCREMENT instead
 *   NAME VARCHAR(60) not null,
 *   FIRSTNAME VARCHAR(60) default null,
 *   CONTACT VARCHAR(60) default null,
 *   STATUS enum('Active', 'InRegistration', 'Terminated') default null,
 *   STATUS_CHANGE TIMESTAMP default null,
 *   -- 
 *   PRIMARY KEY (ID)
 * );
 * </pre>
 */
@Entity
@Table(name="CUSTOMER")
// 
// @Data
// 
// @Getter
// @RequiredArgsConstructor // error with mvn compile??
// @NoArgsConstructor(access=AccessLevel.PROTECTED) // 1. Zwingend für JPA
// @AllArgsConstructor
// @Accessors(fluent=true)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "CONTACT")
    private String contact;

    enum Status { Active, InRegistration, Terminated }
    // 
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;

    @Column(name = "STATUS_CHANGE")
    private LocalDateTime statusChange;

    @Override
    public String toString() {
        return String.format(
            "{ \"id\": %d, \"name\": \"%s\", \"firstName\": \"%s\", \"contact\": \"%s\", \"status\": %s, \"statusChange\": \"%s\" }",
                id, name, firstName, contact, status.toString(), statusChange
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getStatusChange() {
        return statusChange;
    }

    public void setStatusChange(LocalDateTime statusChange) {
        this.statusChange = statusChange;
    }
}
