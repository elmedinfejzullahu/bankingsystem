package com.elmedinf.bankingsystem.services;

import com.elmedinf.bankingsystem.dto.BankRequest;
import com.elmedinf.bankingsystem.dto.BankResponse;
import com.elmedinf.bankingsystem.models.Account;
import com.elmedinf.bankingsystem.models.Bank;
import com.elmedinf.bankingsystem.repository.BankRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Builder
public class BankService {
    private final BankRepository repository;
    private final List<Account> accountList;

    public BankResponse registerBank(BankRequest request) {
        var bank = repository.findByBank(request.getBank());
        Bank newBank = new Bank();
        BankResponse response = new BankResponse();
        if (bank.isPresent()) {
            response.setMessage("Can't create Bank\nThis bank exist!");
            return response;
        } else {
            newBank.setBank(request.getBank());
            newBank.setTotalTransactionFeeAmount(request.getTotalTransactionFeeAmount());
            newBank.setTotalTransferAmount(request.getTotalTransferAmount());
            newBank.setFlatFeeAmount(request.getFlatFeeAmount());
            repository.save(newBank);
            response.setBank(newBank.getBank());
            response.setAccounts(newBank.getAccounts());
            response.setTotalTransactionFeeAmount(newBank.getTotalTransactionFeeAmount());
            response.setTotalTransferAmount(newBank.getTotalTransferAmount());
            response.setFlatFeeAmount(newBank.getFlatFeeAmount());
        }
        return response;
    }
}
