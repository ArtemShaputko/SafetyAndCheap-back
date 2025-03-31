package com.house.safetyandcheap.dto.property;

import com.house.safetyandcheap.model.House;
import com.house.safetyandcheap.model.Property;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseDto extends PropertyDto {
    private String earthStatus;
    private double siteArea;
    private int bedrooms;

    @Override
    public void fill(Property property) {
        super.fill(property);
        if(property instanceof House house) {
            earthStatus = house.getEarthStatus();
            siteArea = house.getSiteArea();
            bedrooms = house.getBedrooms();
        }
    }

    @Override
    public Property fillModel() {
        House property = new House();
        super.setModelFields(property);
        property.setEarthStatus(earthStatus);
        property.setSiteArea(siteArea);
        property.setBedrooms(bedrooms);
        return property;
    }
}
