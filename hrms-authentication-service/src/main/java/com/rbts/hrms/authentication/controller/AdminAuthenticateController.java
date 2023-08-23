//package com.example.AuthenticationService.Controller;
//
//import com.example.AuthenticationService.DTO.AdminLoginDTO;
//import com.example.AuthenticationService.Security.JwtUtils;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/auth")
//@CrossOrigin(origins = "*", maxAge = 3600)
//
//public class AdminAuthenticateController {
//
//
//    @Autowired
//    AuthenticationManager authenticationManager;
//
//
//    @Autowired
//    JwtUtils jwtUtils;
//
//
//    @PostMapping("/admin")
//    public ResponseEntity<?> authenticateAdmin(@RequestBody AdminLoginDTO adminLoginDTO) {
//
//        Authentication authentication = authenticationManager.authenticate(
//            new UsernamePasswordAuthenticationToken(adminLoginDTO.getUsername(), adminLoginDTO.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = jwtUtils.generateJwtToken(authentication);
//        return ResponseEntity.ok(new JWTToken(jwt));
//
//
//    }
//
//    static class JWTToken {
//
//        private String idToken;
//
//        JWTToken(String idToken) {
//            this.idToken = idToken;
//        }
//
//        @JsonProperty("id_token")
//        String getIdToken() {
//            return idToken;
//        }
//
//        void setIdToken(String idToken) {
//            this.idToken = idToken;
//        }
//    }
//
//
//}
//
