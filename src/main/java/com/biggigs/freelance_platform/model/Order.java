package com.biggigs.freelance_platform.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;



@Entity
@Table(name = "orders")
public class Order{
	
	
	// in model/Order.java
	//private java.time.LocalDateTime deliveredAt;
	//private String deliveryNote;      // optional
	//private String deliveryUrl;       // optional (e.g., Google Drive link)

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "client_id")
	private User client; // client placing order
	
	@ManyToOne
	@JoinColumn(name ="gig_id")
	private Gig gig; // gig being placed
	
	@Column(nullable = false)
	private String status; // pending, accepted, completed, cancelled
	
	private LocalDateTime createdAt;
	
	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
		if(status == null) status = "pending";
	}
	
	// Getters and setters
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	
	public User getClient() {return client;}
	public void setClient(User client) {this.client = client;}
	
	public Gig getGig() {return gig;}
	public void setGig(Gig gig) { this.gig = gig; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

