package com.rbts.hrms.authentication.dto;

import lombok.Data;

import java.util.Set;

@Data
public class SignupResponse {
    private Long id;
    private String username;


    private String fullName;


    private String employeeId;


    private String contactNo;
    private Boolean status;


//    private String[] role;

    private String password;


    private String displayName;


    private Set<String> role;
}
