package com.mrp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrp.model.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
	
	public Member findByMemberId(String memberId);
	
	public Member findByMemberEmailId(String memberEmailid);

}
