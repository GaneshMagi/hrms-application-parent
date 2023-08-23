package com.rbts.hrms.candidateonboarding.dto;

import com.rbts.hrms.candidateonboarding.entity.CandidateDetails;
import com.rbts.hrms.candidateonboarding.entity.Position;
import lombok.Data;

import java.util.Set;

@Data
public class PositionProfileResponse {
    private long id;
    private Position position;

    private CandidateDetails candidateDetails;

    private Boolean isActive;


}
