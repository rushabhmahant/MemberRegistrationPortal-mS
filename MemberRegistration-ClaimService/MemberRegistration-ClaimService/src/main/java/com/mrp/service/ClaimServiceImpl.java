package com.mrp.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrp.exceptionhandling.BusinessException;
import com.mrp.model.Claim;
import com.mrp.repository.ClaimRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClaimServiceImpl implements ClaimService {
	
	@Autowired
	private ClaimRepository claimRepository;
	
	@Override
	public List<Claim> getAllClaims() {
		log.info("Fetching all claims from database...");
		return claimRepository.findAll();
	}

	@Override
	public List<Claim> getMemberClaims(String memberId) {
		log.info("Fetching claims for member " + memberId + " from database...");
		if(memberId == null || memberId.length() != 5 || !memberId.startsWith("R-")) {
			log.error("Incorrect member id provided, please provide correct member id");
			throw new BusinessException("400", "Incorrect member id provided, please provide correct member id");
		}
		return claimRepository.getAllMemberClaims(memberId);
	}

	@Override
	public Claim submitClaim(Claim claim) {
		
		if(!validClaimArguments(claim)) {
			log.error("Incorrect claim details, please provide valid details for submitting claim: " + claim);
			throw new BusinessException("400", "Incorrect claim details, please provide valid details for submitting claim");
		}
		
		if(claim.getDateOfAdmission().compareTo(claim.getDateOfDischarge()) > 0) {
			log.error("Discharge date should fall on or after admission date: Admission Date: " + 
					claim.getDateOfAdmission() + ", Discharge Date: " + claim.getDateOfDischarge());
			throw new BusinessException("400", "Discharge date should fall on or after admission date");
		}
		
		if(claim.getDependentId() == null || claim.getDependentId().length() != 5 || !claim.getDependentId().startsWith("R-")) {
			claim.setDependentId("self");
		}
		
		return claimRepository.save(claim);
		
	}

	private boolean validClaimArguments(Claim claim) {
		return Stream.of(claim.getDateOfAdmission(), claim.getDateOfBirth(), claim.getDateOfDischarge(),
				claim.getFirstName(), claim.getLastName())
				.allMatch(Objects::isNull);
	}

}
