package com.biggigs.freelance_platform.repository;

import com.biggigs.freelance_platform.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface MessageRepository extends JpaRepository<Message, Long>{
	List<Message> findByOrderIdOrderBySentAtAsc(Long orderId);
}