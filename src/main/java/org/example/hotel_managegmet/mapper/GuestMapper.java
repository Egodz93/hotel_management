package org.example.hotel_managegmet.mapper;

import org.example.hotel_managegmet.dto.request.GuestRequest;
import org.example.hotel_managegmet.dto.response.GuestResponse;
import org.example.hotel_managegmet.entity.Guest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GuestMapper {

    GuestResponse toResponse(Guest guest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    Guest toEntity(GuestRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    void updateEntityFromRequest(GuestRequest request, @MappingTarget Guest guest);
}
