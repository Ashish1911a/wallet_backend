package com.wallet.service;

import com.wallet.model.Transaction;
import com.wallet.model.Wallet;
import com.wallet.repository.WalletRepository;
import com.wallet.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WalletService {
    
    @Autowired
    private WalletRepository walletRepository;
    
    @Autowired
    private TransactionRepository transactionRepository;

    public Wallet getWalletByUserId(Long userId) {
        return walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found for user: " + userId));
    }

    @Transactional
    public Transaction addTransaction(Long walletId, BigDecimal amount, Transaction.TransactionType type) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found: " + walletId));

        Transaction transaction = new Transaction();
        transaction.setWallet(wallet);
        transaction.setAmount(amount);
        transaction.setType(type);

        wallet.addTransaction(transaction);
        walletRepository.save(wallet);
        
        return transaction;
    }

    public List<Transaction> getTransactionHistory(Long walletId) {
        return transactionRepository.findByWalletIdOrderByTimestampDesc(walletId);
    }
} 