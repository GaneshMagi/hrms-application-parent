package com.rbts.hrms.authentication.repository;


import com.rbts.hrms.authentication.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query("select u from Users u where u.id =? 1")
    Users findOne(Long data);

    @Query("select u from Users u left join u.roles r where r.name =? 1")
    Users findByrolename(String username);

    @Query("select u from Users u where u.username =? 1")
    Users findByUsername(String username);

    @Query("select u from Users u where u.employeeId =? 1")
    Users findByEmployeeId(String empId);

    @Query("select u from Users u where u.contactNo =? 1")
    Users findByContactNo(String contact);

    Boolean existsByUsername(String username);

    @Query("select u from Users u where u.status = true")
    List<Users> getData();

    @Query("select u from Users u order by u.fullName asc ")
    List<Users> findAllData(String fullName);

}
