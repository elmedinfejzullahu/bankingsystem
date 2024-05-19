package com.elmedinf.bankingsystem.dto;

import com.elmedinf.bankingsystem.models.Account;
import lombok.Data;

import java.util.List;
@Data
public class BankRequest {
    private String Bank;
    private List<AccountRequest> accounts;
    private Double totalTransactionFeeAmount;
    private Double totalTransferAmount;
    private Double flatFeeAmount;
}
