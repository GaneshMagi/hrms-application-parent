package com.rbts.hrms.multitenant.customexception;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "multitenant")
@Data
public class AppProperties {


    String baseUrl;
    String username;
    String password;
    String driveName;
    String email;
    String contactNo;
    String domain;
    String range;
    String organizationName;
    String rangeCreate;
    String data;

    String contactNoNull;
    String emailNull;

}
