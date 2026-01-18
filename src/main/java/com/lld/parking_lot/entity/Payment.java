package com.lld.parking_lot.entity;

import com.lld.parking_lot.types.PaymentMethod;
import com.lld.parking_lot.types.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // -------------------------------------------------------
    // RELATIONSHIP: Link to the specific parking session
    // One Payment pays for exactly One Ticket.
    // -------------------------------------------------------
    @OneToOne
    @JoinColumn(name = "ticket_id", nullable = false, unique = true)
    private ParkingTicket ticket;

    // -------------------------------------------------------
    // FINANCIAL DATA
    // Always use BigDecimal for money to avoid rounding errors
    // (e.g., 10.00 instead of 9.9999999)
    // -------------------------------------------------------
    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod; // e.g., CREDIT_CARD

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;        // e.g., SUCCESS

    // -------------------------------------------------------
    // AUDIT DATA
    // -------------------------------------------------------

    // The ID sent back by the bank/Stripe/PayPal
    // Crucial for debugging "Where is my money?"
    private String transactionReference;

    @Column(nullable = false)
    private LocalDateTime paymentTime;

    @PrePersist
    protected void onPay() {
        this.paymentTime = LocalDateTime.now();
    }
}