package com.rbts.hrms.authentication.controller;


import com.rbts.hrms.authentication.dto.ChangePasswordDTO;
import com.rbts.hrms.authentication.dto.MessageResponse;
import com.rbts.hrms.authentication.entity.Users;
import com.rbts.hrms.authentication.exception.ResourceNotFoundException;
import com.rbts.hrms.authentication.repository.UsersRepository;
import com.rbts.hrms.authentication.service.PasswordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping("/api/users")
public class PasswordController {


    @Autowired
    PasswordsService passwordsService;


    @Autowired
    UsersRepository usersRepository;


    /**
     * Changes the password for a specific user.
     *
     * @param id      The ID of the user.
     * @param request The ChangePasswordDTO object containing the current and new password.
     * @return ResponseEntity representing the response of the password change process.
     */

    @PostMapping("/{id}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody ChangePasswordDTO request) {
        Users user = usersRepository.findOne(id);
        try {
            passwordsService.changePassword(user, request.getCurrentPassword(), request.getNewPassword());
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(new MessageResponse("Password Changed successfully!"));
    }



}