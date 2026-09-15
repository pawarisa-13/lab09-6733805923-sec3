package com.example.lab9.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import com.example.lab9.model.Account;
import com.example.lab9.service.AccountService;
import com.example.lab9.service.DepositService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final DepositService depositService;

    public AccountController(AccountService accountService, DepositService depositService) {
        this.accountService = accountService;
        this.depositService = depositService;
    }

    // POST /accounts
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        accountService.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    // GET /accounts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        Account account = accountService.getAccountById(id);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(account);
    }

    // POST /accounts/{id}/deposit
    @PostMapping("/{id}/deposit")
    public ResponseEntity<?> deposit(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body) {
        try {
            Double amount = Double.valueOf(body.get("amount").toString());
            depositService.deposit(id, amount);
            return ResponseEntity.status(HttpStatus.CREATED).body("Deposit successful");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}