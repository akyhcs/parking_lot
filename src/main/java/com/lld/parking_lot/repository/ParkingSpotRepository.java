package com.lld.parking_lot.repository;

import com.lld.parking_lot.entity.ParkingSpot;
import com.lld.parking_lot.types.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
    /*
    SELECT 
        p.id,
        p.current_vehicle_id,
        p.floor_number,
        p.is_electric,
        p.is_occupied,
        p.spot_number,
        p.spot_type
    FROM 
        parking_spots p
    WHERE 
        p.spot_type = ? 
        AND p.is_occupied = false
    LIMIT 1;
    */
    Optional<ParkingSpot> findFirstBySpotTypeAndIsOccupiedFalse(VehicleType spotType);
}