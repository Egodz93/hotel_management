package org.example.hotel_managegmet.service;

import lombok.RequiredArgsConstructor;
import org.example.hotel_managegmet.dto.request.ReservationRequest;
import org.example.hotel_managegmet.dto.response.ReservationResponse;
import org.example.hotel_managegmet.entity.*;
import org.example.hotel_managegmet.entity.enums.ReservationStatus;
import org.example.hotel_managegmet.entity.enums.RoomStatus;
import org.example.hotel_managegmet.exception.AppException;
import org.example.hotel_managegmet.exception.ErrorCode;
import org.example.hotel_managegmet.mapper.ReservationMapper;
import org.example.hotel_managegmet.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationRoomRepository reservationRoomRepository;
    private final GuestRepository guestRepository;
    private final RoomRepository roomRepository;
    private final ReservationMapper reservationMapper;

    @Transactional
    public ReservationResponse createReservation(ReservationRequest request) {
        if (request.getCheckOutDate().isBefore(request.getCheckInDate())) {
            throw new AppException(ErrorCode.INTERNAL_ERROR, "Ngày trả phòng phải sau ngày nhận phòng");
        }

        long nights = ChronoUnit.DAYS.between(request.getCheckInDate().toLocalDate(), request.getCheckOutDate().toLocalDate());
        if (nights <= 0) nights = 1;

        Guest guest = guestRepository.findById(request.getGuestId())
                .orElseThrow(() -> new AppException(ErrorCode.GUEST_NOT_FOUND, request.getGuestId()));

        Reservation reservation = reservationMapper.toEntity(request);
        reservation.setGuest(guest);
        reservation.setConfirmationNumber(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setReservationRooms(new ArrayList<>());

        Reservation savedReservation = reservationRepository.save(reservation);

        List<Room> rooms = roomRepository.findAllById(request.getRoomIds());
        if (rooms.size() != request.getRoomIds().size()) {
            throw new AppException(ErrorCode.INTERNAL_ERROR, "Một số ID phòng không tồn tại");
        }

        for (Room room : rooms) {
            if (room.getStatus() != RoomStatus.AVAILABLE) {
                throw new AppException(ErrorCode.ROOM_NOT_AVAILABLE, room.getRoomNumber());
            }

            double pricePerNight = room.getRoomType().getBasePrice();
            double totalPrice = pricePerNight * nights;

            ReservationRoom resRoom = new ReservationRoom();
            resRoom.setReservation(savedReservation);
            resRoom.setRoom(room);
            resRoom.setPricePerNight(pricePerNight);
            resRoom.setTotalPrice(totalPrice);

            reservationRoomRepository.save(resRoom);

            savedReservation.getReservationRooms().add(resRoom);

            room.setStatus(RoomStatus.RESERVED);
            roomRepository.save(room);
        }

        return reservationMapper.toResponse(savedReservation);
    }

    @Transactional
    public void checkIn(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new AppException(ErrorCode.RESERVATION_NOT_FOUND, reservationId));

        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
            throw new AppException(ErrorCode.INTERNAL_ERROR, "Chỉ đơn đặt đã xác nhận mới có thể Check-in");
        }

        reservation.setStatus(ReservationStatus.CHECKED_IN);

        if (reservation.getReservationRooms() != null) {
            reservation.getReservationRooms().forEach(resRoom -> {
                Room room = resRoom.getRoom();
                room.setStatus(RoomStatus.OCCUPIED);
                roomRepository.save(room);
            });
        }

        reservationRepository.save(reservation);
    }

    @Transactional
    public void checkOut(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new AppException(ErrorCode.RESERVATION_NOT_FOUND, reservationId));

        if (reservation.getStatus() != ReservationStatus.CHECKED_IN) {
            throw new AppException(ErrorCode.INTERNAL_ERROR, "Khách hàng phải hoàn thành Check-in trước khi Check-out");
        }

        reservation.setStatus(ReservationStatus.CHECKED_OUT);

        if (reservation.getReservationRooms() != null) {
            reservation.getReservationRooms().forEach(resRoom -> {
                Room room = resRoom.getRoom();
                room.setStatus(RoomStatus.CLEANING);
                roomRepository.save(room);
            });
        }

        reservationRepository.save(reservation);
    }

    @Transactional
    public void cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESERVATION_NOT_FOUND, id));

        reservation.setStatus(ReservationStatus.CANCELLED);

        if (reservation.getReservationRooms() != null) {
            reservation.getReservationRooms().forEach(rr -> {
                rr.getRoom().setStatus(RoomStatus.AVAILABLE);
                roomRepository.save(rr.getRoom());
            });
        }
        reservationRepository.save(reservation);
    }
}
