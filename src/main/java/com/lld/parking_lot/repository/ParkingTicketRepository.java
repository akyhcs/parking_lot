package com.lld.parking_lot.repository;

import com.lld.parking_lot.entity.ParkingTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, Long> {
}