package com.elmedinf.bankingsystem.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bank;
    @OneToMany
    private List<Account> accounts;
    private Double totalTransactionFeeAmount;
    private Double totalTransferAmount;
    private Double flatFeeAmount;
}
