package com.mrp.service;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrp.exceptionhandling.BusinessException;
import com.mrp.model.Dependents;
import com.mrp.model.Member;
import com.mrp.repository.DependentsRepository;
import com.mrp.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private DependentsRepository dependentsRepository;

	@Override
	public Member register(Member member) throws BusinessException {
		log.info("Registering member " + member.getMemberFirstName() + "...");
		
		if(!validMemberArguments(member)) {
			log.error("Invalid details for member registration, please provide valid details. " + member);
			throw new BusinessException("400", "Invalid details for member registration, please provide valid details");
		}
		log.info("Member details validated, proceeding for registration...");
		
		Member existingMember = memberRepository.findByMemberEmailId(member.getMemberEmailId());
		if(existingMember.getMemberIsRegistered()) {
			log.error("Member is already registered. " + member);
			throw new BusinessException("400", "Member is already registered");
		}
		existingMember.setMemberContactNo(member.getMemberContactNo());
		existingMember.setMemberPAN(member.getMemberPAN());
		existingMember.setMemberCountry(member.getMemberCountry());
		existingMember.setMemberState(member.getMemberState());
		existingMember.setMemberAddress(member.getMemberAddress());
		
		existingMember.setMemberActivationDate(LocalDate.now());
		existingMember.setMemberIsRegistered(true);
		
		
		return memberRepository.save(existingMember);
		
	}

	@Override
	public Member addDependent(String memberId, Dependents dependent) throws BusinessException {
		log.info("Adding dependent " + dependent + "...");
		Member member = memberRepository.findByMemberId(memberId);
		
		if(member == null) {
			log.error("Member does not exist ");
			throw new BusinessException("400", "Member does not exist.");
		}
		if(!member.getMemberIsRegistered()) {
			log.error("Please register first, then add the dependents. ");
			throw new BusinessException("400", "Please register first, then add the dependents.");
		}
		if(member.getMemberDependents().size() == 2) {
			log.error("Cannot add dependent, maximum dependents limit reached: " + member.getMemberDependents().size());
			throw new BusinessException("400", "Cannot add dependent, maximum dependents limit reached");
		}
		
		if(!validDependentArguments(dependent)) {
			log.error("Invalid dependent details, please provide valid details. " + member);
			throw new BusinessException("400", "Invalid dependent details, please provide valid details");
		}
		
		dependent.setDependentAge(Period.between(dependent.getDependentDOB(), LocalDate.now()).getYears());
		dependent.setMember(member);
		dependent = dependentsRepository.save(dependent);
		member.addDependent(dependent);
		log.info("Added dependent in database... " + dependent.getDependentId());
		
		return memberRepository.findByMemberId(memberId);
	}
	
	@Override
	public Member removeDependent(String memberId, String dependentId) {
		log.info("Removing dependent " + dependentId);
		Member member = memberRepository.findByMemberId(memberId);
		
		if(member == null) {
			log.error("No such member exist: " + memberId);
			throw new BusinessException("400", "No such member exist");
		}
		
		log.info("Member " + member.getMemberId() + " currently has " + member.getMemberDependents().size() + " dependents.");
		
		Dependents dependent = dependentsRepository.findByDependentId(dependentId);
		if(dependent == null) {
			log.error("No such dependent exist: " + dependentId);
			throw new BusinessException("400", "No such dependent exist");
		}
		if(!dependent.getMember().getMemberId().equals(memberId)) {
			log.error("Cannot remove dependent: " + dependentId + " for member: " + memberId);
			throw new BusinessException("400", "Cannot remove dependent from the provided member");
		}
		dependentsRepository.delete(dependent);
		log.info("Getting member details for " + memberId);
		member.getMemberDependents().remove(dependent);
		return member;
	}

	private boolean validDependentArguments(Dependents dependent) {
		if(dependent.getDependentName() != null && 
				dependent.getDependentDOB().compareTo(LocalDate.now()) < 0) {
			return true;
		}
		return false;
	}

	private boolean validMemberArguments(Member member) {
		
		if(member.getMemberContactNo() != null && member.getMemberPAN() != null &&
				member.getMemberCountry() != null && member.getMemberState() != null && member.getMemberAddress() != null &&
				member.getMemberContactNo().toString().length() == 10 && 
				member.getMemberPAN().length() == 12) {
			return true;
		}
		
		return false;
	}

	@Override
	public Member findByMemberId(String memberId) {
		return memberRepository.findByMemberId(memberId);
	}

	@Override
	public Member editDependent(String memberId, Dependents dependent) {
		log.info("Modifying dependent details for " + dependent + "...");
		Member member = memberRepository.findByMemberId(memberId);
		
		if(member == null) {
			log.error("Member doest not exist ");
			throw new BusinessException("400", "Member does not exist.");
		}
		if(!member.getMemberIsRegistered()) {
			log.error("Please register first, then edit the dependents. ");
			throw new BusinessException("400", "Please register first, then add the dependents.");
		}
		if(dependent.getDependentId() == null || dependent.getDependentId().length() != 5 || !dependent.getDependentId().startsWith("R-")) {
			log.error("Invalid dependent id, please provide correct dependent id. ");
			throw new BusinessException("400", "Invalid dependent id, please provide correct dependent id.");
		}
		
		if(!validDependentArguments(dependent)) {
			log.error("Invalid dependent details, please provide valid details. " + member);
			throw new BusinessException("400", "Invalid dependent details, please provide valid details");
		}
		
		dependent.setDependentAge(Period.between(dependent.getDependentDOB(), LocalDate.now()).getYears());
		dependent.setMember(member);
		dependent = dependentsRepository.save(dependent);
		//member.addDependent(dependent);
		
		for(Dependents originalDependent: member.getMemberDependents()) {
			if(originalDependent.getDependentId().equals(dependent.getDependentId())) {
				
			}
		}
		
		log.info("Modified dependent in database... " + dependent.getDependentId());
		
		return memberRepository.findByMemberId(memberId);
	}

	@Override
	public Member updateMember(String memberId, Member member) {
		log.info("Updating member " + member + "...");
		if(member.getMemberId() == null) {
			log.error("Please provide a valid member id to perform update");
			throw new BusinessException("400", "Please provide a valid member id to perform update.");
		}
		Member existingMember = memberRepository.findByMemberId(member.getMemberId());
		if(existingMember == null) {
			log.error("Member with the provided member id does not exist ");
			throw new BusinessException("400", "Member with the provided member id does not exist.");
		}
		
		if(!validMemberArguments(member) || member.getMemberEmailId() == null || 
				!member.getMemberEmailId().contains("@")) {
			log.error("Invalid dependent details, please provide valid details. " + member);
			throw new BusinessException("400", "Invalid dependent details, please provide valid details");
		}
		
		if(!member.getMemberEmailId().equals(existingMember.getMemberEmailId()) && 
				memberRepository.findByMemberEmailId(member.getMemberEmailId()) != null) {
			log.error("Email id is already registered: " + member.getMemberEmailId());
			throw new BusinessException("400", "Email id is already registered, please use a different email id");
		}
		member.setMemberPassword(existingMember.getMemberPassword());
		
		
		log.info("Updating member in database... " + member.getMemberId());
		
		return memberRepository.save(member);
	}

}
