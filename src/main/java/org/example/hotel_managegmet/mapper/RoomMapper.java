package org.example.hotel_managegmet.mapper;
import org.example.hotel_managegmet.dto.request.*;
import org.example.hotel_managegmet.dto.response.*;
import org.example.hotel_managegmet.entity.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    AmenityResponse toResponse(Amenity amenity);
    Amenity toEntity(AmenityRequest request);

    @Mapping(target = "amenities", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rooms", ignore = true)
    RoomType toEntity(RoomTypeRequest request);
    RoomTypeResponse toResponse(RoomType roomType);

    @Mapping(target = "roomTypeId", source = "roomType.id")
    @Mapping(target = "roomTypeName", source = "roomType.name")
    @Mapping(target = "basePrice", source = "roomType.basePrice")
    @Mapping(target = "maxOccupancy", source = "roomType.maxOccupancy")
    @Mapping(target = "statusDisplay", expression = "java(room.getStatus().getDisplayName())")
    RoomResponse toResponse(Room room);

    @Mapping(target = "roomType", ignore = true)
    @Mapping(target = "id", ignore = true)
    Room toEntity(RoomRequest request);
}
