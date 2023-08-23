package com.rbts.hrms.candidateonboarding.dto;

import com.rbts.hrms.candidateonboarding.entity.Status;
import lombok.Data;

import java.util.Date;

@Data

public class EmplyoeeRequest {

    private String empId;

    private String fullName;


    private String emailId;


    private String contactNo;


    private String whatsappNo;


    private String designation;

    private String altContactNo;

    private String createdBy;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private Date creationDate;


    private Status statusId;
}
