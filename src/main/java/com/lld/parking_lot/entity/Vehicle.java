package com.lld.parking_lot.entity;

import com.lld.parking_lot.types.VehicleType;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import jakarta.persistence.*;

/**
 * Represents a physical vehicle in the parking system.
 * <p>
 * This entity acts as the primary record for entry/exit tracking.
 * It is identified uniquely by its License Plate.
 * </p>
 */

@Entity
@Table(name = "vehicles")
@Data // Generates Getters, Setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a no-args constructor required by JPA
public class Vehicle {

    /**
     * Unique internal identifier for database relationships.
     * Not exposed to the end-user usually.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The official license plate number.
     * <p>
     * Annotated with `unique = true` to prevent duplicate entries for the
     * same physical car. This is the primary search key for the system.
     * </p>
     */
    @Column(nullable = false, unique = true, length = 15)
    private String licensePlate;

    /**
     * The classification of the vehicle.
     * Stored as a String in the DB (e.g., "SUV") for readability.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType type;
    /**
     * If you omit @Column: JPA uses intelligent defaults:
    Column Name: Same as the field name (e.g., brand becomes brand).
    Data Type: Inferred from the Java type (e.g., String becomes VARCHAR(255)).
    Nullability: Default is nullable = true
    */
    // Optional metadata for identification purposes
    private String brand;  // e.g., "Toyota"
    private String model;  // e.g., "Camry"
    private String color;  // e.g., "Silver"

    /**
     * Flag to indicate if the vehicle requires an electric charging spot.
     * Defaults to false.
     */
    @Column(name = "is_electric")
    private boolean isElectric = false;

    /**
     * Audit field recording when this vehicle was first registered in the system.
     * `updatable = false` ensures this timestamp never changes after insertion.
     */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * JPA Lifecycle hook to automatically set the creation timestamp
     * before the entity is persisted to the database.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}