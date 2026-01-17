package com.lld.parking_lot.repository;

import com.lld.parking_lot.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // This tells Spring to create a Bean for this interface
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    // Custom query to find a car by its plate
    // Spring generates the SQL for this automatically!
    Optional<Vehicle> findByLicensePlate(String licensePlate);
}
