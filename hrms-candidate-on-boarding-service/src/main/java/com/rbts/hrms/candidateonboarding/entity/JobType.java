package com.rbts.hrms.candidateonboarding.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="jobtype")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class JobType  extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jobtypeSequenceGenerator")
    @SequenceGenerator(name = "jobtypeSequenceGenerator", allocationSize = 1, initialValue = 1)
    private long id;


    @Column(name = "jobtype",unique = true,nullable = false)
    private String jobType;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;



}
