package com.rbts.hrms.authentication.exception;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "authentication")
@Data
public class AppProperties {


    String baseUrl;
    String user;
    String pass;
    String empId;
    String contact;
    String contactNo;
    String currentPassword;
    String newPassword;
    String employeeId;
    String refreshToken;
    String token;
    String name;
    String admin;
    String users;
    String vendor;
    String hr;



}
