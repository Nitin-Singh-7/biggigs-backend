package com.biggigs.freelance_platform.repository;

import com.biggigs.freelance_platform.model.User;
import com.biggigs.freelance_platform.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUser(User user);
}
