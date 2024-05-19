package com.elmedinf.bankingsystem.dto;

import lombok.Data;

@Data
public class AccountRequest {
    private String username;
    private Double balance;
}
