package com.rbts.hrms.candidateonboarding.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.yaml.snakeyaml.events.Event;

import javax.persistence.*;

@Entity
@Table(name="priority")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Priority  {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "prioritySequenceGenerator")
    @SequenceGenerator(name = "prioritySequenceGenerator", allocationSize = 1, initialValue = 1)
    private long id;

    @Column(name="priority_name",nullable = false,unique = true)
    private  String priorityName;

    @Column(name="sla_time")
    private String SLAtime;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;


}
