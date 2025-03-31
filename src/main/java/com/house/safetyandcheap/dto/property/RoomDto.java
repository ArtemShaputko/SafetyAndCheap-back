package com.house.safetyandcheap.dto.property;

import com.house.safetyandcheap.model.Property;
import com.house.safetyandcheap.model.Room;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto extends PropertyDto {
    private int roomsInApartment;
    private int roomsInOffer;
    private double livingArea;

    @Override
    public void fill(Property property) {
        super.fill(property);
        if(property instanceof Room room) {
            roomsInApartment = room.getRoomsInApartment();
            roomsInOffer = room.getRoomsInOffer();
            livingArea = room.getLivingArea();
        }
    }

    @Override
    public Property fillModel() {
        Room room = new Room();
        setModelFields(room);
        room.setRoomsInApartment(roomsInApartment);
        room.setRoomsInOffer(roomsInOffer);
        room.setLivingArea(livingArea);
        return room;
    }
}
