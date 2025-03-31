package com.house.safetyandcheap.service;

import com.house.safetyandcheap.dto.auth.UserLoginDto;
import com.house.safetyandcheap.dto.auth.UserRegistrationDto;
import com.house.safetyandcheap.dto.auth.UserVerificationDto;
import com.house.safetyandcheap.mapper.Mapper;
import com.house.safetyandcheap.model.User;
import com.house.safetyandcheap.model.EmailVerificationCode;
import com.house.safetyandcheap.repository.UserRepository;
import com.house.safetyandcheap.repository.EmailVerificationCodeRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    @Setter
    @Getter
    private int codeLength = 6;
    @Value("${jwt.ttl-hours}")
    private static long TTL;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final EmailVerificationCodeRepository emailVerificationCodeRepository;
    private final EmailService emailService;
    private final Mapper mapper;

    public void register(UserRegistrationDto userDto) throws Exception {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new Exception("User already exists");
        }
        emailVerificationCodeRepository.deleteByEmail(userDto.getEmail());
        var code = new EmailVerificationCode(userDto.getEmail(), generateCode(codeLength), TimeUnit.HOURS.toMillis(TTL));
        String messageText = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <meta name="description" content="Verification code for Safety and Cheap service">
                    <title>Verification Code</title>
                    <style>
                        {
                            margin: 0;
                            padding: 0;
                            box-sizing: border-box;
                        }

                        body {
                            font-family: 'Arial', sans-serif;
                            background-color: #f9f9fb;
                            color: #333;
                            display: flex;
                            justify-content: center;
                            align-items: center;
                            height: 100vh;
                            margin: 0;
                        }

                        .container {
                            text-align: center;
                            background: #ffffff;
                            padding: 2rem;
                            border-radius: 15px;
                            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
                            max-width: 400px;
                            width: 100%%;
                        }

                        h1 {
                            font-size: 2rem;
                            color: #4a4a4a;
                            margin-bottom: 1rem;
                        }

                        p {
                            font-size: 1.2rem;
                            color: #666;
                            margin-bottom: 1.5rem;
                        }

                        .code {
                            font-size: 2.5rem;
                            font-weight: bold;
                            color: #2c7be5;
                        }

                        @media (max-width: 600px) {
                            h1 {
                                font-size: 1.8rem;
                            }

                            .code {
                                font-size: 2rem;
                            }
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h1>Safety and Cheap Verification</h1>
                        <p>Your verification code is:</p>
                        <span class="code">%s</span>
                        <p>Please keep this code secure and do not share it with anyone.</p>
                    </div>
                </body>
                </html>
                """;
        emailService.sendHtmlEmail(
                userDto.getEmail(),
                "Verification Code", messageText.formatted(code.getCode()));
        emailVerificationCodeRepository.save(code);
    }

    public User login(UserLoginDto userDto) throws Exception {
        User user = userRepository.findByEmail(userDto.getEmail()).orElseThrow(() -> new Exception("User not found"));
        try {
            log.info("TEST");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDto.getEmail(),
                            userDto.getPassword())
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return user;
    }

    public User verifyUser(UserVerificationDto userDto) throws Exception {
        var code = emailVerificationCodeRepository
                .findByEmail(userDto.getEmail()).orElseThrow(() -> new Exception("No verification code sent to email"));
        if (!Objects.equals(code.getCode(), userDto.getVerificationCode())) {
            throw new Exception("Invalid verification code");
        }
        emailVerificationCodeRepository.deleteByEmail(userDto.getEmail());
        User user = mapper.userFromVerificationDto(userDto);
        userRepository.save(user);
        return user;
    }

    private String generateCode(int N) {
        return new Random().ints(N, 0, 10)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());
    }
}
