package com.biggigs.freelance_platform.repository;


import com.biggigs.freelance_platform.model.Gig;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface GigRepository extends JpaRepository<Gig, Long>{
	List<Gig> findByFreelancerId(Long freelancerId);
}