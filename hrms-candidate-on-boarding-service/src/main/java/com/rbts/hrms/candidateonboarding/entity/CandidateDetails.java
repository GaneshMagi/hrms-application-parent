package com.rbts.hrms.candidateonboarding.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * A CandidateDetails.
 */
@Entity
@Table(name = "candidate_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class CandidateDetails   extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidateSequenceGenerator")
    @SequenceGenerator(name = "candidateSequenceGenerator", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "contact_no", unique = true)
    private String contactNo;

    @Column(name = "alt_contact_no")
    private String altContactNo;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birth_date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthDate;

    @Column(name = "passing_year")
    private Integer passingYear;

    @Column(name = "whatsapp_no")
    private String whatsappNo;

    @Column(name = "resume_url")
    private String resumeUrl;

    @Column(name = "linked_in_url")
    private String linkedInUrl;

    @Column(name = "qualification")
    private String qualification;

    @Column(name = "experience_in_years")
    private double experienceInYears;

    @Column(name = "profile_scanned_on")
    private LocalDate profileScannedOn;

    @Column(name = "current_company_name")
    private String currentCompanyName;

    @Column(name = "profile_referance")
    private String profileReferance;

    @Column(name = "feedback_status")
    private String feedbackStatus;


    @Column(name = "address")
    private String address;

    @OneToOne
    private Status statusId;

    @OneToMany
    private Set<Skill> skill;

    @OneToOne
    @JsonIgnoreProperties(value = { "candidateIds" }, allowSetters = true)
    private City city;


}
