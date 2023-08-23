package com.rbts.hrms.candidateonboarding.dto;


import com.rbts.hrms.candidateonboarding.entity.*;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class FeedbackRequest {


    private String currentCtc;


    private String expectedCtc;


    private LocalDate interviewDate;


    private Integer noticePeriod;


    private String earliestAvailability;


    private String detailFeedback;


    private String behavourialFeedback;


    private String technicalFeedback;


    private String feedbackSummary;


    private Instant offerLetter;


    private String finalStatus;


    private Set<PanelDetails> panelDetails;


//    private NotificationDetails notificationDetails;
//
//
//    private NotificationTemplate notificationTemplate;
//



    private Set<OfferLetter> offerLetterIds = new HashSet<>();


    private CandidateDetails candidateId;

    private InterviewLevel interviewLevelId;
}
