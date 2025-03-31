package com.house.safetyandcheap.controller;

import com.house.safetyandcheap.dto.auth.UserLoginDto;
import com.house.safetyandcheap.dto.auth.UserRegistrationDto;
import com.house.safetyandcheap.dto.auth.UserVerificationDto;
import com.house.safetyandcheap.dto.auth.LoginResponseDto;
import com.house.safetyandcheap.service.AuthenticationService;
import com.house.safetyandcheap.service.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDto userDto) throws Exception {
        authenticationService.register(userDto);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestBody UserVerificationDto userDto) throws Exception {
        authenticationService.verifyUser(userDto);
        return ResponseEntity.ok("User verified successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody UserLoginDto userDto) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth objet in controller {}", authentication);
        var user = authenticationService.login(userDto);
        String jwtToken = jwtService.generateToken(user);
        LoginResponseDto loginResponseDto = new LoginResponseDto(jwtToken, jwtService.getTtl());
        log.info("Authentication: {}", SecurityContextHolder.getContext().getAuthentication());
        return ResponseEntity.ok(loginResponseDto);
    }
}
