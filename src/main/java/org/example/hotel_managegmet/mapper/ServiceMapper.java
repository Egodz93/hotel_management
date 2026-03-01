package org.example.hotel_managegmet.mapper;

import org.example.hotel_managegmet.dto.request.ServiceRequest;
import org.example.hotel_managegmet.dto.response.ServiceRequestResponse;
import org.example.hotel_managegmet.dto.response.ServiceResponse;
import org.example.hotel_managegmet.entity.HotelService;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    ServiceResponse toResponse(HotelService service);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "serviceRequests", ignore = true)
    HotelService toEntity(ServiceRequest request);

    @Mapping(target = "reservationId", source = "reservation.id")
    @Mapping(target = "confirmationNumber", source = "reservation.confirmationNumber")
    @Mapping(target = "serviceName", source = "service.name")
    @Mapping(target = "servicePrice", source = "service.price")
    @Mapping(target = "totalCost", expression = "java(sr.getService().getPrice() * sr.getQuantity())")
    @Mapping(target = "statusDisplay", expression = "java(sr.getStatus().getDisplayName())")
    ServiceRequestResponse toResponse(org.example.hotel_managegmet.entity.ServiceRequest sr);
}
