package com.rbts.hrms.candidateonboarding.feign;


import feign.Headers;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "authentication-service", configuration = FeignTenantInterceptor.class)
public interface Userfeign {

    @GetMapping("/api/auth/getAll")
    public List<Users> getAll();


    @GetMapping("/api/auth/get/{id}")
    public Users getUser(@PathVariable Long id);

//    @GetMapping("/api/auth/getRole/{id}")
//    public Role getById(@PathVariable(name = "id") Long id);
}
