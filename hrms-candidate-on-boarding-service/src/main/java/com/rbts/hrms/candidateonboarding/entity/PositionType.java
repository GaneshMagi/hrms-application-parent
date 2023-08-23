package com.rbts.hrms.candidateonboarding.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="position_type")
@Data
public class PositionType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "positionTypeSequenceGenerator")
    @SequenceGenerator(name = "positionTypeSequenceGenerator", allocationSize = 1, initialValue = 1)
    private long id;

    @Column(name="name",nullable = false,unique = true)
    private  String type;
}
