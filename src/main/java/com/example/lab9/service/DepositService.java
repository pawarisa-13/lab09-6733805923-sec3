package com.example.lab9.service;

import com.example.lab9.repository.DepositRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.lab9.model.Account;
import com.example.lab9.model.DepositTransaction;
import com.example.lab9.repository.AccountRepository;

@Service
public class DepositService {

    private final DepositRepository depositRepository;
    private final AccountRepository accountRepository;

    public DepositService(AccountRepository accountRepository, DepositRepository depositRepository) {
        this.accountRepository = accountRepository;
        this.depositRepository = depositRepository;
    }

    @Transactional 
    public void deposit(Long accountId, Double amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than 0");
        }

        // ค้นหา Account
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountId));

        // เพิ่ม balance
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        // สร้าง DepositTransaction
        DepositTransaction transaction = new DepositTransaction();
        transaction.setAmount(amount);
        transaction.setAccount(account);
        depositRepository.save(transaction);

        throw new RuntimeException("Test Rollback");
    }
}