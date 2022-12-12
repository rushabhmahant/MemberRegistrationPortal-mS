package com.mrp;

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

import com.mrp.model.Claim;
import com.mrp.repository.ClaimRepository;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClaimRepositoryTests {
	
	@Autowired
    private ClaimRepository claimRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void submitClaim(){

        Claim member = Claim.builder()
                .dateOfAdmission(LocalDate.now())
                .dateOfDischarge(LocalDate.now())
                .dateOfBirth(LocalDate.of(2002, 12, 7))
                .age(20)
                .firstName("Test")
                .lastName("Claim")
                .memberId("R-001")
                .dependentId("self")
                .providerName("Adorn Asthetics")
                .totalBillAmount(10000.00)
                .build();

        claimRepository.save(member);
        
        Assertions.assertThat(member.getMemberId()).startsWith("R-");
    }

    @Test
    @Order(2)
    public void getAllClaims(){

        List<Claim> claims = claimRepository.findAll();
        
        Assertions.assertThat(claims.size()).isGreaterThan(0);

    }
    
    @Test
    @Order(3)
    @Rollback(value = false)
    public void deleteClaims(){

        List<Claim> claims = claimRepository.getAllMemberClaims("R-001");
        claimRepository.deleteById(claims.get(0).getClaimId());

        List<Claim> updatedClaims= claimRepository.getAllMemberClaims("R-001");

        Assertions.assertThat(updatedClaims.size()).isEqualTo(0);

    }

}
