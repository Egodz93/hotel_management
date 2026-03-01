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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        if (!request.getCheckOutDate().isAfter(request.getCheckInDate()))
            throw new AppException(ErrorCode.INVALID_DATE_RANGE);

        long nights = ChronoUnit.DAYS.between(
                request.getCheckInDate().toLocalDate(), request.getCheckOutDate().toLocalDate());
        if (nights > 30) throw new AppException(ErrorCode.STAY_TOO_LONG);

        Guest guest = guestRepository.findById(request.getGuestId())
                .orElseThrow(() -> new AppException(ErrorCode.GUEST_NOT_FOUND, request.getGuestId()));

        String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String randomPart = UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        String confirmationNumber = "HTL" + datePart + "-" + randomPart;

        Reservation reservation = reservationMapper.toEntity(request);
        reservation.setGuest(guest);
        reservation.setConfirmationNumber(confirmationNumber);
        reservation.setStatus(ReservationStatus.PENDING);

        Reservation saved = reservationRepository.save(reservation);

        List<Room> rooms = roomRepository.findAllById(request.getRoomIds());
        if (rooms.size() != request.getRoomIds().size())
            throw new AppException(ErrorCode.SOME_ROOMS_NOT_FOUND);

        List<ReservationRoom> resRooms = new ArrayList<>();
        for (Room room : rooms) {
            boolean isBooked = reservationRoomRepository.existsByRoomIdAndDateRange(
                    room.getId(), request.getCheckInDate(), request.getCheckOutDate(),
                    ReservationStatus.CANCELLED, ReservationStatus.CHECKED_OUT);
            if (isBooked)
                throw new AppException(ErrorCode.ROOM_NOT_AVAILABLE, room.getRoomNumber());

            ReservationRoom rr = new ReservationRoom();
            rr.setReservation(saved);
            rr.setRoom(room);
            rr.setPricePerNight(room.getRoomType().getBasePrice());
            rr.setTotalPrice(room.getRoomType().getBasePrice() * nights);
            resRooms.add(rr);
        }
        reservationRoomRepository.saveAll(resRooms);
        saved.setReservationRooms(resRooms);

        return reservationMapper.toResponse(saved);
    }

    public ReservationResponse getById(Long id) {
        return reservationMapper.toResponse(findById(id));
    }

    public ReservationResponse getByConfirmationNumber(String confirmationNumber) {
        Reservation r = reservationRepository.findByConfirmationNumber(confirmationNumber)
                .orElseThrow(() -> new AppException(ErrorCode.RESERVATION_NOT_FOUND, confirmationNumber));
        return reservationMapper.toResponse(r);
    }

    public List<ReservationResponse> getByGuestId(Long guestId) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new AppException(ErrorCode.GUEST_NOT_FOUND, guestId));
        return reservationRepository.findByGuestOrderByCheckInDateDesc(guest)
                .stream().map(reservationMapper::toResponse).toList();
    }

    @Transactional
    public ReservationResponse confirmReservation(Long id) {
        Reservation r = findById(id);
        if (r.getStatus() != ReservationStatus.PENDING)
            throw new AppException(ErrorCode.CANNOT_MODIFY_RESERVATION);
        r.setStatus(ReservationStatus.CONFIRMED);
        return reservationMapper.toResponse(reservationRepository.save(r));
    }

    @Transactional
    public void checkIn(Long id) {
        Reservation r = findById(id);

        if (r.getStatus() != ReservationStatus.PENDING && r.getStatus() != ReservationStatus.CONFIRMED)
            throw new AppException(ErrorCode.CANNOT_CHECKIN);

        if (LocalDateTime.now().isBefore(r.getCheckInDate().minusHours(2)))
            throw new AppException(ErrorCode.CHECKIN_TOO_EARLY, r.getCheckInDate());

        r.setStatus(ReservationStatus.CHECKED_IN);
        r.getReservationRooms().forEach(rr -> {
            rr.getRoom().setStatus(RoomStatus.OCCUPIED);
            roomRepository.save(rr.getRoom());
        });
        reservationRepository.save(r);
    }

    @Transactional
    public void checkOut(Long id) {
        Reservation r = findById(id);
        if (r.getStatus() != ReservationStatus.CHECKED_IN)
            throw new AppException(ErrorCode.CANNOT_CHECKOUT);

        r.setStatus(ReservationStatus.CHECKED_OUT);
        r.getReservationRooms().forEach(rr -> {
            rr.getRoom().setStatus(RoomStatus.CLEANING);
            roomRepository.save(rr.getRoom());
        });

        double total = r.getReservationRooms().stream().mapToDouble(ReservationRoom::getTotalPrice).sum();
        int earnedPoints = (int) (total * 10);
        Guest guest = r.getGuest();
        guest.setLoyaltyPoints((guest.getLoyaltyPoints() != null ? guest.getLoyaltyPoints() : 0) + earnedPoints);
        guestRepository.save(guest);

        reservationRepository.save(r);
    }

    @Transactional
    public void cancelReservation(Long id) {
        Reservation r = findById(id);
        if (r.getStatus() == ReservationStatus.CHECKED_IN || r.getStatus() == ReservationStatus.CHECKED_OUT)
            throw new AppException(ErrorCode.CANNOT_CANCEL_RESERVATION);

        r.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(r);
    }

    private Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESERVATION_NOT_FOUND, id));
    }
}
