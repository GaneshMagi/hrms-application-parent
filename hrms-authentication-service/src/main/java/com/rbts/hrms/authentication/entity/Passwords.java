package com.rbts.hrms.authentication.entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Password")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Passwords {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "passwordSequenceGenerator")
    @SequenceGenerator(name = "passwordSequenceGenerator",allocationSize = 1, initialValue = 1)
    @Column(name = "id")
    private Long id;


    @Column(name="passwords", updatable = false)
    private String password;


    @ManyToOne
    @JoinColumn(name="user_id")
    private Users user;

    @Column(name = "created_date", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;


}
