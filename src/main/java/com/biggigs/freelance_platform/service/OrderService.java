package com.biggigs.freelance_platform.service;


import com.biggigs.freelance_platform.model.Gig;
import com.biggigs.freelance_platform.model.Order;
import com.biggigs.freelance_platform.model.User;
import com.biggigs.freelance_platform.repository.GigRepository;
import com.biggigs.freelance_platform.repository.OrderRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class OrderService {
	private final OrderRepository orderRepository;
    private final GigRepository gigRepository;
    private final AuthService authService;

    public OrderService(OrderRepository orderRepository, GigRepository gigRepository, AuthService authService) {
        this.orderRepository = orderRepository;
        this.gigRepository = gigRepository;
        this.authService = authService;
    }
	
    public Order placeOrder(String token, Long gigId) {
        User client = authService.getUserByToken(token);

        if (!"CLIENT".equalsIgnoreCase(client.getRole())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only clients can place orders");
        }

        Gig gig = gigRepository.findById(gigId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Gig not found"));

        Order order = new Order();
        order.setClient(client);
        order.setGig(gig);

        return orderRepository.save(order);
    }
    
    public List<Order> getClientOrders(String token) {
        User client = authService.getUserByToken(token);
        return orderRepository.findByClientId(client.getId());
    }

    public List<Order> getFreelancerOrders(String token) {
        User freelancer = authService.getUserByToken(token);
        return orderRepository.findByGigFreelancerId(freelancer.getId());
    }

    public Order updateOrderStatus(String token, Long orderId, String status) {
        User freelancer = authService.getUserByToken(token);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        if (!Objects.equals(order.getGig().getFreelancer().getId(), freelancer.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only the freelancer can update this order");
        }

        order.setStatus(status);
        return orderRepository.save(order);
    }
    
    
}
