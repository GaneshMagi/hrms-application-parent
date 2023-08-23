package com.rbts.hrms.candidateonboarding.dto;

import com.rbts.hrms.candidateonboarding.entity.InterviewLevel;
import com.rbts.hrms.candidateonboarding.entity.OfferLetter;
import com.rbts.hrms.candidateonboarding.entity.PanelDetails;
import com.rbts.hrms.candidateonboarding.entity.PanelSkill;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class FeedbackResponse {


    Long id;
    private String currentCtc;


    private String expectedCtc;


    private LocalDate interviewDate;


    private Integer noticePeriod;


    private String earliestAvailability;


    private String detailFeedback;

    private String finalStatus;
    private String behavourialFeedback;


    private String technicalFeedback;


    private String feedbackSummary;


    private Instant offerLetter;


    private String createdBy;


    private String lastModifiedBy;


    private Date lastModifiedDate;

    private Date creationDate;

    private Set<PanelDetails> panelDetails ;


//    private NotificationDetails notificationDetails;
//
//
//    private NotificationTemplate notificationTemplate;




    private CandidateResponse candidateId;

    private InterviewLevel interviewLevelId;
}
