package com.lld.parking_lot.entity;

import com.lld.parking_lot.types.TicketStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Represents a transaction or session for a vehicle parking in a spot.
 */
@Entity
@Table(name = "parking_tickets")
@Data
@NoArgsConstructor
public class ParkingTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique ticket number (e.g., UUID or barcode string).
     */
    @Column(nullable = false, unique = true)
    private String ticketNumber;

    /**
     * The vehicle associated with this ticket.
     */
    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    /**
     * The spot assigned to this ticket.
     */
    @ManyToOne
    @JoinColumn(name = "spot_id", nullable = false)
    private ParkingSpot parkingSpot;

    @Column(nullable = false)
    private LocalDateTime entryTime;

    private LocalDateTime exitTime;

    /**
     * The fee calculated upon exit or payment.
     */
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status = TicketStatus.ACTIVE;
}