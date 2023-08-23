package com.rbts.hrms.authentication.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="login_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loginSequenceGenerator")
    @SequenceGenerator(name = "loginSequenceGenerator",allocationSize = 1, initialValue = 1)
    @Column(name = "id")
    private Long id;


    @Column(name="username",nullable = false)
    private String username;

    @Column(name="login_time",nullable = false, updatable = false)
    private LocalDateTime loginTime;

    @Column(name="logout_time",updatable = false)
    private LocalDateTime logoutTime;

    @Column(name="active",nullable = false)
    private boolean active;
}
