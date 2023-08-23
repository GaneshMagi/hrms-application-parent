package com.rbts.hrms.candidateonboarding.entity;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="position_status")
@Data
public class PositionStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "positionStatusSequenceGenerator")
    @SequenceGenerator(name = "positionStatusSequenceGenerator", allocationSize = 1, initialValue = 1)
    private long id;

    @Column(name="name",nullable = false,unique = true)
    private  String statusName;

}
