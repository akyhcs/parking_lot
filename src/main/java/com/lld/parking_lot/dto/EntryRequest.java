package com.lld.parking_lot.dto;

import com.lld.parking_lot.types.VehicleType;
import lombok.Data;

@Data
public class EntryRequest {
    private String licensePlate;
    private VehicleType vehicleType;
    private Long entranceGateId;
}