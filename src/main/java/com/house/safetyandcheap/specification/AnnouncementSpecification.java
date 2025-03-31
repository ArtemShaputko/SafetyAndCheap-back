package com.house.safetyandcheap.specification;

import com.house.safetyandcheap.dto.search.*;
import com.house.safetyandcheap.enumerations.InfrastructureType;
import com.house.safetyandcheap.model.*;
import jakarta.persistence.criteria.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementSpecification implements Specification<Announcement> {

    private final AnnouncementSearchDto searchDto;

    public AnnouncementSpecification(AnnouncementSearchDto searchDto) {
        this.searchDto = searchDto;
    }

    @Override
    public @NotNull Predicate toPredicate(@NotNull Root<Announcement> root,
                                          @NotNull CriteriaQuery<?> query,
                                          @NotNull CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        // Фильтрация по статусам объявления
        if (searchDto.getStatuses() != null && !searchDto.getStatuses().isEmpty()) {
            predicates.add(root.get("status").in(searchDto.getStatuses()));
        }

        // Фильтрация по предложениям (цене)
        OfferSearchDto offerDto = searchDto.getOffer();
        if (offerDto != null) {
            Join<Announcement, Offer> offerJoin = root.join("offer", JoinType.LEFT);
            addRangePredicate(offerDto.getMinPrice(), offerDto.getMaxPrice(), "price", offerJoin, cb, predicates);
            if (offerDto.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(offerJoin.get("price"), offerDto.getMinPrice()));
            }
            if (offerDto.getMaxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(offerJoin.get("price"), offerDto.getMaxPrice()));
            }
        }

        // Фильтрация по свойствам недвижимости
        PropertySearchDto propertyDto = searchDto.getProperty();
        if (propertyDto != null) {
            Join<Announcement, Property> propertyJoin = root.join("property", JoinType.LEFT);

            // Общие условия для Property
            addRangePredicate(propertyDto.getMinArea(), propertyDto.getMaxArea(), "area", propertyJoin, cb, predicates);
            addRangePredicate(propertyDto.getMinFloors(), propertyDto.getMaxFloors(), "floors", propertyJoin, cb, predicates);
            if (propertyDto.getInfrastructure() != null && !propertyDto.getInfrastructure().isEmpty()) {
                for (InfrastructureType type : propertyDto.getInfrastructure()) {
                    predicates.add(cb.isMember(type, propertyJoin.get("infrastructure")));
                }
            }

            switch (propertyDto) {
                case HouseSearchDto houseSearchDto ->
                        handleHouseConditions(houseSearchDto, propertyJoin, cb, predicates);
                case ApartmentSearchDto apartmentSearchDto ->
                        handleApartmentConditions(apartmentSearchDto, propertyJoin, cb, predicates);
                case RoomSearchDto roomSearchDto -> handleRoomConditions(roomSearchDto, propertyJoin, cb, predicates);
                default -> {
                }
            }
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }

    private void handleHouseConditions(HouseSearchDto houseDto,
                                       Join<Announcement, Property> propertyJoin,
                                       CriteriaBuilder cb,
                                       List<Predicate> predicates) {
        Join<Announcement, House> houseJoin = cb.treat(propertyJoin, House.class);
        if (houseDto.getEarthStatuses() != null && !houseDto.getEarthStatuses().isEmpty()) {
            predicates.add(houseJoin.get("earth_status").in(houseDto.getEarthStatuses()));
        }
        addRangePredicate(houseDto.getMinSiteArea(), houseDto.getMaxSiteArea(), "site_area", houseJoin, cb, predicates);
        addRangePredicate(houseDto.getMinBedrooms(), houseDto.getMaxBedrooms(), "bedrooms", houseJoin, cb, predicates);
    }

    private void handleApartmentConditions(ApartmentSearchDto apartmentDto,
                                           Join<Announcement, Property> propertyJoin,
                                           CriteriaBuilder cb,
                                           List<Predicate> predicates) {
        Join<Announcement, Apartment> apartmentJoin = cb.treat(propertyJoin, Apartment.class);
        addRangePredicate(apartmentDto.getMinRooms(), apartmentDto.getMaxRooms(), "rooms", apartmentJoin, cb, predicates);
        addRangePredicate(apartmentDto.getMinFloor(), apartmentDto.getMaxFloor(), "floor", apartmentJoin, cb, predicates);
        addRangePredicate(apartmentDto.getMinLivingArea(), apartmentDto.getMaxLivingArea(), "living_area", apartmentJoin, cb, predicates);
    }

    private void handleRoomConditions(RoomSearchDto roomDto,
                                      Join<Announcement, Property> propertyJoin,
                                      CriteriaBuilder cb,
                                      List<Predicate> predicates) {
        Join<Announcement, Room> roomJoin = cb.treat(propertyJoin, Room.class);
        addRangePredicate(roomDto.getMinRoomsInApartment(), roomDto.getMaxRoomsInApartment(), "apartment_rooms", roomJoin, cb, predicates);
        addRangePredicate(roomDto.getMinRoomsInOffer(), roomDto.getMaxRoomsInOffer(), "offer_rooms", roomJoin, cb, predicates);
        addRangePredicate(roomDto.getMinLivingArea(), roomDto.getMaxLivingArea(), "living_area", roomJoin, cb, predicates);
    }

    private <T extends Comparable<? super T>> void addRangePredicate(T min,
                                                                     T max,
                                                                     String fieldName, Join<?, ?> join,
                                                                     CriteriaBuilder cb,
                                                                     List<Predicate> predicates) {
        if (min != null) {
            predicates.add(cb.greaterThanOrEqualTo(join.get(fieldName), min));
        }
        if (max != null) {
            predicates.add(cb.lessThanOrEqualTo(join.get(fieldName), max));
        }
    }

}