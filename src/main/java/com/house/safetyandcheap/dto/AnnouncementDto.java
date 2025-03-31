package com.house.safetyandcheap.dto;

import com.house.safetyandcheap.dto.property.PropertyDto;
import com.house.safetyandcheap.enumerations.StatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementDto {
    private Long id;
    private StatusType status;
    private Date publishDate;
    private String description;
    private UserDto author;
    private PropertyDto property;
    private OfferDto offer;
}
