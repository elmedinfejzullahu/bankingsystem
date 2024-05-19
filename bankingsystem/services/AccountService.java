package com.elmedinf.bankingsystem.services;

import com.elmedinf.bankingsystem.dto.AccountRequest;
import com.elmedinf.bankingsystem.dto.AccountResponse;
import com.elmedinf.bankingsystem.dto.TransactionRequest;
import com.elmedinf.bankingsystem.dto.TransactionResponse;
import com.elmedinf.bankingsystem.models.Account;
import com.elmedinf.bankingsystem.models.Transaction;
import com.elmedinf.bankingsystem.repository.AccountRepository;
import com.elmedinf.bankingsystem.repository.TransactionRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Builder
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;


    public AccountResponse createAccount(AccountRequest request) {
        var user = accountRepository.findAccountByUsername(request.getUsername());
        Account newAccount = new Account();
        AccountResponse response = new AccountResponse();

        if (user.isPresent()) {
            response.setMessage("Can't create Account\nThis account exist!");
        } else {
            newAccount.setUsername(request.getUsername());
            newAccount.setBalance(request.getBalance());
            response.setMessage("Welcome!!!");
            accountRepository.save(newAccount);
            response.setUsername(newAccount.getUsername());
            response.setBalance(newAccount.getBalance());
        }
        return response;
    }

    public AccountResponse signIn(String username) {
        var user = accountRepository.findAccountByUsername(username);
        AccountResponse response = new AccountResponse();
        if (!user.isPresent()) {
            response.setMessage("User doesn't exist\nPlease register! ");

        } else {
            response.setUsername(user.get().getUsername());
            response.setBalance(user.get().getBalance());
            response.setMessage("Welcome ");
        }
        return response;
    }

    public Account getAccount(String username){
    return accountRepository.findAccountByUsername(username).orElse(null);
    }

    public AccountResponse withdraw(Double amount,  String username){
        int flatFee = 10;
        AccountResponse response = new AccountResponse();
        var balance = getAccount(username);
        balance.setBalance(balance.getBalance() - flatFee - amount);
        accountRepository.save(balance);
        response.setUsername(balance.getUsername());
        response.setBalance(getAccount(username).getBalance());
        response.setMessage("Transaction was successful");

        return response;
    }

    public AccountResponse deposit(Double amount, String username){
        int flatFee = 10;
        AccountResponse response = new AccountResponse();
        var balance = getAccount(username);
        balance.setBalance(balance.getBalance() + amount - flatFee);
        accountRepository.save(balance);
        response.setUsername(balance.getUsername());
        response.setBalance(getAccount(username).getBalance());
        response.setMessage("Transaction was successful");

        return response;
    }

    public AccountResponse transferToAnotherAccount(Double amount, String from, String to){
        double percentFee = 0.05;
        double flatFee = 0.1;

        AccountResponse response = new AccountResponse();
        var fromAcc = getAccount(from);
        var toAcc = getAccount(to);
        fromAcc.setBalance(fromAcc.getBalance() - amount - flatFee);
        var remainingAmount = amount - (amount * percentFee);
        toAcc.setBalance(toAcc.getBalance() + remainingAmount);
        accountRepository.save(fromAcc);
        accountRepository.save(toAcc);
        response.setMessage("Transaction was successful");
        return response;
    }

    public TransactionResponse transaction(TransactionRequest request) {
        TransactionResponse response = new TransactionResponse();
        var fromAcc = getAccount(request.getOriginatingAccId().getUsername());
        var toAcc = getAccount(request.getResultingAccId().getUsername());
        Transaction transaction = new Transaction();
        transaction.setOriginatingAccId(fromAcc.getId());
        transaction.setResultingAccId(toAcc.getId());
        transaction.setTransactionReason(request.getTransactionReason());
        transaction.setAmount(request.getAmount());

        transactionRepository.save(transaction);
        response.setMessage("Transaction was successful");

        return response;
    }

    public List<Transaction> getAllTransaction(){
        return transactionRepository.findAll();
    }

    public List<Account> getAllAccount(){
        return accountRepository.findAll();
    }

    public Double allAmount(){
        var list = accountRepository.findAll();
        double total = 0.0;
        for (Account account : list) {
            total += account.getBalance();

        }

        return total;
    }

    public Double allAmountTransaction(){
        var list = transactionRepository.findAll();
        double total = 0.0;
        for (Transaction account : list) {
            total += account.getAmount();

        }
        String formattedValue = String.format("%.2f", total);
        return total;
    }





}
