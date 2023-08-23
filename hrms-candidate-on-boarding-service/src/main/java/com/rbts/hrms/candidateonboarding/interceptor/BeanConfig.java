package com.rbts.hrms.candidateonboarding.interceptor;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "aware")
public class BeanConfig {

    @Bean
    public AuditorAware<String> aware(HttpServletRequest request) {
        return () -> {
            String token = request.getHeader("Authorization").substring(7);
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+token);
            String username = getUsernameFromToken(request); // method to extract username from JWT token
            System.out.println("@@@@@@@@@@@@@@@@@11111@@@@@@@@@@@@@@@@@@@@@"+username);
            return Optional.ofNullable(username);
        };
    }
    private String getUsernameFromToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String secret = "JwtRedBerylTech";
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

}


//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Optional;
//
//@Configuration
//@EnableJpaAuditing(auditorAwareRef = "aware")
//public class BeanConfig {
//    @Bean
//    public AuditorAware<String> aware(HttpServletRequest request) {
//        return () -> {
//            String token = getTokenFromRequest(request);
//            String username = getUsernameFromToken(token);
//            return Optional.ofNullable(username);
//        };
//    }
//
//    private String getTokenFromRequest(HttpServletRequest request) {
//        String authHeader = request.getHeader("Authorization");
//        if (authHeader == null || authHeader.isEmpty()) {
//            throw new RuntimeException("Authorization header not found in the request");
//        }
//        String[] authHeaderParts = authHeader.split(" ");
//        if (!"Bearer".equalsIgnoreCase(authHeaderParts[0])) {
//            throw new RuntimeException("Invalid authorization header");
//        }
//        return authHeaderParts[1];
//    }
//
//    private String getUsernameFromToken(String token) {
//        String secret = "JwtRedBerylTech";
//        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//        return claims.getSubject();
//    }
//}
