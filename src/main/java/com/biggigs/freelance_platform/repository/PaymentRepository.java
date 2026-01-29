package com.biggigs.freelance_platform.repository;

import com.biggigs.freelance_platform.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> { }
