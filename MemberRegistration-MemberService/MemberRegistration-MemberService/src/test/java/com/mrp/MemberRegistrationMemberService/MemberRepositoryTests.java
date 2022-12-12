package com.mrp.MemberRegistrationMemberService;

import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.mrp.model.Member;
import com.mrp.repository.MemberRepository;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberRepositoryTests {
	
	@Autowired
    private MemberRepository memberRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void onboardMemberTest(){

        Member member = Member.builder()
                .memberEmailId("tc2@mrp.com")
                .memberPassword("Testcase1@mrp")
                .memberFirstName("Jon")
                .memberLastName("Snow")
                .memberDOB(LocalDate.of(2006, 9, 02))
                .memberAge(16)
                .memberIsRegistered(false)
                .build();

        memberRepository.save(member);
        
        Assertions.assertThat(member.getMemberId()).startsWith("R-");
    }

    @Test
    @Order(2)
    public void getMemberTest(){

        Member member = memberRepository.findByMemberEmailId("tc2@mrp.com");
        
        Assertions.assertThat(member.getMemberEmailId()).isEqualTo("tc2@mrp.com");

    }

    @Test
    @Order(3)
    public void getListOfMembersTest(){

        List<Member> members = memberRepository.findAll();
        
        Assertions.assertThat(members.size()).isGreaterThan(0);

    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateMemberTest(){

        Member member = memberRepository.findByMemberEmailId("tc2@mrp.com");

        member.setMemberEmailId("tc2-updated@mrp.com");

        Member updatedMember =  memberRepository.save(member);

        Assertions.assertThat(updatedMember.getMemberEmailId()).isEqualTo("tc2-updated@mrp.com");

    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteMemberTest(){

        Member member = memberRepository.findByMemberEmailId("tc2-updated@mrp.com");
        
        memberRepository.delete(member);

        Member member1 = null;

        Member optionalMember = memberRepository.findByMemberEmailId("tc2-updated@mrp.com");

        if(optionalMember != null){
            member1 = optionalMember;
        }

        Assertions.assertThat(member1).isNull();
    }

}
