package com.house.safetyandcheap.dto.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomSearchDto extends PropertySearchDto{
    private Integer minRoomsInApartment;
    private Integer maxRoomsInApartment;
    private Integer minRoomsInOffer;
    private Integer maxRoomsInOffer;
    private Double minLivingArea;
    private Double maxLivingArea;
}
