package com.rbts.hrms.candidateonboarding.customexception;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "candidate")
@Data
public class AppProperties {


    String skill;
    String skill1;
    String city;
    String contactNo;
    String candidate;
    String candidate1;
    String status;
    String employee;
    String employeeid;
    String interviewlevel;
    String interviewleveldata;
    String paneldetails;
    String interviewDate;
    String level;
    String levelname;
    String message;

    String email;

    String notificationTemplate;

    String attachment;
    String url;
    String notificationDetails;
    String data;
    String employeedata;
    String panel;
    String feedback;
    String messagedata;
    String notificationTemplatedata;
    String value;
    String cityName;
    String panelskill;
    String panelskill1;
    String emaildata;
    String contactdata;
    String empdata;
    String preference;
    String interviewID;
    String paneldata;
    String interviewname;
    String notificationid;
    String candidatedetails;
    String messagetype;
    String notificationname;
    String qualification;
    String qualificationdata;
    String language;
    String languagedata;
    String statusdata;
    String profile;
    String shiftNamedata;
    String shiftName;
    String priorityname;
    String prioritynamedata;
    String jobtypedata;
    String jobtype;
    String vendorName;
    String jobdescription;
    String vendorDetails;
    String positionId;
    String candidatedata;
    String positionAssignment;
    String hr;
    String panelrole;
    String vendor;

    String active;

    String positionStatus;
    String positionStatus1;

    String positionType;
    String positionType1;
    String event;
    String event1;
    String delete;
    String profileName;
    String profileId;
    String gstn;
    String cin;
    String gstn1;
    String cin1;
    String vendorName1;
    String jd;
    String employeedetails;
    String panelDetails1;
}
