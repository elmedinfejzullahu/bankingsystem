package com.elmedinf.bankingsystem.dto;

import lombok.Data;

@Data
public class AccountResponse {
    private String username;
    private Double balance;
    private String message;
}
