package com.rbts.hrms.candidateonboarding.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="qualifications")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Qualification {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "qualificationsSequenceGenerator")
    @SequenceGenerator(name = "qualificationsSequenceGenerator", allocationSize = 1, initialValue = 200)
    private long id;


    @Column(name="name" ,nullable = false,unique = true)
    private  String name;


    @Column(name="description")
    private  String description;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;



}
