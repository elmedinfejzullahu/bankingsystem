package com.elmedinf.bankingsystem;


import com.elmedinf.bankingsystem.dto.AccountRequest;
import com.elmedinf.bankingsystem.dto.AccountResponse;
import com.elmedinf.bankingsystem.dto.BankRequest;
import com.elmedinf.bankingsystem.dto.TransactionRequest;
import com.elmedinf.bankingsystem.services.AccountService;
import com.elmedinf.bankingsystem.services.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class Main implements CommandLineRunner {

    private final AccountService accountService;
    private final BankService bankService;
    List<AccountRequest> requestList = new ArrayList<>();

    @Override
    public void run(String... args) throws Exception {
        Scanner reader = new Scanner(System.in);


        boolean exit = false;
        boolean finished = false;
        boolean loggedOut = false;

        while (!exit) {
            System.out.println("----------------------");

            System.out.println("Create Bank ---------1");
            System.out.println("Create Account ------2");
            System.out.println("Sign In -------------3");
            System.out.println("Check bank accounts -4");
            System.out.println("----------------------");

            int value = reader.nextInt();
            switch (value) {
                case 1:
                    BankRequest bankRequest = new BankRequest();
                    System.out.println("----------------------");
                    System.out.println("Enter bank name:");
                    String bankName = reader.next();
                    while (!finished) {
                        System.out.println("----------------------");
                        System.out.println("Register Account");
                        System.out.println("Add account name: ");
                        String name = reader.next();

                        System.out.println("Add Balance");
                        Double amount = reader.nextDouble();

                        AccountRequest accountRequest = new AccountRequest();

                        accountRequest.setUsername(name);
                        accountRequest.setBalance(amount);
                        accountService.createAccount(accountRequest);
                        requestList.add(accountRequest);
                        System.out.println(requestList.toString());
                        bankRequest.setBank(bankName);
                        System.out.println("Do you wanna add Account: \nPress 1 for yes or 2 to stop:");
                        int number = reader.nextInt();
                        if (number == 2) {
                            finished = true;
                        } else finished = false;
                    }

                    bankRequest.setAccounts(requestList);
                    bankRequest.setFlatFeeAmount(0.00);
                    bankRequest.setTotalTransactionFeeAmount(0.00);
                    bankRequest.setTotalTransferAmount(0.00);
                    System.out.println(bankService.registerBank(bankRequest).getMessage());
                    finished = false;
                    break;
                case 2:
                    System.out.println("Enter your username: ");
                    String username = reader.next();
                    System.out.println("Add your balance:");
                    double balance = reader.nextDouble();
                    AccountRequest request = new AccountRequest();
                    request.setUsername(username);
                    request.setBalance(balance);
                    System.out.println(accountService.createAccount(request));
                    break;
                case 3:
                    System.out.println("Enter your username");
                    String userAccount = reader.next();
                    AccountResponse res = accountService.signIn(userAccount);

                    if (!res.getUsername().isEmpty()) {
                        while (!loggedOut) {
                            System.out.println("----------------------");

                            System.out.println("Balance -------------1");
                            System.out.println("Withdraw ------------2");
                            System.out.println("Deposit -------------3");
                            System.out.println("Transfer A2A---------4");
                            System.out.println("Transaction History -5");
                            System.out.println("Sign Out ------------6");
                            System.out.println("----------------------");

                            int value1 = reader.nextInt();

                            switch (value1) {
                                case 1:
                                    var balance1 = accountService.getAccount(res.getUsername());
                                    System.out.println("----------------------");
                                    System.out.println(res.getMessage() + balance1.getUsername());
                                    System.out.println("Your balance is: " + balance1.getBalance());
                                    System.out.println("----------------------");
                                    break;
                                case 2:
                                    System.out.println("----------------------");
                                    System.out.println("Enter amount to withdraw:");
                                    double amountWithdraw = reader.nextDouble();
                                    var balanceWithdraw = accountService.getAccount(res.getUsername());
                                    while (amountWithdraw < 0 || amountWithdraw >= balanceWithdraw.getBalance()) {
                                        System.out.println("Transaction is not allowed");
                                        System.out.println("Type another amount");
                                        amountWithdraw = reader.nextDouble();
                                    }

                                    var transfer =  accountService.withdraw(amountWithdraw,res.getUsername());
                                    System.out.println(transfer.getMessage());


                                case 3:
                                    System.out.println("----------------------");
                                    System.out.println("Enter amount to deposit:");
                                    double amountDeposit = reader.nextDouble();
                                    while (amountDeposit < 0){
                                        System.out.println("Transaction is not allowed");
                                        System.out.println("Type another amount");
                                        amountDeposit = reader.nextDouble();
                                    }
                                    var deposit = accountService.deposit(amountDeposit, res.getUsername());
                                    System.out.println(deposit.getMessage());
                                case 4:
                                    System.out.println("----------------------");
                                    System.out.println("Send money to ");
                                    String to = reader.next();
                                    System.out.println("Enter amount");
                                    double amountA2A = reader.nextDouble();
                                    System.out.println("Type reason of transfer");
                                    reader.nextLine();
                                    String reason = reader.nextLine();


                                    System.out.println(reason);


                                    var from = accountService.getAccount(res.getUsername());
                                    var toTransaction = accountService.getAccount(to);
                                    while (amountA2A < 0){
                                        System.out.println("Transaction is not allowed");
                                        System.out.println("Type another amount");
                                        amountA2A = reader.nextDouble();
                                    }

                                    TransactionRequest transactionRequest = new TransactionRequest();
                                    transactionRequest.setAmount(amountA2A);
                                    transactionRequest.setTransactionReason(reason);
                                    transactionRequest.setAmount(amountA2A);
                                    transactionRequest.setOriginatingAccId(from);
                                    transactionRequest.setResultingAccId(toTransaction);

                                    var transaction = accountService.transaction(transactionRequest);
                                    var transferA2A = accountService.transferToAnotherAccount(amountA2A, from.getUsername(), to);
                                    System.out.println(transferA2A.getMessage());

                                case 5:
                                    System.out.println("----------------------");
                                    System.out.println(accountService.getAllTransaction().toString());
                                    System.out.println("----------------------");
                                case 6:
                                    loggedOut = true;



                            }

                        }

                        loggedOut = false;
                    }
                case 4:
                    System.out.println(accountService.getAllAccount().toString());
                    System.out.println("Sum of all the bank's funds: " + String.format("%.2f",(accountService.allAmount())));
                    System.out.println("Sum of all transaction funds: " + accountService.allAmountTransaction());

            }
        }
    }
}


