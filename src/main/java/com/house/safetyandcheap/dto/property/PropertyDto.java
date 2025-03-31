package com.house.safetyandcheap.dto.property;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.house.safetyandcheap.enumerations.InfrastructureType;
import com.house.safetyandcheap.model.Image;
import com.house.safetyandcheap.model.Property;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = HouseDto.class, name = "House"),
        @JsonSubTypes.Type(value = ApartmentDto.class, name = "Apartment"),
        @JsonSubTypes.Type(value = RoomDto.class, name = "Room")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class PropertyDto {
    private long id;
    private double latitude;
    private double longitude;
    private String address;
    private double area;
    private boolean bargain;
    private int floors;
    private List<ImageDto> photos;
    private List<InfrastructureType> infrastructure;

    public void fill(Property property) {
        this.id = property.getId();
        this.latitude = property.getLatitude();
        this.longitude = property.getLongitude();
        this.address = property.getAddress();
        this.area = property.getArea();
        this.bargain = property.isBargain();
        this.floors = property.getFloors();
        this.photos = property.getPhotos().stream().map(
                image -> new ImageDto(image.getId(), image.getUrl())
        ).toList();
        this.infrastructure = property.getInfrastructure();
    }

    public abstract Property fillModel();

    protected void setModelFields(Property property) {
        property.setLatitude(this.latitude);
        property.setLongitude(this.longitude);
        property.setAddress(this.address);
        property.setArea(this.area);
        property.setBargain(this.bargain);
        property.setFloors(this.floors);
        property.setInfrastructure(this.infrastructure);
        property.setPhotos(photos == null ? Collections.emptyList() :
                photos.stream()
                        .map(imageDto -> new Image(null, imageDto.getUrl(), property))
                        .toList());
    }
}
