package com.house.safetyandcheap.model;

import com.house.safetyandcheap.enumerations.InfrastructureType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED  )
@DiscriminatorColumn(name = "property_type", discriminatorType = DiscriminatorType.INTEGER)
@Table(name = "properties")
public class Property {
    @Id
    @SequenceGenerator(name = "property_seq",
            sequenceName = "announcements_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "property_seq")
    private Long id;

    private double latitude;
    private double longitude;

    private String address;
    private double area;
    private boolean bargain;
    private int floors;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "property_id")
    private List<Image> photos;

    @ElementCollection(targetClass = InfrastructureType.class)
    @CollectionTable(name = "property_infrastructure", joinColumns = @JoinColumn(name = "property_id"))
    @Enumerated(EnumType.ORDINAL)
    private List<InfrastructureType> infrastructure;
}
