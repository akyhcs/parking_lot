package com.lld.parking_lot.service;

import com.lld.parking_lot.entity.ParkingSpot;
import com.lld.parking_lot.entity.ParkingTicket;
import com.lld.parking_lot.entity.Vehicle;
import com.lld.parking_lot.repository.ParkingTicketRepository;
import com.lld.parking_lot.repository.ParkingSpotRepository;
import com.lld.parking_lot.repository.VehicleRepository;
import com.lld.parking_lot.types.VehicleType;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class ParkingService {

    private final VehicleRepository vehicleRepository;
    private final ParkingTicketRepository ticketRepository;
    private final ParkingSpotRepository spotRepository;


    public ParkingService(VehicleRepository vehicleRepository, ParkingTicketRepository ticketRepository, ParkingSpotRepository spotRepository) {
        this.vehicleRepository = vehicleRepository;
        this.ticketRepository = ticketRepository;
        this.spotRepository = spotRepository;
    }

    public ParkingTicket enterParkingLot(String licensePlate, VehicleType type) {
        
        // STEP 1: Check if the vehicle exists
        // We try to find it. If not found, we create a new one on the fly.
        Vehicle vehicle = vehicleRepository.findByLicensePlate(licensePlate)
                .orElseGet(() -> {
                    // This block runs ONLY if the car is new
                    Vehicle newVehicle = new Vehicle();
                    newVehicle.setLicensePlate(licensePlate);
                    newVehicle.setType(type);
                    return vehicleRepository.save(newVehicle);
                });

        // STEP 2: Create the Ticket for THIS visit
        ParkingTicket ticket = new ParkingTicket();
        ticket.setVehicle(vehicle); // Re-uses the ID (e.g., 50)
        ticket.setEntryTime(LocalDateTime.now());

        // STEP 3: Allocate a Parking Spot
        ParkingSpot spot = allocateSpot(type);
        
        // Mark spot as occupied
        spot.setOccupied(true);
        spot.setCurrentVehicle(vehicle);
        spotRepository.save(spot);

        ticket.setParkingSpot(spot);

        return ticketRepository.save(ticket);
    }

    private ParkingSpot allocateSpot(VehicleType vehicleType) {
        // Strategy: Best Fit (Smallest available spot that fits the vehicle)
        List<VehicleType> suitableTypes = getSuitableSpotTypes(vehicleType);

        for (VehicleType type : suitableTypes) {
            Optional<ParkingSpot> spot = spotRepository.findFirstBySpotTypeAndIsOccupiedFalse(type);
            if (spot.isPresent()) {
                return spot.get();
            }
        }
        throw new RuntimeException("No parking spot available for vehicle type: " + vehicleType);
    }

    private List<VehicleType> getSuitableSpotTypes(VehicleType type) {
        List<VehicleType> types = new ArrayList<>();
        types.add(type); // Try exact match first

        switch (type) {
            case MOTORCYCLE:
                types.add(VehicleType.COMPACT);
                types.add(VehicleType.SEDAN);
                types.add(VehicleType.SUV);
                types.add(VehicleType.TRUCK);
                break;
            case COMPACT:
                types.add(VehicleType.SEDAN);
                types.add(VehicleType.SUV);
                types.add(VehicleType.TRUCK);
                break;
            case SEDAN:
                types.add(VehicleType.SUV);
                types.add(VehicleType.TRUCK);
                break;
            case SUV:
                types.add(VehicleType.TRUCK);
                break;
            default:
                // TRUCK can only fit in TRUCK
                break;
        }
        return types;
    }
}