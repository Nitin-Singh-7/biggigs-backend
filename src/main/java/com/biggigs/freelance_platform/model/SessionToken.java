package com.biggigs.freelance_platform.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "session_tokens")
public class SessionToken {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String token;
	
	@ManyToOne
	private User user;
	
	private LocalDateTime createdAt;
	private LocalDateTime expiresAt;
	
	public SessionToken() {}
	
	public SessionToken(String token, User user, LocalDateTime createdAt, LocalDateTime expiresAt) {
		this.token = token;
		this.user = user;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
	}
	
	// getters and setters
	public Long getId() {return id;}
	public String getToken() {return token;}
	public void setToken(String token) {this.token = token;}
	
	public User getUser() {return user;}
	public void setUser(User user) {this.user = user;}
	
	public LocalDateTime getCreatedAt() {return createdAt;}
	public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}
	
	public LocalDateTime getExpiresAt() {return expiresAt;}
	public void setExpiresAt(LocalDateTime expiresAt) {this.expiresAt = expiresAt;}
	
}
