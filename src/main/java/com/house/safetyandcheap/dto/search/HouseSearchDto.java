package com.house.safetyandcheap.dto.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseSearchDto extends PropertySearchDto{
    private List<String> earthStatuses;
    private Double minSiteArea;
    private Double maxSiteArea;
    private Integer minBedrooms;
    private Integer maxBedrooms;
}
