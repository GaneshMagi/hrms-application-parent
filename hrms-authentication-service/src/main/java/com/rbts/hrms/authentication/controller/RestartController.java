package com.rbts.hrms.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestartController {

    @Autowired
    private RestartEndpoint restartEndpoint;

    @GetMapping("/restart-service")
    public void restartAuthenticationService() {
        restartEndpoint.restart();
    }
}
