package com.biggigs.freelance_platform.model.enums;

public enum OrderStatus {
    PENDING,      // order placed, waiting for freelancer to accept
    ACCEPTED,     // freelancer accepted the order
    DELIVERED,    // freelancer delivered work
    COMPLETED,    // client approved delivery, freelancer paid
    REJECTED      // order rejected or cancelled
}
