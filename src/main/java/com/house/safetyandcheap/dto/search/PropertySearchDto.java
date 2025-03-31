package com.house.safetyandcheap.dto.search;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.house.safetyandcheap.enumerations.InfrastructureType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = HouseSearchDto.class, name = "House"),
        @JsonSubTypes.Type(value = ApartmentSearchDto.class, name = "Apartment"),
        @JsonSubTypes.Type(value = RoomSearchDto.class, name = "Room")
})

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class PropertySearchDto {
    private Double minArea;
    private Double maxArea;
    private Integer minFloors;
    private Integer maxFloors;
    private List<InfrastructureType> infrastructure;
}
