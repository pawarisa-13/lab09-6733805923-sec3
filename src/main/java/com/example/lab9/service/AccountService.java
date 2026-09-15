package com.example.lab9.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.lab9.model.Account;
import com.example.lab9.repository.AccountRepository;

@Service 
public class AccountService {
    
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    //create
    public void createAccount(Account account){
        accountRepository.save(account);
    }

    //readall
    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    } 

    //read{id}
    public Account getAccountById(Long id){
        return accountRepository.findById(id).orElse(null);
    }
}
