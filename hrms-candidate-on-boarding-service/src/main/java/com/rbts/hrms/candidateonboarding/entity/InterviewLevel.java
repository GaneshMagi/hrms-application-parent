package com.rbts.hrms.candidateonboarding.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A InterviewLevel.
 */
@Entity
@Table(name = "interview_level")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class InterviewLevel extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "interviewlevelSequenceGenerator")
    @SequenceGenerator(name = "interviewlevelSequenceGenerator", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "level_name",nullable = false)
    private String levelName;

    @Column(name = "preference",nullable = false,unique = true)
    private int preference;

    @Column(name = "predecessor")
    private String predecessor;


    @ManyToOne
    private Status statusId;


}
