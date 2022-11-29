package com.mrp.service;

import java.util.List;

import com.mrp.model.Claim;

public interface ClaimService {
	
	public List<Claim> getAllClaims();
	
	public List<Claim> getMemberClaims(String memberId);
	
	public Claim submitClaim(Claim claim);

}
