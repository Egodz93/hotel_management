package org.example.hotel_managegmet.mapper;

import org.example.hotel_managegmet.dto.request.*;
import org.example.hotel_managegmet.dto.response.*;
import org.example.hotel_managegmet.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    AmenityResponse toResponse(Amenity amenity);
    Amenity toEntity(AmenityRequest request);

    RoomTypeResponse toResponse(RoomType roomType);

    @Mapping(target = "amenities", ignore = true)
    @Mapping(target = "id", ignore = true)
    RoomType toEntity(RoomTypeRequest request);

    @Mapping(target = "roomTypeName", source = "roomType.name")
    RoomResponse toResponse(Room room);

    @Mapping(target = "roomType", ignore = true)
    @Mapping(target = "id", ignore = true)
    Room toEntity(RoomRequest request);

    void updateRoomFromRequest(RoomRequest request, @MappingTarget Room room);
}
