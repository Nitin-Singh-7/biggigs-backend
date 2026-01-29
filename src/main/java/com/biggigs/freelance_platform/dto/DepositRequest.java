package com.biggigs.freelance_platform.dto;

import com.biggigs.freelance_platform.model.enums.PaymentMethod;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class DepositRequest {
    @NotNull @Min(1)
    private BigDecimal amount;

    @NotNull
    private PaymentMethod method;

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public PaymentMethod getMethod() { return method; }
    public void setMethod(PaymentMethod method) { this.method = method; }
}
