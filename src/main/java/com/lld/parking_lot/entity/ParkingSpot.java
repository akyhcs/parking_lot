package com.lld.parking_lot.entity;

import com.lld.parking_lot.types.VehicleType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a physical parking spot within the parking lot.
 */
@Entity
@Table(name = "parking_spots")
@Data
@NoArgsConstructor
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * A unique identifier for the spot (e.g., "A-101", "G-12").
     */
    @Column(nullable = false, unique = true, length = 10)
    private String spotNumber;

    /**
     * The type of vehicle this spot is designed to accommodate.
     * A spot of type SUV can typically accommodate smaller vehicles as well.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType spotType;

    /**
     * Indicates if this spot has an electric charger.
     */
    @Column(name = "is_electric")
    private boolean isElectric = false;

    /**
     * Status flag for quick availability checks.
     * Should be kept in sync with currentVehicle.
     */
    @Column(name = "is_occupied")
    private boolean isOccupied = false;

    /**
     * The vehicle currently parked in this spot.
     * Null if the spot is free.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_vehicle_id")
    private Vehicle currentVehicle;

    /**
     * The floor number where this spot is located.
     */
    @Column(name = "floor_number")
    private Integer floorNumber;
}