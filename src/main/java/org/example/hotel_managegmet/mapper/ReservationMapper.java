package org.example.hotel_managegmet.mapper;

import org.example.hotel_managegmet.dto.request.ReservationRequest;
import org.example.hotel_managegmet.dto.response.ReservationResponse;
import org.example.hotel_managegmet.entity.Reservation;
import org.example.hotel_managegmet.entity.ReservationRoom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(target = "guestFullName", expression = "java(reservation.getGuest().getFirstName() + \" \" + reservation.getGuest().getLastName())")
    @Mapping(target = "roomNumbers", expression = "java(mapRooms(reservation.getReservationRooms()))")
    ReservationResponse toResponse(Reservation reservation);

    @Mapping(target = "guest", ignore = true)
    @Mapping(target = "reservationRooms", ignore = true)
    @Mapping(target = "id", ignore = true)
    Reservation toEntity(ReservationRequest request);

    default List<String> mapRooms(List<ReservationRoom> reservationRooms) {
        if (reservationRooms == null) return List.of();
        return reservationRooms.stream()
                .map(rr -> String.valueOf(rr.getRoom().getRoomNumber())) // Ép kiểu sang String
                .collect(Collectors.toList());
    }
}
