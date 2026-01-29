package com.biggigs.freelance_platform.service;

import com.biggigs.freelance_platform.model.Order;
import com.biggigs.freelance_platform.model.Payment;
import com.biggigs.freelance_platform.model.User;
import com.biggigs.freelance_platform.model.enums.PaymentMethod;
import com.biggigs.freelance_platform.model.enums.PaymentStatus;
import com.biggigs.freelance_platform.model.enums.PaymentType;
import com.biggigs.freelance_platform.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepo;
    private final WalletService walletService;

    public PaymentService(PaymentRepository paymentRepo, WalletService walletService) {
        this.paymentRepo = paymentRepo;
        this.walletService = walletService;
    }

    @Transactional
    public Payment deposit(User user, BigDecimal amount, PaymentMethod method) {
        // 1) credit wallet
        walletService.credit(user, amount);

        // 2) record payment
        Payment p = new Payment();
        p.setUser(user);
        p.setAmount(amount);
        p.setMethod(method);
        p.setStatus(PaymentStatus.SUCCESS);
        p.setType(PaymentType.CREDIT);
        p.setReference("DEPOSIT-" + UUID.randomUUID());
        return paymentRepo.save(p);
    }

    @Transactional
    public Payment chargeForOrder(User user, BigDecimal amount, Order order) {
        // 1) debit wallet
        walletService.debit(user, amount);

        // 2) record payment
        Payment p = new Payment();
        p.setUser(user);
        p.setAmount(amount);
        p.setMethod(PaymentMethod.UPI); // or null/optional for internal debit
        p.setStatus(PaymentStatus.SUCCESS);
        p.setType(PaymentType.DEBIT);
        p.setReference("ORDER-DEBIT-" + UUID.randomUUID());
        p.setOrderRef(order);
        return paymentRepo.save(p);
    }
}
