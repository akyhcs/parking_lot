package com.lld.parking_lot.repository;

import com.lld.parking_lot.entity.ParkingSpot;
import com.lld.parking_lot.types.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
    Optional<ParkingSpot> findFirstBySpotTypeAndIsOccupiedFalse(VehicleType spotType);
}