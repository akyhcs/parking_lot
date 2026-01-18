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
        Vehicle vehicle = getOrCreateVehicle(licensePlate, type);
        ParkingSpot spot = assignSpot(vehicle, type);
        return createTicket(vehicle, spot);
    }

    private Vehicle getOrCreateVehicle(String licensePlate, VehicleType type) {
        return vehicleRepository.findByLicensePlate(licensePlate)
                .orElseGet(() -> {
                    Vehicle newVehicle = new Vehicle();
                    newVehicle.setLicensePlate(licensePlate);
                    newVehicle.setType(type);
                    return vehicleRepository.save(newVehicle);
                });
    }

    private ParkingSpot assignSpot(Vehicle vehicle, VehicleType type) {
        ParkingSpot spot = allocateSpot(type);
        spot.setOccupied(true);
        spot.setCurrentVehicle(vehicle);
        return spotRepository.save(spot);
    }

    private ParkingTicket createTicket(Vehicle vehicle, ParkingSpot spot) {
        ParkingTicket ticket = new ParkingTicket();
        ticket.setVehicle(vehicle);
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
    /**
     * Generates a prioritized list of suitable parking spot types for a given vehicle.
     * <p>
     * Implements a "Best Fit" hierarchy:
     * 1. <b>Exact Match:</b> Prioritizes the spot type that matches the vehicle (e.g., Compact -> Compact).
     * 2. <b>Upgrade:</b> If the exact match is unavailable, allows larger spots (e.g., Compact -> SUV).
     * 3. <b>Restriction:</b> Prevents larger vehicles from occupying smaller spots.
     * </p>
     *
     * @param type The type of the vehicle entering the lot.
     * @return A list of allowed spot types, ordered from most to least efficient.
     * @example Input: COMPACT -> Returns: [COMPACT, SEDAN, SUV, TRUCK]
     */
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