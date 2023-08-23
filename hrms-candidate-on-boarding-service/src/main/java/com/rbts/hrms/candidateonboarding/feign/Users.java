package com.rbts.hrms.candidateonboarding.feign;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
public class Users {


        private Long id;



        private String fullName;



        private String employeeId;



        private String username;



        private String password;



        private String contactNo;



        private String displayName;


        @Column(name="activation", nullable = false)
        private boolean status;




        private Set<Role> roles = new HashSet<>();




    }
