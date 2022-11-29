package com.mrp.service;

import com.mrp.exceptionhandling.BusinessException;
import com.mrp.model.Dependents;
import com.mrp.model.Member;

public interface MemberService {

	Member register(Member member) throws BusinessException;

	Member addDependent(String memberId, Dependents dependent) throws BusinessException;

	Member findByMemberId(String memberId);

	Member removeDependent(String memberId, String dependentId);

	Member editDependent(String memberId, Dependents dependent);

}
