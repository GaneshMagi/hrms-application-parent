package com.rbts.hrms.authentication.entity;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="user_details",uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {



    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequenceGenerator")
    @SequenceGenerator(name = "userSequenceGenerator",allocationSize = 1, initialValue = 1)
    @Column(name = "id")
    private Long id;


    @Column(name="full_name")
    private String fullName;

    @Column(name="employee_id", unique = true, nullable = false)
    private String employeeId;


    @Column(name="username", nullable = false)
    private String username;


    @Column(name="password", nullable = false)
    private String password;


    @Column(name="contact_no", unique = true, nullable = false)
    private String contactNo;


    @Column(name="display_name")
    private String displayName;


    @Column(name="activation", nullable = false)
    private Boolean status;



    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();




    public Users(String username, String fullname, String employeeId, String contactNo,String password,String displayName) {

        this.username=username;
        this.fullName=fullname;
        this.employeeId=employeeId;
        this.contactNo=contactNo;
        this.password=password;
        this.displayName=displayName;

    }

}
