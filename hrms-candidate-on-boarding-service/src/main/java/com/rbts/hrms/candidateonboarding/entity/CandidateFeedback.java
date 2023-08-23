package com.rbts.hrms.candidateonboarding.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * A CandidateFeedback.
 */
@Entity
@Table(name = "candidate_feedback")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class CandidateFeedback extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedbackSequenceGenerator")
    @SequenceGenerator(name = "feedbackSequenceGenerator", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "current_ctc")
    private String currentCtc;

    @Column(name = "expected_ctc")
    private String expectedCtc;

    @Column(name = "interview_date")
    private LocalDate interviewDate;

    @Column(name = "notice_period")
    private Integer noticePeriod;

    @Column(name = "earliest_availability")
    private String earliestAvailability;

    @Column(name = "detail_feedback")
    private String detailFeedback;

    @Column(name = "behavourial_feedback")
    private String behavourialFeedback;

    @Column(name = "technical_feedback")
    private String technicalFeedback;

    @Column(name = "feedback_summary")
    private String feedbackSummary;

    @Column(name = "offer_letter")
    private Instant offerLetter;


    @Column(name = "final_status")
    private String finalStatus;



    @ManyToMany
    @JoinTable(
            name = "candidate_feedback_paneldetails",
            joinColumns = @JoinColumn(name = "candidatefeedback_id"),
            inverseJoinColumns = @JoinColumn(name = "paneldetails_id")
    )

    private Set<PanelDetails> panelDetails;

//    @ManyToOne
//    private NotificationDetails  notificationDetails;
//
//    @ManyToOne
//    private NotificationTemplate  notificationTemplate;
//
//
//
////    @OneToMany(mappedBy = "candidateFeedback")
////    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
////    @JsonIgnoreProperties(value = { "candidateFeedback" }, allowSetters = true)
////    private Set<OfferLetter> offerLetterIds = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "candidateid_id")
    private CandidateDetails candidateId;

    @ManyToOne
    @JoinColumn(name = "interviewlevelid_id")
    private InterviewLevel interviewLevelId;


}
