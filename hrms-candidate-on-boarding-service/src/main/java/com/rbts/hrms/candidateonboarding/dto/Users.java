package com.rbts.hrms.candidateonboarding.dto;

import lombok.Data;

import java.util.Set;

@Data
public class Users {




    private String fullName;

    private String employeeId;


    private String username;

    private String password;

    private String contactNo;
    private String altContactNo;
    private String cinNo;

    private String gstn;
    private String address;
    private String contactPerson;
    private Set<String> roles;

}
