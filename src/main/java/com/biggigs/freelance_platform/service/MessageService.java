package com.biggigs.freelance_platform.service;

import com.biggigs.freelance_platform.model.*;
import com.biggigs.freelance_platform.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class MessageService {
	private final MessageRepository messageRepository;
	private final OrderRepository orderRepository;
	private final AuthService authService;
	
	public MessageService(MessageRepository messageRepository, OrderRepository orderRepository, AuthService authService) {
		this.messageRepository = messageRepository;
		this.orderRepository = orderRepository;
		this.authService = authService;
	}
	
	public Message sendMessage(String token, Long orderId, String content) {
		User sender = authService.getUserByToken(token);
		
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
		
		// Ensure only client or freelancer from this order can send
		if (!(Objects.equals(order.getClient().getId(), sender.getId()) || 
		      Objects.equals(order.getGig().getFreelancer().getId(), sender.getId()))) {
		    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not part of this order");
		}
		
		Message msg = new Message();
		msg.setOrder(order);
		msg.setSender(sender);
		msg.setContent(content);
		
		return messageRepository.save(msg);

	}
	
	public List<Message> getMessages(String token, Long orderId){
		User requester = authService.getUserByToken(token);
		
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
		
		if (!(Objects.equals(order.getClient().getId(), requester.getId()) || 
			      Objects.equals(order.getGig().getFreelancer().getId(), requester.getId()))) {
			    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not part of this order");
		}
		
		return messageRepository.findByOrderIdOrderBySentAtAsc(orderId);
	}
	
}
