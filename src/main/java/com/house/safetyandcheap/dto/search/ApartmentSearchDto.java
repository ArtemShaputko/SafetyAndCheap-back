package com.house.safetyandcheap.dto.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentSearchDto extends PropertySearchDto{
    private Integer minRooms;
    private Integer maxRooms;
    private Integer minFloor;
    private Integer maxFloor;
    private Double minLivingArea;
    private Double maxLivingArea;
}
