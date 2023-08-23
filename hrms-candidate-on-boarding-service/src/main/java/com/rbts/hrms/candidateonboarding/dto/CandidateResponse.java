package com.rbts.hrms.candidateonboarding.dto;

import com.rbts.hrms.candidateonboarding.entity.City;
import com.rbts.hrms.candidateonboarding.entity.Skill;
import com.rbts.hrms.candidateonboarding.entity.Status;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Data
public class CandidateResponse {


    private long id;
    private String fullName;
    private String email;
    private String contactNo;
    private String altContactNo;
    private String gender;
    private LocalDate birthDate;
    private Integer passingYear;
    private String whatsappNo;
    private String resumeUrl;
    private String linkedInUrl;
    private String qualification;
    private double experienceInYears;
    private LocalDate profileScannedOn;
    private String currentCompanyName;
    private String profileReferance;
    private String feedbackStatus;
    private String createdBy;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Date creationDate;
    private Status statusId;
    private Set<Skill> skill;
    private City city;
    private String address;
}
