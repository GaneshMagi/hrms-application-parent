package com.rbts.hrms.candidateonboarding.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="jobdescription")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class JobDescription  extends Auditable<String> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jobdescriptionSequenceGenerator")
    @SequenceGenerator(name = "jobdescriptionSequenceGenerator", allocationSize = 1, initialValue = 1)
    private long id;
    @Column(name = "name")
    private String name;

    @Column(name = "job_description")
    private String jobDescription;
    @Column(name = "role_and_responsibility")
    private String roleAndResponsibility;

    @Column(name="skills_required")
    private String skillRequired;

    @Column(name="salary_range")
    private String salaryRange;
    @Column(name="experience")
    private String experience;


    @Column(name="job_location")
    private String jobLocation;
    @Column(name="must_to_have")
    private String mustToHave;

    @Column(name="nice_to_have")
    private String niceToHave;



    @ManyToMany
    private Set<Skill> skill;


    @ManyToMany
    private Set<City> city;

    @ManyToOne
    private  Shifts shifts;
    @ManyToOne
    private JobType  jobType;

    @ManyToMany
    private Set<BenefitsAndPerks> benefits_and_perks;


    @ManyToMany
    private Set<Qualification> qualifications;

    @ManyToMany
    private Set<Languages> languages;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;



}
