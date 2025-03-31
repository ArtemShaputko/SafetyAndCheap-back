package com.house.safetyandcheap.mapper;

import com.house.safetyandcheap.dto.*;
import com.house.safetyandcheap.dto.auth.UserVerificationDto;
import com.house.safetyandcheap.dto.property.*;
import com.house.safetyandcheap.model.*;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Mapper {
    private final PasswordEncoder passwordEncoder;

    private PropertyDto createPropertyDto(Property property) {
        if (property instanceof House) {
            return new HouseDto();
        } else if (property instanceof Room) {
            return new RoomDto();
        } else if(property instanceof Apartment){
            return new ApartmentDto();
        }
        throw new IllegalArgumentException("Invalid property type");
    }

    public User userFromVerificationDto(UserVerificationDto userVerificationDto) {
        User user = new User();
        user.setEmail(userVerificationDto.getEmail());
        user.setPassword(passwordEncoder.encode(userVerificationDto.getPassword()));
        user.setName(userVerificationDto.getName());
        return user;
    }

    public UserDto userToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        return userDto;
    }

    public Announcement announcementFromDto(AnnouncementDto announcementDto) {
        Announcement announcement = new Announcement();
        announcement.setStatus(announcementDto.getStatus());
        announcement.setDescription(announcementDto.getDescription());
        announcement.setPublishDate(announcementDto.getPublishDate());
        announcement.setOffer(offerFromDto(announcementDto.getOffer()));
        announcement.setProperty(announcementDto.getProperty().fillModel());
        return announcement;
    }

    public Offer offerFromDto(OfferDto offerDto) {
        Offer offer = new Offer();
        offer.setPrice(offerDto.getPrice());
        return offer;
    }

    public AnnouncementDto announcementToDto(Announcement announcement) {
        AnnouncementDto announcementDto = new AnnouncementDto();
        announcementDto.setId(announcement.getId());
        announcementDto.setStatus(announcement.getStatus());
        announcementDto.setDescription(announcement.getDescription());
        announcementDto.setPublishDate(announcement.getPublishDate());
        announcementDto.setOffer(offerToDto(announcement.getOffer()));
        announcementDto.setAuthor(userToDto(announcement.getAuthor()));
        PropertyDto propertyDto = createPropertyDto(announcement.getProperty());
        propertyDto.fill(announcement.getProperty());
        announcementDto.setProperty(propertyDto);
        return announcementDto;
    }

    public OfferDto offerToDto(Offer offer) {
        OfferDto offerDto = new OfferDto();
        offerDto.setId(offer.getId());
        offerDto.setPrice(offer.getPrice());
        return offerDto;
    }

}
