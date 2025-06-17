package com.wallet.service;

import com.wallet.model.Transaction;
import com.wallet.model.User;
import com.wallet.repository.TransactionRepository;
import com.wallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WalletService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public BigDecimal addMoney(String username, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setBalance(user.getBalance().add(amount));
        userRepository.save(user);

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setAmount(amount);
        transaction.setType(Transaction.TransactionType.CREDIT);
        transactionRepository.save(transaction);

        return user.getBalance();
    }

    @Transactional
    public BigDecimal withdrawMoney(String username, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (user.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        user.setBalance(user.getBalance().subtract(amount));
        userRepository.save(user);

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setAmount(amount);
        transaction.setType(Transaction.TransactionType.DEBIT);
        transactionRepository.save(transaction);

        return user.getBalance();
    }

    public BigDecimal getBalance(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user.getBalance();
    }

    public List<Transaction> getTransactionHistory(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return transactionRepository.findByUserOrderByTimestampDesc(user);
    }
} 