package com.biggigs.freelance_platform.controller;

import com.biggigs.freelance_platform.dto.DepositRequest;
import com.biggigs.freelance_platform.dto.WalletResponse;
import com.biggigs.freelance_platform.model.User;
import com.biggigs.freelance_platform.model.Wallet;
import com.biggigs.freelance_platform.service.AuthService;
import com.biggigs.freelance_platform.service.PaymentService;
import com.biggigs.freelance_platform.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private final AuthService authService;
    private final WalletService walletService;
    private final PaymentService paymentService;

    public WalletController(AuthService authService, WalletService walletService, PaymentService paymentService) {
        this.authService = authService;
        this.walletService = walletService;
        this.paymentService = paymentService;
    }

    @GetMapping("/me")
    public ResponseEntity<WalletResponse> myWallet(@RequestHeader("X-Auth-Token") String token) {
        User u = authService.getUserByToken(token);
        Wallet w = walletService.getOrCreate(u);
        return ResponseEntity.ok(new WalletResponse(w.getBalance()));
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestHeader("X-Auth-Token") String token,
                                     @Valid @RequestBody DepositRequest req) {
        User u = authService.getUserByToken(token);
        paymentService.deposit(u, req.getAmount(), req.getMethod());
        Wallet w = walletService.getOrCreate(u);
        return ResponseEntity.ok(new WalletResponse(w.getBalance()));
    }
}
