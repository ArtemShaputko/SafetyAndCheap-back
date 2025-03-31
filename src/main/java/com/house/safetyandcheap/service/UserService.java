package com.house.safetyandcheap.service;

import com.house.safetyandcheap.dto.UserDto;
import com.house.safetyandcheap.exception.VerificationException;
import com.house.safetyandcheap.mapper.Mapper;
import com.house.safetyandcheap.model.SmsVerificationCode;
import com.house.safetyandcheap.model.User;
import com.house.safetyandcheap.repository.SmsVerificationCodeRepository;
import com.house.safetyandcheap.sms.Smsc;
import com.house.safetyandcheap.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    @Value("${jwt.ttl-hours}")
    private static long TTL;
    @NonNull
    private UserRepository userRepository;
    @NonNull
    private SmsVerificationCodeRepository verificationCodeRepository;
    @NonNull
    private Mapper mapper;
    @Value("${sms.login}")
    private String smsLogin;
    @Value("${sms.password}")
    private String smsPassword;
    public static final String USER_EMAIL_NOT_FOUND = "User with email %s not found";

    public UserDto getUserByEmail(@NotNull String email) {
        User user = userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        return mapper.userToDto(user);
    }

    public UserDto getUserById(@NotNull Long id) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return mapper.userToDto(user);
    }

    public void updateUser(UserDto userDto, Long userId) {
        userRepository.findById(userId).ifPresentOrElse(user -> {
            if (userDto.getName() != null) {
                user.setName(userDto.getName());
            }
            if (userDto.getSurname() != null) {
                user.setSurname(userDto.getSurname());
            }
            userRepository.save(user);
        }, () -> {
            throw new EntityNotFoundException("User with id " + userId + " not found");
        });
    }

    public void providePhoneNumber(String email, @NotNull String phoneNumber) {
        if(!userRepository.existsByEmail(email)) {
            throw new EntityNotFoundException(String.format(USER_EMAIL_NOT_FOUND, email));
        }
        if(!verificationCodeRepository.existsByPhoneNumber(phoneNumber)) {
            throw new EntityExistsException("Phone number already registered");
        }
        Smsc smsc = new Smsc(smsLogin, smsPassword);
        var code = new SmsVerificationCode(
                email,
                generateCode(4),
                phoneNumber,
                TTL
        );
        verificationCodeRepository.save(code);
        smsc.send_sms(
                phoneNumber,
                "Your verification code: " + code.getCode(),
                1,
                "",
                "",
                0,
                "SafetyAndCheap",
                "");
    }

    public void verifyPhoneNumber(String email, @NotNull String phoneNumber, @NotNull String verificationCode) throws VerificationException {
        var code = verificationCodeRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        if (!code.getCode().equals(verificationCode) || !code.getPhoneNumber().equals(phoneNumber)) {
            throw new VerificationException("Wrong user authorities");
        }
        userRepository.findByEmail(email).ifPresentOrElse(user -> {
            user.setPhoneNumber(phoneNumber);
            userRepository.save(user);
        }, () -> {
            throw new EntityNotFoundException(String.format(USER_EMAIL_NOT_FOUND, email));
        });
    }

    private String generateCode(int N) {
        return new Random().ints(N, 0, 10)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());
    }

}
