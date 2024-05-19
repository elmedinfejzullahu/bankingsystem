package com.elmedinf.bankingsystem.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private Double balance;


}
