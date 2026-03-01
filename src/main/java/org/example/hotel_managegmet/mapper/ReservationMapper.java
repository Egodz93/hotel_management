package org.example.hotel_managegmet.mapper;
import org.example.hotel_managegmet.dto.request.ReservationRequest;
import org.example.hotel_managegmet.dto.response.ReservationResponse;
import org.example.hotel_managegmet.entity.Reservation;
import org.example.hotel_managegmet.entity.ReservationRoom;
import org.mapstruct.*;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    @Mapping(target = "guestId", source = "guest.id")
    @Mapping(target = "guestFullName", expression = "java(reservation.getGuest().getFirstName() + \" \" + reservation.getGuest().getLastName())")
    @Mapping(target = "roomNumbers", expression = "java(mapRooms(reservation.getReservationRooms()))")
    @Mapping(target = "nights", expression = "java(java.time.temporal.ChronoUnit.DAYS.between(reservation.getCheckInDate().toLocalDate(), reservation.getCheckOutDate().toLocalDate()))")
    @Mapping(target = "totalAmount", expression = "java(reservation.getReservationRooms() != null ? reservation.getReservationRooms().stream().mapToDouble(rr -> rr.getTotalPrice()).sum() : 0.0)")
    @Mapping(target = "statusDisplay", expression = "java(reservation.getStatus().getDisplayName())")
    ReservationResponse toResponse(Reservation reservation);

    @Mapping(target = "guest", ignore = true)
    @Mapping(target = "reservationRooms", ignore = true)
    @Mapping(target = "serviceRequests", ignore = true)
    @Mapping(target = "invoice", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "confirmationNumber", ignore = true)
    @Mapping(target = "status", ignore = true)
    Reservation toEntity(ReservationRequest request);

    default List<String> mapRooms(List<ReservationRoom> rooms) {
        if (rooms == null) return List.of();
        return rooms.stream().map(rr -> String.valueOf(rr.getRoom().getRoomNumber())).collect(Collectors.toList());
    }
}
