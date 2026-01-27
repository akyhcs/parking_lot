package com.lld.parking_lot.types;

/**
 * Represents the category of the vehicle entering the parking lot.
 * <p>
 * This classification is critical for:
 * 1. Spot Allocation: Ensuring a BUS doesn't park in a COMPACT spot.
 * 2. Fee Calculation: Larger vehicles generally incur higher hourly rates.
 * </p>
 */
public enum VehicleType {
    /**
     * Motorbikes and scooters. Requires the smallest parking footprint.
     */
    MOTORCYCLE,

    /**
     * Small city cars (e.g., Mini Cooper, Fiat 500).
     * Can fit into compact spots.
     */
    COMPACT,

    /**
     * Standard mid-sized vehicles.
     * Requires a standard parking spot.
     */
    SEDAN,

    /**
     * Large vehicles (e.g., Range Rover, Escalade).
     * May require extra-wide spots or specific zones.
     */
    SUV,

    /**
     * Commercial vehicles or heavy-duty pickups.
     * Likely restricted to ground floor or specific high-clearance zones.
     */
    TRUCK,

    /**
     * For vehicles requiring accessible parking spots for individuals with disabilities.
     */
    ACCESSIBLE
}