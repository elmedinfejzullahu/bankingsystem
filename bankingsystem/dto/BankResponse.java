package com.elmedinf.bankingsystem.dto;

import com.elmedinf.bankingsystem.models.Account;
import lombok.Data;

import java.util.List;
@Data
public class BankResponse {
    private String Bank;
    private List<Account> accounts;
    private Double totalTransactionFeeAmount;
    private Double totalTransferAmount;
    private Double flatFeeAmount;
    private String message;
}
