package com.lld.parking_lot.entity;

import com.lld.parking_lot.types.GateType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gates")
@Data
@NoArgsConstructor
public class Gate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String gateNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GateType gateType;
}
