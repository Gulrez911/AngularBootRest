package com.ctet.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ctet.entity.Employee;


@Repository
public interface EmpRepository extends JpaRepository<Employee, Long> {

	@Query("SELECT c FROM Employee c WHERE c.id=:id")
	Optional<Employee> findById(@Param("id") Long id);
}