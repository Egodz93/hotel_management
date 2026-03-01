package org.example.hotel_managegmet.mapper;
import org.example.hotel_managegmet.dto.request.GuestRequest;
import org.example.hotel_managegmet.dto.response.GuestResponse;
import org.example.hotel_managegmet.entity.Guest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface GuestMapper {
    @Mapping(target = "fullName", expression = "java(guest.getFirstName() + \" \" + guest.getLastName())")
    @Mapping(target = "loyaltyDiscount", expression = "java(guest.getLoyaltyPoints() != null ? guest.getLoyaltyPoints() / 100.0 : 0.0)")
    GuestResponse toResponse(Guest guest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "loyaltyPoints", constant = "0")
    @Mapping(target = "reservations", ignore = true)
    Guest toEntity(GuestRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "loyaltyPoints", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    void updateEntityFromRequest(GuestRequest request, @MappingTarget Guest guest);
}
