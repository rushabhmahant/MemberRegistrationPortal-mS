package com.mrp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrp.exceptionhandling.BusinessException;
import com.mrp.exceptionhandling.ControllerException;
import com.mrp.model.Dependents;
import com.mrp.model.Member;
import com.mrp.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/member")
@CrossOrigin
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody Member member){
		try {
			Member registeredMember = memberService.register(member);
			return new ResponseEntity<Member>(registeredMember, HttpStatus.OK);
		}catch(BusinessException be) {
			ControllerException ce = new ControllerException(be.getErrorCode(), be.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			log.error("Exception occurred while member registration: ", e);
			ControllerException ce = new ControllerException("500", "Internal Server Error: " + e.getMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/{memberId}/add-dependent")
	public ResponseEntity<?> addDependent(@PathVariable String memberId, @RequestBody Dependents dependent){
		try {
			Member updatedMember = memberService.addDependent(memberId, dependent);
			return new ResponseEntity<Member>(updatedMember, HttpStatus.OK);
		}catch(BusinessException be) {
			ControllerException ce = new ControllerException(be.getErrorCode(), be.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			log.error("Exception occurred while adding dependent: ", e);
			ControllerException ce = new ControllerException("500", "Internal Server Error: " + e.getMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/{memberId}/edit-dependent")
	public ResponseEntity<?> editDependent(@PathVariable String memberId, @RequestBody Dependents dependent){
		try {
			Member updatedMember = memberService.editDependent(memberId, dependent);
			return new ResponseEntity<Member>(updatedMember, HttpStatus.OK);
		}catch(BusinessException be) {
			ControllerException ce = new ControllerException(be.getErrorCode(), be.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			log.error("Exception occurred while adding dependent: ", e);
			ControllerException ce = new ControllerException("500", "Internal Server Error: " + e.getMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/{memberId}/remove-dependent/{dependentId}")
	public ResponseEntity<?> removeDependent(@PathVariable String memberId, @PathVariable String dependentId) {
		try {
			Member updatedMember = memberService.removeDependent(memberId, dependentId);
			return new ResponseEntity<Member>(updatedMember, HttpStatus.OK);
		}catch(BusinessException be) {
			ControllerException ce = new ControllerException(be.getErrorCode(), be.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			log.error("Exception occurred while removing dependent: ", e);
			ControllerException ce = new ControllerException("500", "Internal Server Error: " + e.getMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{memberId}")
	public ResponseEntity<?> findByMemberId(@PathVariable String memberId){
		try {
			return new ResponseEntity<Member>(memberService.findByMemberId(memberId),
					HttpStatus.OK);
		}catch(BusinessException be) {
			ControllerException ce = new ControllerException(be.getErrorCode(), be.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			log.error("Exception occurred while finding member: ", e);
			ControllerException ce = new ControllerException("500", "Internal Server Error: " + e.getMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
