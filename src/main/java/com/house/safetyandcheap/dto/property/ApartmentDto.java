package com.house.safetyandcheap.dto.property;

import com.house.safetyandcheap.model.Apartment;
import com.house.safetyandcheap.model.Property;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentDto extends PropertyDto{
    private int rooms;
    private int floor;
    private double livingArea;

    @Override
    public void fill(Property property) {
        super.fill(property);
        if(property instanceof Apartment apartment) {
            rooms = apartment.getRooms();
            floor = apartment.getFloor();
            livingArea = apartment.getLivingArea();
        }
    }

    @Override
    public Property fillModel() {
        Apartment apartment = new Apartment();
        setModelFields(apartment);
        apartment.setRooms(rooms);
        apartment.setFloor(floor);
        apartment.setLivingArea(livingArea);
        return null;
    }
}
