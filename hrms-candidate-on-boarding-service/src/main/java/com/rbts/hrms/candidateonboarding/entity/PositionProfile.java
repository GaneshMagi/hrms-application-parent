package com.rbts.hrms.candidateonboarding.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="position_profiles")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class PositionProfile extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "positionprofileSequenceGenerator")
    @SequenceGenerator(name = "positionprofileSequenceGenerator", allocationSize = 1, initialValue = 1)
    private long id;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;




    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id_id")
    private CandidateDetails candidateDetails;


    @Column(name = "is_active", nullable = false)
    private Boolean isActive;


}
