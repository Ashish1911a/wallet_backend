package com.wallet.controller;

import com.wallet.model.Transaction;
import com.wallet.model.Wallet;
import com.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping("/{userId}")
    public ResponseEntity<Wallet> getWallet(@PathVariable Long userId) {
        return ResponseEntity.ok(walletService.getWalletByUserId(userId));
    }

    @PostMapping("/{walletId}/credit")
    public ResponseEntity<Transaction> creditWallet(
            @PathVariable Long walletId,
            @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(walletService.addTransaction(walletId, amount, Transaction.TransactionType.CREDIT));
    }

    @PostMapping("/{walletId}/debit")
    public ResponseEntity<Transaction> debitWallet(
            @PathVariable Long walletId,
            @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(walletService.addTransaction(walletId, amount, Transaction.TransactionType.DEBIT));
    }

    @GetMapping("/{walletId}/transactions")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable Long walletId) {
        return ResponseEntity.ok(walletService.getTransactionHistory(walletId));
    }
} 