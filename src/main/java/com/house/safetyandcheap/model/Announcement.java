package com.house.safetyandcheap.model;

import com.house.safetyandcheap.enumerations.StatusType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "announcements")
public class Announcement {
    @Id
    @SequenceGenerator(name = "announcements_seq",
            sequenceName = "announcements_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "announcements_seq")
    private Long id;
    @Enumerated(EnumType.ORDINAL)
    private StatusType status;
    @Column(name = "publish_date")
    private Date publishDate;
    private String description;
    @OneToOne(cascade = CascadeType.ALL)
    private Property property;
    @OneToOne(cascade = CascadeType.ALL)
    private Offer offer;
    @ManyToOne
    private User author;
    @ManyToMany(mappedBy = "views")
    private List<User> viewers;
}

