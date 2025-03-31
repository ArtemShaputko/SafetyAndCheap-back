package com.house.safetyandcheap.dto.search;

import com.house.safetyandcheap.enumerations.StatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementSearchDto {
    private List<StatusType> statuses;
    private PropertySearchDto property;
    private OfferSearchDto offer;
}
