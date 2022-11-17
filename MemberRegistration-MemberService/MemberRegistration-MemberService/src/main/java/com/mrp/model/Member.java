package com.mrp.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.mrp.utils.MemberIdGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "memberIdGenerator")
	@GenericGenerator(name = "memberIdGenerator", 
			strategy = "com.mrp.utils.MemberIdGenerator",
			parameters = {
					@Parameter(name = MemberIdGenerator.INCREMENT_PARAM, value = "1"),
					@Parameter(name = MemberIdGenerator.INITIAL_PARAM, value = "101"),
					@Parameter(name = MemberIdGenerator.VALUE_PREFIX_PARAMETER, value = "R-"),
					@Parameter(name = MemberIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%03d")
			})
	private String memberId;	//	Format R-XXX
	
	/*
	 * Member fields for signup
	 */
	@Column(nullable = false, unique = true)
	private String memberEmailId;
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(nullable = false)
	private String memberPassword;
	@Column(nullable = false)
	private String memberFirstName;
	@Column(nullable = false)
	private String memberLastName;
	@Column(nullable = false)
	private LocalDate memberDOB;
	
	/*
	 * Member fields for registration
	 */
	private Long memberContactNo;
	private String memberPAN;
	private Integer memberAge;
	private LocalDate memberActivationDate;
	
	@Column(nullable = false)
	private Boolean memberIsRegistered = false;		//	Initially set to false while signing up
	
	/*
	 * One to many relation between member and dependents
	 */
	@OneToMany(mappedBy = "member")
	private Set<Dependents> memberDependents = new HashSet<Dependents>();
	
	public void addDependent(Dependents dependent) {
		this.memberDependents.add(dependent);
	}
	

}
