package com.rbts.hrms.candidateonboarding.dto;

import com.rbts.hrms.candidateonboarding.entity.InterviewLevel;
import com.rbts.hrms.candidateonboarding.entity.PanelDetails;
import com.rbts.hrms.candidateonboarding.entity.Skill;
import com.rbts.hrms.candidateonboarding.entity.Status;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class PanelSkillResponse {
    long id;
    PanelDetails panelId;
    Status status;

    InterviewLevel interviewLevel;

    private Skill skill;

    private String createdBy;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Date creationDate;

}
