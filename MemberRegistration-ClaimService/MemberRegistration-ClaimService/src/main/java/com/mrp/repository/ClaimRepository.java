package com.mrp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mrp.model.Claim;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {
	
	Claim findByClaimId(Long claimId);
	
	@Query("SELECT c FROM Claim c where c.memberId=:memberId")
	List<Claim> getAllMemberClaims(String memberId);

}
