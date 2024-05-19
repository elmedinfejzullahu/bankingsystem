package com.elmedinf.bankingsystem.dto;

import com.elmedinf.bankingsystem.models.Account;
import lombok.Data;

@Data
public class TransactionResponse {
    private Double amount;
    private Account originatingAccId;
    private Account resultingAccId;
    private String transactionReason;
    private String message;
}
