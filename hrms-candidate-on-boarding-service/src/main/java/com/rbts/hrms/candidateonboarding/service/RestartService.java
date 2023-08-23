package com.rbts.hrms.candidateonboarding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.stereotype.Service;

@Service
public class RestartService {

    private final WebServerApplicationContext context;

    @Autowired
    public RestartService(WebServerApplicationContext context) {
        this.context = context;
    }

    public void restartAuthenticationService() {
        context.getBean(RestartEndpoint.class).restart();
    }
}
