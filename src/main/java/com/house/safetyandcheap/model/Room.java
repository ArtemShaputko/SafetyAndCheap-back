package com.house.safetyandcheap.model;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Builder
@Table(name = "rooms")
public class Room extends Property {
    @Id
    @SequenceGenerator(name = "room_seq",
            sequenceName = "announcements_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_seq")
    private Long id;
    @Column(name = "apartment_rooms")
    private int roomsInApartment;
    @Column(name = "offer_rooms")
    private int roomsInOffer;
    @Column(name = "living_area")
    private double livingArea;
}
