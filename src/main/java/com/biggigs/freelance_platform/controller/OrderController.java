package com.biggigs.freelance_platform.controller;

import com.biggigs.freelance_platform.model.Gig;
import com.biggigs.freelance_platform.model.Order;
import com.biggigs.freelance_platform.model.User;
import com.biggigs.freelance_platform.service.AuthService;
import com.biggigs.freelance_platform.service.GigService;
import com.biggigs.freelance_platform.service.OrderService;
import com.biggigs.freelance_platform.service.PaymentService;
import com.biggigs.freelance_platform.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final GigService gigService;
    private final AuthService authService;
    private final PaymentService paymentService;
    private final WalletService walletService;

    // Constructor injection
    public OrderController(OrderService orderService,
                           GigService gigService,
                           AuthService authService,
                           PaymentService paymentService,
                           WalletService walletService) {
        this.orderService = orderService;
        this.gigService = gigService;
        this.authService = authService;
        this.paymentService = paymentService;
        this.walletService = walletService;
    }

    // -------- Existing endpoints --------

    // place an order (client)
    @PostMapping("/place/{gigId}")
    public ResponseEntity<Order> placeOrder(@RequestHeader("X-Auth-Token") String token,
                                            @PathVariable Long gigId) {
        return ResponseEntity.ok(orderService.placeOrder(token, gigId));
    }

    // Get all orders of the client
    @GetMapping("/client")
    public ResponseEntity<List<Order>> getClientOrders(@RequestHeader("X-Auth-Token") String token){
        return ResponseEntity.ok(orderService.getClientOrders(token));
    }

    // Get all orders received by freelancers
    @GetMapping("/freelancer")
    public ResponseEntity<List<Order>> getFreelancerOrders(@RequestHeader("X-Auth-Token") String token){
        return ResponseEntity.ok(orderService.getFreelancerOrders(token));
    }

    // freelancer updates order status
    @PutMapping("/update/{orderId}")
    public ResponseEntity<Order> updateOrderStatus(@RequestHeader("X-Auth-Token") String token,
                                                   @PathVariable Long orderId,
                                                   @RequestParam String status){
        return ResponseEntity.ok(orderService.updateOrderStatus(token, orderId, status));
    }

    // -------- New: place order AND charge wallet in one go --------
    @PostMapping("/place-with-payment/{gigId}")
    @Transactional
    public ResponseEntity<?> placeOrderWithPayment(@RequestHeader("X-Auth-Token") String token,
                                                   @PathVariable Long gigId) {
        // 1) Identify client
        User client = authService.getUserByToken(token);

        // 2) Find gig & price
        Gig gig = gigService.getGigById(gigId);
        BigDecimal price = BigDecimal.valueOf(gig.getPrice());

        // 3) Create order using your existing service
        Order order = orderService.placeOrder(token, gigId);

        // 4) Debit wallet & record payment
        paymentService.chargeForOrder(client, price, order);

        // 5) Response with updated balance
        BigDecimal newBalance = walletService.getOrCreate(client).getBalance();

        Map<String, Object> resp = new HashMap<>();
        resp.put("orderId", order.getId());
        resp.put("gigId", gig.getId());
        resp.put("price", price);
        resp.put("walletBalance", newBalance);

        return ResponseEntity.ok(resp);
    }
    
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalState(IllegalStateException ex) {
        return ResponseEntity.badRequest().body(java.util.Map.of("error", ex.getMessage()));
    }

}
