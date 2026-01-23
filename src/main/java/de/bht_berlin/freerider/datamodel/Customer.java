package de.bht_berlin.freerider.datamodel;

/**
 * Record of a {@link Customer} entity.
 */
public record Customer (
    long id,
    String name,
    String firstNames,
    String contacts
) { };
