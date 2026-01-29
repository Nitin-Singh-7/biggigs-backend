package com.biggigs.freelance_platform.service;

import com.biggigs.freelance_platform.dto.LoginRequest;
import com.biggigs.freelance_platform.dto.SignUpRequest;
import com.biggigs.freelance_platform.model.SessionToken;
import com.biggigs.freelance_platform.model.User;
import com.biggigs.freelance_platform.repository.SessionTokenRepository;
import com.biggigs.freelance_platform.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;


import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {
	private final UserRepository userRepository;
	private final SessionTokenRepository tokenRepository;
	private final PasswordEncoder passwordEncoder;
	
	public AuthService(UserRepository userRepository,
			SessionTokenRepository tokenRepository,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.tokenRepository = tokenRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public User signup(SignUpRequest req) {
		userRepository.findByEmail(req.getEmail())
			.ifPresent(u -> {throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already in use"); });
		
		String hashed = passwordEncoder.encode(req.getPassword());
		User user = new User(req.getName(), req.getEmail(), hashed, req.getRole().toUpperCase());
				return userRepository.save(user);
	}
	
	public String login(LoginRequest req) {
		User user = userRepository.findByEmail(req.getEmail())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid credentials"));
		
		if(!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credentials");
		}
		
		// create simple token
		String token = UUID.randomUUID().toString();
		LocalDateTime now = LocalDateTime.now();
		SessionToken sessionToken = new SessionToken(token, user, now, now.plusDays(7));
		tokenRepository.save(sessionToken);
		return token;
	}
	
	public User getUserByToken(String token) {
		SessionToken t = tokenRepository.findByToken(token)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token"));
		
		if(t.getExpiresAt() != null && t.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token Expired");
		}
		return t.getUser();
	}
}