package com.rbts.hrms.candidateonboarding.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name="position_assignment")
@Data
public class PositionAssignment extends Auditable<String> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "positionassignmentSequenceGenerator")
    @SequenceGenerator(name = "positionassignmentSequenceGenerator", allocationSize = 1, initialValue = 1)
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position positionId;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "local_date")
    private LocalDate localDate;

}
