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
@Table(name = "houses")
public class House extends Property{
    @Id
    @SequenceGenerator(name = "house_seq",
            sequenceName = "announcements_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "house_seq")
    private Long id;
    @Column(name = "earth_status")
    private String earthStatus;
    @Column(name = "site_area")
    private double siteArea;
    private int bedrooms;
}
