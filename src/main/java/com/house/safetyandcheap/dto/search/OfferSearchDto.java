package com.house.safetyandcheap.dto.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferSearchDto {
    Integer minPrice;
    Integer maxPrice;
}
