package com.house.safetyandcheap.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "offers")
public class Offer {
    @Id
    @SequenceGenerator(name = "offer_seq",
            sequenceName = "announcements_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offer_seq")
    private Long id;
    private int price;
}
