package com.mrp.service;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrp.exceptionhandling.BusinessException;
import com.mrp.model.Member;
import com.mrp.repository.MemberRepository;
import com.mrp.utils.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OnboardServiceImpl implements OnboardService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Member authenticate(Member member) {
		String memberEmailId = member.getMemberEmailId();
		if(memberEmailId == null || !memberEmailId.contains("@")) {
			log.error("Invalid username or pasword! " + member);
			throw new BusinessException("400", "Invalid username or password");
		}
		Member memberFound = memberRepository.findByMemberEmailId(memberEmailId);
		if(memberFound == null || !member.getMemberPassword().equals(passwordEncoder.decodePassword(memberFound.getMemberPassword()))) {
			log.error("Incorrect username or pasword! " + member);
			throw new BusinessException("400", "Incorrect username or pasword!");
		}
		
		return memberFound;
	}

	@Override
	public Member signup(Member member) {
		log.info("Signing up member... " + member);
		if(!validMemberArguments(member)) {
			log.error("Invalid details for member signup, please provide valid details. " + member);
			throw new BusinessException("400", "Invalid details for member signup, please provide valid details");
		}
		if(Period.between(member.getMemberDOB(), LocalDate.now()).getYears() < 18) {
			log.error("Cannot signup, member should be atleast 18 years old! " + member);
			throw new BusinessException("400", "Cannot signup, member should be atleast 18 years old!");
		}
		log.info("Member details are validated, checking if Email id already exists");
		
		if(memberRepository.findByMemberEmailId(member.getMemberEmailId()) != null) {
			log.error("Email id is already registered: " + member.getMemberEmailId());
			throw new BusinessException("400", "Email id is already registered, please use a different email id");
		}
		
		member.setMemberAge(Period.between(member.getMemberDOB(), LocalDate.now()).getYears());
		member.setMemberPassword(passwordEncoder.encodePassword(member.getMemberPassword()));
		log.info("Storing member details in database... " + member);
		return memberRepository.save(member);
	}

	private boolean validMemberArguments(Member newMember) {
		if(newMember.getMemberFirstName() != null && newMember.getMemberLastName() != null &&
				!newMember.getMemberFirstName().isBlank() && !newMember.getMemberLastName().isBlank() && 
				newMember.getMemberEmailId() != null && !newMember.getMemberEmailId().isBlank() && newMember.getMemberEmailId().contains("@") && 
				newMember.getMemberPassword() != null && !newMember.getMemberPassword().isBlank() && newMember.getMemberPassword().length()>=8) {
			if(newMember.getMemberDOB() != null && newMember.getMemberDOB().compareTo(LocalDate.now()) <= 0) {
				return true;
			}
		}
		return false;
	}

}
