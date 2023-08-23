package com.rbts.hrms.authentication.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="permission")
@Data
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permissionSequenceGenerator")
    @SequenceGenerator(name = "permissionSequenceGenerator",allocationSize = 1, initialValue = 10)
    @Column(name = "id")
    private Long id;

    @Column(name="name")
    private String name;

    @ManyToMany
    private List<Role> roles = new ArrayList<Role>();
}
