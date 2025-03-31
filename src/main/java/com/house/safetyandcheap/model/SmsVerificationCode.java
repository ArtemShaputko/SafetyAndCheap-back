package com.house.safetyandcheap.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("SmsVerificationCode")
public class SmsVerificationCode implements Serializable {
    @Id
    private String email;
    private String code;
    @Column(name = "phone_number")
    private String phoneNumber;
    @TimeToLive
    private Long ttl;
}
