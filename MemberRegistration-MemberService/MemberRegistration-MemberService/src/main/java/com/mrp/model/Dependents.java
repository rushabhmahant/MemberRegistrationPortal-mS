package com.mrp.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mrp.utils.MemberIdGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dependents")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dependents {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dependentIdGenerator")
	@GenericGenerator(name = "dependentIdGenerator", 
			strategy = "com.mrp.utils.DependentIdGenerator",
			parameters = {
					@Parameter(name = MemberIdGenerator.INCREMENT_PARAM, value = "1"),
					@Parameter(name = MemberIdGenerator.INITIAL_PARAM, value = "501"),
					@Parameter(name = MemberIdGenerator.VALUE_PREFIX_PARAMETER, value = "R-"),
					@Parameter(name = MemberIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%03d")
			})
	private String dependentId;
	@Column(nullable = false)
	private String dependentName;
	@Column(nullable = false)
	private LocalDate dependentDOB;
	@Column(nullable = false)
	private Integer dependentAge;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "member_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Member member;

}
