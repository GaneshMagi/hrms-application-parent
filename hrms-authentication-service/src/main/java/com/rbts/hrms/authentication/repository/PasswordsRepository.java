package com.rbts.hrms.authentication.repository;

import com.rbts.hrms.authentication.entity.Passwords;
import com.rbts.hrms.authentication.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasswordsRepository extends JpaRepository<Passwords, Long> {


 //   List<Passwords> findByUserOrderByCreatedDateDesc(Users user, Pageable pageable);


    List<Passwords> findTop5ByUserOrderByCreatedDateDesc(Users user);

    List<Passwords> findTop5ByUserIdOrderByCreatedDateDesc(Long id);
}
