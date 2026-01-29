package com.biggigs.freelance_platform.service;

import com.biggigs.freelance_platform.model.Gig;
import com.biggigs.freelance_platform.model.User;
import com.biggigs.freelance_platform.repository.GigRepository;
import com.biggigs.freelance_platform.service.AuthService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;


@Service
public class GigService {
	private final GigRepository gigRepository;
	private final AuthService authService;
	
	
	public GigService(GigRepository gigRepository, AuthService authService) {
		this.gigRepository = gigRepository;
		this.authService = authService;
	}
	
	public Gig createGig(String token, Gig gig) {
		User freelancer = authService.getUserByToken(token);
		
		if(!"FREELANCER".equalsIgnoreCase(freelancer.getRole())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only Freelancers can post gigs");
		}
		gig.setFreelancer(freelancer);
		return gigRepository.save(gig);
	}
	
	public List<Gig> getAllGigs(){
		return gigRepository.findAll();
	}
	
	public Gig getGigById(Long id) {
		return gigRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Gig not found"));
	}
	
	public Gig updateGig(String token, Long id, Gig updatedGig) {
		User freelancer = authService.getUserByToken(token);
		Gig existing = getGigById(id);
		
		if (!Objects.equals(existing.getFreelancer().getId(), freelancer.getId())) {
		    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can update your own gigs");
		}
		
		existing.setTitle(updatedGig.getTitle());
		existing.setDescription(updatedGig.getDescription());
		existing.setPrice(updatedGig.getPrice());
		existing.setTimeline(updatedGig.getTimeline());
		
		return gigRepository.save(existing);
	}
	
	public void deleteGig(String token, Long id) {
		User freelancer = authService.getUserByToken(token);
		Gig gig = getGigById(id);
		
		if (!Objects.equals(gig.getFreelancer().getId(), freelancer.getId())) {
		    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only delete your own gigs");
		}
		
		gigRepository.delete(gig);
	}
	public List<Gig> getMyGigs(String token) {
	    User me = authService.getUserByToken(token);
	    // if your field name is 'freelancer' in Gig:
	    return gigRepository.findByFreelancerId(me.getId());
	    // If the method below doesn't exist or your field is named differently,
	    // use the JPQL version shown in step 3.
	}

}