package com.rbts.hrms.authentication.controller;


import com.rbts.hrms.authentication.dto.*;
import com.rbts.hrms.authentication.entity.LoginDetails;
import com.rbts.hrms.authentication.entity.RefreshToken;
import com.rbts.hrms.authentication.exception.TokenRefreshException;
import com.rbts.hrms.authentication.repository.LoginDetailsRepository;
import com.rbts.hrms.authentication.security.JwtUtils;
import com.rbts.hrms.authentication.service.RefreshTokenService;
import com.rbts.hrms.authentication.service.TokenBlacklistService;
import com.rbts.hrms.authentication.service.UserDetailsServiceImpl;
import com.rbts.hrms.authentication.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class PublicController {


    @Autowired
    AuthenticationManager authenticationManager;


    @Autowired
    JwtUtils jwtUtils;


    @Autowired
    RefreshTokenService refreshTokenService;


    @Autowired
    TokenBlacklistService tokenBlacklistService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    LoginDetailsRepository loginDetailsRepository;



    /**
     * Authenticates a user with the provided username and password.
     *
     * @param loginRequest The LoginRequest object containing the username and password.
     * @param request      The HttpServletRequest object.
     * @return ResponseEntity representing the response of the authentication process.
     */

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        String username = loginRequest.getUsername();
        List<LoginDetails> loginDetailsList = loginDetailsRepository.findByUsernameAndActiveIsTrue(username);
        if (!loginDetailsList.isEmpty()) {
            // User is already logged in, handle the error or return an appropriate response
            return ResponseEntity.badRequest().body("User is already logged in");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UsersService userDetails = (UsersService) authentication.getPrincipal();



        String jwt = jwtUtils.generateJwtToken(userDetails);


        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        LoginDetails loginDetails = new LoginDetails();
        loginDetails.setUsername(username);
        loginDetails.setLoginTime(LocalDateTime.now());
//        loginDetails.setSessionId(request.getSession().getId());
        loginDetails.setActive(false);
        loginDetailsRepository.save(loginDetails);


        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(),
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }


    /**
     * Refreshes the access token using the provided refresh token.
     *
     * @param request The RefreshTokenRequest object containing the refresh token.
     * @return ResponseEntity representing the response of the token refresh process.
     */
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@RequestBody RefreshTokenRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new RefreshTokenResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }



    /**
     * Logs out the currently authenticated user.
     *
     * @param request The HttpServletRequest object.
     * @return ResponseEntity representing the response of the logout process.
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principle.toString() != "anonymousUser") {
            Long userId = ((UsersService) principle).getId();
            String username =((UsersService) principle).getUsername();

            List<LoginDetails> loginDetailsList = loginDetailsRepository.findByUsernameAndActiveIsTrue(username);
            if (!loginDetailsList.isEmpty()) {
                LoginDetails loginDetails = loginDetailsList.get(0);
                loginDetails.setLogoutTime(LocalDateTime.now());
                loginDetails.setActive(false);
                loginDetailsRepository.save(loginDetails);
            }

            refreshTokenService.deleteByUserId(userId);
        }
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String jwtToken = authorizationHeader.substring(7);
                tokenBlacklistService.addTokenToBlacklist(jwtToken, 1000000); // add token to blacklist with expiration time of 10 seconds
                return ResponseEntity.ok(new MessageResponse("User has been logged out successfully!"));
            }



            return ResponseEntity.badRequest().body(new MessageResponse("Authorization header is missing or invalid!"));
        }



    }


