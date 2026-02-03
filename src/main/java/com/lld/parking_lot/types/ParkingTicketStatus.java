package com.lld.parking_lot.types;

/**
 * Represents the current state of a parking ticket.
 */
public enum ParkingTicketStatus {
    ACTIVE, // Vehicle is currently parked
    PAID,   // Payment completed, vehicle can exit
    LOST    // Ticket was lost, special handling required
}