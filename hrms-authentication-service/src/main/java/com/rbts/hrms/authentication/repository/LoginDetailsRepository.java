package com.rbts.hrms.authentication.repository;

import com.rbts.hrms.authentication.entity.LoginDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoginDetailsRepository extends JpaRepository<LoginDetails, Long> {

    List<LoginDetails> findByUsernameAndActiveIsTrue(String username);

}
