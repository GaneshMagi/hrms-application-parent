package com.rbts.hrms.authentication.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="Role")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Role  {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roleSequenceGenerator")
    @SequenceGenerator(name = "roleSequenceGenerator",allocationSize = 1, initialValue = 10)
    @Column(name = "id")
    private Long id;


    @Column(name = "name")
    private String name;
}
