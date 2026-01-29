package com.biggigs.freelance_platform.controller;

import com.biggigs.freelance_platform.dto.LoginRequest;
import com.biggigs.freelance_platform.dto.SignUpRequest;
import com.biggigs.freelance_platform.dto.UserResponse;
import com.biggigs.freelance_platform.model.User;
import com.biggigs.freelance_platform.service.AuthService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {
	private final AuthService authService;
	public AuthController(AuthService authService) {this.authService = authService;}
	
	@PostMapping("/signup")
	public ResponseEntity<UserResponse> sigup(@Valid @RequestBody SignUpRequest req){
		User saved = authService.signup(req);
		UserResponse resp = new UserResponse(saved.getId(), saved.getName(), saved.getEmail(), saved.getRole());
		return ResponseEntity.ok(resp);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req){
		String token = authService.login(req);
		User u = authService.getUserByToken(token); // get user back
		
		UserResponse resp = new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getRole());
		// return token and user (client will store token)
		return ResponseEntity.ok().body(new java.util.HashMap<String, Object>() {{
			put("token", token);
			put("user", resp);
		}});
	}
	
	// Example endpoint to fetch current user by token header
	
	@GetMapping("/me")
	public ResponseEntity<User> getCurrentUser(@RequestHeader("X-Auth-Token") String token) {
	    User user = authService.getUserByToken(token);
	    return ResponseEntity.ok(user);
	}
	
	

}
