package com.rbts.hrms.candidateonboarding.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name="position")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Position   extends Auditable<String> implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "positionSequenceGenerator")
    @SequenceGenerator(name = "positionSequenceGenerator", allocationSize = 1, initialValue = 1)
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private JobDescription jobdescription;

    @OneToOne
    private Priority priority;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;



    @OneToMany
    @JoinTable(name = "position_second_level_panel",
            joinColumns = @JoinColumn(name = "position_id"),
            inverseJoinColumns = @JoinColumn(name = "panel_details_id"))
    private Set<PanelDetails> secondLevelPanel;


    @OneToMany
    @JoinTable(name = "position_first_level_panel",
            joinColumns = @JoinColumn(name = "position_id"),
            inverseJoinColumns = @JoinColumn(name = "panel_details_id"))
    private Set<PanelDetails> firstLevelPanel;


    @Column(name = "noOfPosition")
    private int noOfPosition;

    @OneToOne
    @JoinColumn(name="positionType_id" ,nullable = false)
    private PositionType positionType;


    @OneToOne
    @JoinColumn(name="positionStatus_id" ,nullable = false)
    private PositionStatus positionStatus;

    @OneToOne
    @JoinColumn(name="employee_id" ,nullable = false)
    private Employee employee;


    @Column(name = "open_on")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate openOn;

    @Column(name = "close_on")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate closeOn;

}

