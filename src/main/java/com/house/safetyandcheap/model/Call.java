package com.house.safetyandcheap.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "calls")
public class Call {
    @Id
    @SequenceGenerator(name = "call_seq",
            sequenceName = "call_sequence",
            allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "call_seq")
    private Long id;
    @DateTimeFormat
    private LocalDate date;
    private Time time;
    @ManyToOne
    private User user;
    @ManyToOne
    private Announcement announcement;
}
