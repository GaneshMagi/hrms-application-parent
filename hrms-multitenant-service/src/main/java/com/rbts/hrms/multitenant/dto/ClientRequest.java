package com.rbts.hrms.multitenant.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRequest {

   private String fullName;
    private String email;
    private String organizationName;
    private String domainName;
    private String contactNo;
    private String referralCode;
    private String noOfEmployee;
    private  String subscriptionType;
    private boolean isActive;



}
