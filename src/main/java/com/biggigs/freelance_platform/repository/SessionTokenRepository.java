package com.biggigs.freelance_platform.repository;

import com.biggigs.freelance_platform.model.SessionToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface SessionTokenRepository extends JpaRepository<SessionToken, Long>{
	Optional<SessionToken> findByToken(String token);
}
