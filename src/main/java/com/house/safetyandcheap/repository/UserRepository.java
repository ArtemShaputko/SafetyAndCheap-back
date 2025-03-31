package com.house.safetyandcheap.repository;

import com.house.safetyandcheap.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByPhoneNumber(String phone);
    Boolean existsByEmail(String email);
}
