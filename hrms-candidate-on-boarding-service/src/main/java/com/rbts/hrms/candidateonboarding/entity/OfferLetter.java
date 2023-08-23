package com.rbts.hrms.candidateonboarding.entity;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A OfferLetter.
 */
@Entity
@Table(name = "offer_letter")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
public class OfferLetter extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offerletterSequenceGenerator")
    @SequenceGenerator(name = "offerletterSequenceGenerator", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "date")
    private Instant date;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "ref_code")
    private String refCode;

    @Column(name = "position")
    private String position;

    @Column(name = "date_of_joining")
    private Instant dateOfJoining;

    @Column(name = "probationary_period")
    private String probationaryPeriod;

    @Column(name = "notice_period")
    private String noticePeriod;

    @Column(name = "compensation")
    private Double compensation;

    @Column(name = "employment_type")
    private String employmentType;

    @Column(name = "place_of_joining")
    private String placeOfJoining;

    @Column(name = "grade")
    private String grade;
    @Column(name = "designation")
    private String designation;



    @ManyToOne
//    @JsonIgnoreProperties(
//        value = { "panelId", "notificationDetailsId", "notificationTemplateId", "offerLetterIds", "candidateId", "interviewLevelId" },
//        allowSetters = true
//    )
    @JoinColumn(name = "candidatefeedback_id", nullable = false)
    private CandidateFeedback candidateFeedback;


}
