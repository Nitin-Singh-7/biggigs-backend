package com.biggigs.freelance_platform.model;

import com.biggigs.freelance_platform.model.enums.PaymentMethod;
import com.biggigs.freelance_platform.model.enums.PaymentStatus;
import com.biggigs.freelance_platform.model.enums.PaymentType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.SUCCESS;

    @Enumerated(EnumType.STRING)
    private PaymentType type; // CREDIT or DEBIT

    private String reference; // e.g., "DEPOSIT-2025-..."; can be UUID
    private LocalDateTime createdAt = LocalDateTime.now();

    // Optional: link a payment to an order (for debits)
    @ManyToOne
    private Order orderRef;

    // getters/setters
    public Long getId() { return id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public PaymentMethod getMethod() { return method; }
    public void setMethod(PaymentMethod method) { this.method = method; }
    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }
    public PaymentType getType() { return type; }
    public void setType(PaymentType type) { this.type = type; }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Order getOrderRef() { return orderRef; }
    public void setOrderRef(Order orderRef) { this.orderRef = orderRef; }
}
