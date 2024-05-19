package com.elmedinf.bankingsystem.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private Long originatingAccId;
    private Long resultingAccId;
    private String transactionReason;
}
