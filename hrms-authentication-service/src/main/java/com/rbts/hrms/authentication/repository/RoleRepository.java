package com.rbts.hrms.authentication.repository;

import com.rbts.hrms.authentication.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {


    @Query("select r from Role r where r.name=?1")
    Role findByName1(String name);

     @Query("select r from  Role r order by r.name asc")
     List<Role> findAllData(String name);
}
