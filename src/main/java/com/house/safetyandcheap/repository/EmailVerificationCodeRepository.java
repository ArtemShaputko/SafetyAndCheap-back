package com.house.safetyandcheap.repository;

import com.house.safetyandcheap.model.EmailVerificationCode;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EmailVerificationCodeRepository extends CrudRepository<EmailVerificationCode, String> {
    Optional<EmailVerificationCode> findByEmail(String email);
    void deleteByEmail(String email);
}
