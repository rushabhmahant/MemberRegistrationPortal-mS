package com.mrp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrp.exceptionhandling.BusinessException;
import com.mrp.exceptionhandling.ControllerException;
import com.mrp.model.Member;
import com.mrp.service.OnboardService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/onboard")
public class OnboardController {
	
	@Autowired
	private OnboardService onboardService;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticate(@RequestBody Member member){
		try {
			Member foundMember = onboardService.authenticate(member);
			return new ResponseEntity<Member>(foundMember, HttpStatus.OK);
		}catch(BusinessException be) {
			ControllerException ce = new ControllerException(be.getErrorCode(), be.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			ControllerException ce = new ControllerException("500", "Internal Server Error: " + e.getMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody Member member){
		try {
			Member newMember = onboardService.signup(member);
			return new ResponseEntity<Member>(newMember, HttpStatus.OK);
		}catch(BusinessException be) {
			ControllerException ce = new ControllerException(be.getErrorCode(), be.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			log.error("Exception occurred while signing up user: ", e);
			ControllerException ce = new ControllerException("500", "Internal Server Error: "+ e);
			return new ResponseEntity<ControllerException>(ce, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}
