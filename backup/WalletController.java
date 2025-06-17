package com.wallet.controller;

import com.wallet.model.Transaction;
import com.wallet.service.WalletService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping("/balance")
    public ResponseEntity<BalanceResponse> getBalance(Authentication authentication) {
        BigDecimal balance = walletService.getBalance(authentication.getName());
        return ResponseEntity.ok(new BalanceResponse(balance));
    }

    @PostMapping("/add")
    public ResponseEntity<BalanceResponse> addMoney(
            Authentication authentication,
            @RequestBody MoneyRequest request) {
        BigDecimal newBalance = walletService.addMoney(authentication.getName(), request.getAmount());
        return ResponseEntity.ok(new BalanceResponse(newBalance));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<BalanceResponse> withdrawMoney(
            Authentication authentication,
            @RequestBody MoneyRequest request) {
        BigDecimal newBalance = walletService.withdrawMoney(authentication.getName(), request.getAmount());
        return ResponseEntity.ok(new BalanceResponse(newBalance));
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getTransactionHistory(Authentication authentication) {
        List<Transaction> transactions = walletService.getTransactionHistory(authentication.getName());
        return ResponseEntity.ok(transactions);
    }
}

@Data
class MoneyRequest {
    private BigDecimal amount;
}

@Data
class BalanceResponse {
    private final BigDecimal balance;
} 