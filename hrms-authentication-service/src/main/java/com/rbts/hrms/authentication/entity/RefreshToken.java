package com.rbts.hrms.authentication.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name="Refresh_Token")
@Data
public class RefreshToken {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tokenSequenceGenerator")
    @SequenceGenerator(name = "tokenSequenceGenerator",allocationSize = 1, initialValue = 10)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

    @Column(name="token",nullable = false, unique = true, updatable = false)
    private String token;


    @Column(name="expiry_date",nullable = false, updatable = false)
    private Instant expiryDate;
}
