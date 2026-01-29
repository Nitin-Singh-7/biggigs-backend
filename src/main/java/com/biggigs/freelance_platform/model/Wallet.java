package com.biggigs.freelance_platform.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Wallet {
	 @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @OneToOne
	    @JoinColumn(name = "user_id", unique = true)
	    private User user;

	    @Column(nullable = false, precision = 12, scale = 2)
	    private BigDecimal balance = BigDecimal.ZERO;

	    private LocalDateTime updatedAt = LocalDateTime.now();

	    public Wallet() {}
	    public Wallet(User user) { this.user = user; }

	    // getters/setters
	    public Long getId() { return id; }
	    public User getUser() { return user; }
	    public void setUser(User user) { this.user = user; }
	    public BigDecimal getBalance() { return balance; }
	    public void setBalance(BigDecimal balance) { this.balance = balance; }
	    public LocalDateTime getUpdatedAt() { return updatedAt; }
	    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
