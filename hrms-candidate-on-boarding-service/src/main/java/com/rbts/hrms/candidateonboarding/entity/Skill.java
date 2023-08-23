package com.rbts.hrms.candidateonboarding.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Skill.
 */
@Entity
@Table(name = "skill")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Skill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", allocationSize = 1, initialValue = 2000)
    private Long id;
    @Column(name = "skill_name",unique = true,nullable = false)
    private String skillName;


    @Column(name = "is_active", nullable = false)
    private Boolean isActive;


//    @OneToMany(mappedBy = "skill")
//    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//    @JsonIgnoreProperties(value = { "statusId", "skill", "city" }, allowSetters = true)
//    private Set<CandidateDetails> candidateIds = new HashSet<>();

//   @OneToMany(mappedBy = "skill")
//   @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//  @JsonIgnoreProperties(value = { "panelId", "statusId", "interviewLevel", "skill" }, allowSetters = true)
//   private Set<PanelSkill> panelSkillIds = new HashSet<>();


}
