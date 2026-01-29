package com.biggigs.freelance_platform.controller;

import com.biggigs.freelance_platform.model.Message;
import com.biggigs.freelance_platform.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
	
	private final MessageService messageService;
	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}
	
	@PostMapping("/{orderId}")
	public ResponseEntity<Message> sendMessage(@RequestHeader("X-Auth-Token") String token, 
			@PathVariable Long orderId,
			@RequestBody String content){
		return ResponseEntity.ok(messageService.sendMessage(token,orderId, content));
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<List<Message>> getMessages(@RequestHeader("X-Auth-Token") String token,
													 @PathVariable Long orderId){
		return ResponseEntity.ok(messageService.getMessages(token, orderId));
	}
}
