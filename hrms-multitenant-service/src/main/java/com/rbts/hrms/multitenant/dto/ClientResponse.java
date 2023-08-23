package com.rbts.hrms.multitenant.dto;


import com.rbts.hrms.multitenant.entity.Range;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ClientResponse {

    long id;
    private String fullName;
    private String email;
    private String organizationName;
    private String domainName;
    private String contactNo;
    private String referralCode;
    private String subscriptionType;
    private Range rangeId;
    private boolean isActive;




}
