package com.house.safetyandcheap.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "apartments")
public class Apartment extends Property {
    @Id
    @SequenceGenerator(name = "apartment_seq",
            sequenceName = "announcements_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "apartment_seq")
    private Long id;
    private int rooms;
    private int floor;
    @Column(name = "living_area")
    private double livingArea;
}
