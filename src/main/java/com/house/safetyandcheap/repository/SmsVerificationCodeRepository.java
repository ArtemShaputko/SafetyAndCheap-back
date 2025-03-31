package com.house.safetyandcheap.repository;

import com.house.safetyandcheap.model.SmsVerificationCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SmsVerificationCodeRepository extends CrudRepository<SmsVerificationCode, Long> {
    Optional<SmsVerificationCode> findByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}
