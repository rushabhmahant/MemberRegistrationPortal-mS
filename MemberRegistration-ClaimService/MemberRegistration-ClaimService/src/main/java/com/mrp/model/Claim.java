package com.mrp.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "claim")
@SequenceGenerator(name = "claimIdGenerator", sequenceName = "claimIdGenerator",  initialValue = 1000000000)
public class Claim {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "claimIdGenerator")
	private Long claimId;
	@Column(nullable = false)
	private String memberId;
	@Column(nullable = false)
	private String dependentId = "self";	// 'self' for self claim, dependentId for dependent's claim
	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;
	@Column(nullable = false)
	private LocalDate dateOfBirth;
	@Column(nullable = false)
	private Integer age;
	@Column(nullable = false)
	private LocalDate dateOfAdmission;
	@Column(nullable = false)
	private LocalDate dateOfDischarge;
	@Column(nullable = false)
	private String providerName;
	@Column(nullable = false)
	private Double totalBillAmount;
	
	
}
