package com.rbts.hrms.authentication.service;

import com.rbts.hrms.authentication.entity.Passwords;
import com.rbts.hrms.authentication.entity.Users;
import com.rbts.hrms.authentication.exception.AppProperties;
import com.rbts.hrms.authentication.exception.ResourceNotFoundException;
import com.rbts.hrms.authentication.repository.PasswordsRepository;
import com.rbts.hrms.authentication.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PasswordsService {


    @Autowired
    PasswordsRepository passwordsRepository;


    @Autowired
    UsersRepository usersRepository;


    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    AppProperties appProperties;




    public void changePassword(Users user, String currentPassword, String newPassword) throws ResourceNotFoundException {

        // Verify that the current password matches what's stored in the user table
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new ResourceNotFoundException(appProperties.getCurrentPassword());
        }

        // Get the user's five most recent passwords from the password table
        List<Passwords> recentPasswords = passwordsRepository.findTop5ByUserIdOrderByCreatedDateDesc(user.getId());

        // Check that the new password is not one of the five most recent passwords
        boolean isNewPasswordRecent = recentPasswords.stream()
                .anyMatch(p -> passwordEncoder.matches(newPassword, p.getPassword()));

        if (isNewPasswordRecent) {
            throw new ResourceNotFoundException(appProperties.getNewPassword());
        }

        // Hash the new password and save it in both the user table and the password table
        String hashedPassword = passwordEncoder.encode(newPassword);

        user.setPassword(hashedPassword);
        usersRepository.save(user);

        Passwords password = new Passwords();
        password.setUser(user);
        password.setPassword(hashedPassword);
        password.setCreatedDate(new Date());
        passwordsRepository.save(password);
    }

}







