package com.house.safetyandcheap.controller;

import com.house.safetyandcheap.dto.UserDto;
import com.house.safetyandcheap.exception.VerificationException;
import com.house.safetyandcheap.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UserDto user, @RequestParam Long userId) {
        userService.updateUser(user, userId);
        return ResponseEntity.ok("User updated successfully");
    }

    @PostMapping("/get_code")
    public ResponseEntity<String> getCode(@RequestParam String phoneNumber) {
        userService.providePhoneNumber(SecurityContextHolder.getContext().getAuthentication().getName(), phoneNumber);
        return ResponseEntity.ok("Code sent successfully");
    }

    @PostMapping("/verify_number")
    public ResponseEntity<String> verifyNumber(@RequestParam String phoneNumber,
                                               @RequestParam String code) throws VerificationException {
        userService.verifyPhoneNumber(SecurityContextHolder.getContext().getAuthentication().getName(), phoneNumber, code);
        return ResponseEntity.ok("Code verified successfully");

    }

    @GetMapping("/get_by_id")
    public ResponseEntity<UserDto> getUserById(@RequestParam Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("/get_current")
    public ResponseEntity<UserDto> getUserByEmail() {
        return ResponseEntity.ok(userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
    }
}
