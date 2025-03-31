package com.house.safetyandcheap.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @SequenceGenerator(name = "user_seq",
            sequenceName = "user_sequence",
            allocationSize = 75)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long id;
    private String name;
    private String surname;
    @Email
    @Column(unique = true)
    private String email;
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Call> calls;
    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<Announcement> announcements;
    @ManyToMany
    private List<Announcement> views;
    @Column(nullable = false)
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }

}
