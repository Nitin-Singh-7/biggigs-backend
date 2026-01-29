package com.biggigs.freelance_platform.controller;


import com.biggigs.freelance_platform.model.Gig;
import com.biggigs.freelance_platform.service.GigService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gigs")
public class GigController {
	private final GigService gigService;
	
	public GigController(GigService gigService) {
		this.gigService = gigService;
	}
	
	@PostMapping
	public ResponseEntity<Gig> CreateGig(@RequestHeader("X-Auth-Token") String token,
										  @RequestBody Gig gig){
		return ResponseEntity.ok(gigService.createGig(token, gig));
	}
	
	@GetMapping
	public ResponseEntity<List<Gig>> getAllGigs() {
        return ResponseEntity.ok(gigService.getAllGigs());
    }
	
	 @GetMapping("/{id}")
	    public ResponseEntity<Gig> getGig(@PathVariable Long id) {
	        return ResponseEntity.ok(gigService.getGigById(id));
	    }
	
	@PutMapping("/{id}")
	public ResponseEntity<Gig> updateGig(@RequestHeader("X-Auth-Token") String token,
										 @PathVariable Long id,
										 @RequestBody Gig gig){
		return ResponseEntity.ok(gigService.updateGig(token, id, gig));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteGig(@RequestHeader("X-Auth-Token") String token,
									   @PathVariable Long id){
		gigService.deleteGig(token, id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/mine")
	public ResponseEntity<List<Gig>> myGigs(@RequestHeader("X-Auth-Token") String Token){
		return ResponseEntity.ok(gigService.getMyGigs(Token));
		}
}
