package com.lld.parking_lot.dto;

import lombok.Data;

@Data
public class ExitRequest {
    private Long ticketId;
    private Long exitGateId;
    private String paymentMethod;
}