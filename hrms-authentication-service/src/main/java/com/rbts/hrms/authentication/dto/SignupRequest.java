package com.rbts.hrms.authentication.dto;


import lombok.Data;

import java.util.Set;

@Data
public class SignupRequest {



    private String username;


    private String fullName;


    private String employeeId;


    private String contactNo;



//    private String[] role;

    private String password;


    private String displayName;


    private Set<String> roles;
    private Boolean status;


}

