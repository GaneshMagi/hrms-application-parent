package com.rbts.hrms.candidateonboarding.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * A PanelSkill.
 */
@Entity
@Table(name = "panel_skill")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class PanelSkill extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "panelskillSequenceGenerator")
    @SequenceGenerator(name = "panelskillSequenceGenerator", allocationSize = 1, initialValue = 1)
    private Long id;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="panel_id_id" ,nullable = false)
    private PanelDetails panelId;

    @ManyToOne
    private Status status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="interviewlevel_id" ,nullable = false)
    private InterviewLevel interviewLevel;


    @ManyToOne
    @JoinColumn(name="skill_id" ,nullable = false)
    private Skill skill;



}
