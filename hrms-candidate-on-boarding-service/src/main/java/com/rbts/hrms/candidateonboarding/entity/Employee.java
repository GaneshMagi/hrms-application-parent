package com.rbts.hrms.candidateonboarding.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Employee extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employeeSequenceGenerator")
    @SequenceGenerator(name = "employeeSequenceGenerator", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "emp_id",nullable = false, unique = true)
    private String empId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email_id",nullable = false, unique = true)
    private String emailId;

    @Column(name = "contact_no",nullable = false, unique = true)
    private String contactNo;

    @Column(name = "whatsapp_no")
    private String whatsappNo;

    @Column(name = "designation")
    private String designation;

    @Column(name = "alt_contact_no")
    private String altContactNo;



//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "candidate_id_id")
//    private CandidateDetails candidateId;



    @ManyToOne
    private Status statusId;


}
