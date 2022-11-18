package com.mrp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mrp.model.Dependents;

@Repository
public interface DependentsRepository extends JpaRepository<Dependents, String> {

	Dependents findByDependentId(String dependentId);
	
	
	
}
