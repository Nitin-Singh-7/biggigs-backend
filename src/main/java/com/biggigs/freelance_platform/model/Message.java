package com.biggigs.freelance_platform.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Order order; /// link to order
	
	@ManyToOne
	private User sender; // who send the message
	
	@Column(length = 2000)
	private String content;
	
	private LocalDateTime sentAt;
	
	@PrePersist
	public void prePersist() {
		this.sentAt = LocalDateTime.now();
	}
	
	// Getters and setters
	 public Long getId() { return id; }
	 public Order getOrder() { return order; }
	 public void setOrder(Order order) { this.order = order; }
	 public User getSender() { return sender; }
	 public void setSender(User sender) { this.sender = sender; }
	 public String getContent() { return content; }
	 public void setContent(String content) { this.content = content; }
	 public LocalDateTime getSentAt() { return sentAt; }
	 public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
}

