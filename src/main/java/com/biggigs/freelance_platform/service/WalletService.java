package com.biggigs.freelance_platform.service;

import com.biggigs.freelance_platform.model.User;
import com.biggigs.freelance_platform.model.Wallet;
import com.biggigs.freelance_platform.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class WalletService {
    private final WalletRepository walletRepo;

    public WalletService(WalletRepository walletRepo) {
        this.walletRepo = walletRepo;
    }

    public Wallet getOrCreate(User user) {
        return walletRepo.findByUser(user).orElseGet(() -> walletRepo.save(new Wallet(user)));
    }

    @Transactional
    public Wallet credit(User user, BigDecimal amount) {
        Wallet w = getOrCreate(user);
        w.setBalance(w.getBalance().add(amount));
        w.setUpdatedAt(java.time.LocalDateTime.now());
        return walletRepo.save(w);
    }

    @Transactional
    public Wallet debit(User user, BigDecimal amount) {
        Wallet w = getOrCreate(user);
        if (w.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient balance");
        }
        w.setBalance(w.getBalance().subtract(amount));
        w.setUpdatedAt(java.time.LocalDateTime.now());
        return walletRepo.save(w);
    }
}
