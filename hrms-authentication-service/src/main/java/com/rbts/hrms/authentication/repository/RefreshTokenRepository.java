package com.rbts.hrms.authentication.repository;

import com.rbts.hrms.authentication.entity.RefreshToken;
import com.rbts.hrms.authentication.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByUser(Users user);
}
