package com.house.safetyandcheap.model;

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
@RedisHash("EmailVerificationCode")
public class EmailVerificationCode implements Serializable {
    @Id
    private String email;
    private String code;
    @TimeToLive
    private Long ttl;
}
