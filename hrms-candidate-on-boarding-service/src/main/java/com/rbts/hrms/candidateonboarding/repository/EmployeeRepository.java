package com.rbts.hrms.candidateonboarding.repository;

import com.rbts.hrms.candidateonboarding.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Employee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {


    @Query("select e from Employee e where e.emailId = ?1")
    Employee findByEmail(String email);

    @Query("select e from Employee e where e.contactNo = ?1")
    Employee findBycontactNo(String contactNo);

    @Query("select e from Employee e where e.designation = ?1")
    List<Employee> findByDesignation(String designation);

    @Query("select e from Employee e where e.empId = ?1")
    Employee findByEmpId(String empid);
    @Query("select e from Employee e where e.id = ?1")
    Employee getById(Long id);

    @Query("select e from Employee e order by e.fullName asc")
    List<Employee> findAllData();


}
