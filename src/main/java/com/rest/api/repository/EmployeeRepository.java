package com.rest.api.repository;

import com.rest.api.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Muhammad Atta
 *
 */

@Repository
public interface EmployeeRepository extends JpaRepository <Employee, Long> {

}