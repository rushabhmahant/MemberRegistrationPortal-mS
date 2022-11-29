package com.mrp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrp.exceptionhandling.BusinessException;
import com.mrp.exceptionhandling.ControllerException;
import com.mrp.model.Claim;
import com.mrp.service.ClaimService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/claimservices")
public class ClaimController {
	
	@Autowired
	private ClaimService claimService;
	
	@GetMapping("/get-all-claims")
	public ResponseEntity<?> getAllClaims(){
		try {
			List<Claim> listOfClaims = claimService.getAllClaims();
			return new ResponseEntity<List<Claim>>(listOfClaims, HttpStatus.OK);
		}catch(BusinessException be) {
			ControllerException ce = new ControllerException(be.getErrorCode(), be.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			log.error("Exception occurred while fetching all claims: ", e);
			ControllerException ce = new ControllerException("500", "Internal Server Error: " + e.getMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/get-member-claims/{memberId}")
	public ResponseEntity<?> getMemberClaims(@PathVariable String memberId){
		try {
			List<Claim> memberClaims = claimService.getMemberClaims(memberId);
			return new ResponseEntity<List<Claim>>(memberClaims, HttpStatus.OK);
		}catch(BusinessException be) {
			ControllerException ce = new ControllerException(be.getErrorCode(), be.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			log.error("Exception occurred while fetching member claims: ", e);
			ControllerException ce = new ControllerException("500", "Internal Server Error: " + e.getMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/submit-claim/{memberId}")
	public ResponseEntity<?> submitClaim(@RequestBody Claim claim){
		try {
			Claim newClaim = claimService.submitClaim(claim);
			return new ResponseEntity<Claim>(newClaim, HttpStatus.OK);
		}catch(BusinessException be) {
			ControllerException ce = new ControllerException(be.getErrorCode(), be.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			log.error("Exception occurred while submitting claim: ", e);
			ControllerException ce = new ControllerException("500", "Internal Server Error: " + e.getMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

}
