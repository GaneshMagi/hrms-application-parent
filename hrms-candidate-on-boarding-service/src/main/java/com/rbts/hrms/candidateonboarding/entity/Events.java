package com.rbts.hrms.candidateonboarding.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "events")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "eventSequenceGenerator")
    @SequenceGenerator(name = "eventSequenceGenerator", allocationSize = 1, initialValue = 1)
    private long id;

    @Column(name="name",nullable = false,unique = true)
    private  String name;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}
