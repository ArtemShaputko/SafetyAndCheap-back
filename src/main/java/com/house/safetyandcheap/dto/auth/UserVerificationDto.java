package com.house.safetyandcheap.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVerificationDto {
    private String name;
    private String email;
    private String password;
    private String verificationCode;
}
