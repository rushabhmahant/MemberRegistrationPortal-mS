package com.mrp.service;

import com.mrp.model.Member;

public interface OnboardService {
	
	public Member authenticate(Member member);

	public Member signup(Member member);

}
