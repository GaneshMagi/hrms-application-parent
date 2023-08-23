package com.rbts.hrms.candidateonboarding.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="languages")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Languages  {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "languagesSequenceGenerator")
    @SequenceGenerator(name = "languagesSequenceGenerator", allocationSize = 1, initialValue = 200)
    private long id;


    @Column(name="language",nullable = false,unique = true)
    private String language;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;




}
