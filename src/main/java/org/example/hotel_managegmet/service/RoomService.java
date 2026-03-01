package org.example.hotel_managegmet.service;

import lombok.RequiredArgsConstructor;
import org.example.hotel_managegmet.dto.request.*;
import org.example.hotel_managegmet.dto.response.*;
import org.example.hotel_managegmet.entity.*;
import org.example.hotel_managegmet.entity.enums.ReservationStatus;
import org.example.hotel_managegmet.entity.enums.RoomStatus;
import org.example.hotel_managegmet.exception.AppException;
import org.example.hotel_managegmet.exception.ErrorCode;
import org.example.hotel_managegmet.mapper.RoomMapper;
import org.example.hotel_managegmet.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final AmenityRepository amenityRepository;
    private final RoomMapper roomMapper;

    @Transactional
    public AmenityResponse createAmenity(AmenityRequest request) {
        return roomMapper.toResponse(amenityRepository.save(roomMapper.toEntity(request)));
    }

    @Transactional
    public RoomTypeResponse createRoomType(RoomTypeRequest request) {
        RoomType roomType = roomMapper.toEntity(request);
        if (request.getAmenityIds() != null && !request.getAmenityIds().isEmpty()) {
            List<Amenity> amenities = amenityRepository.findAllById(request.getAmenityIds());
            if (amenities.size() != request.getAmenityIds().size())
                throw new AppException(ErrorCode.AMENITY_NOT_FOUND);
            roomType.setAmenities(new HashSet<>(amenities));
        }
        return roomMapper.toResponse(roomTypeRepository.save(roomType));
    }

    @Transactional
    public RoomResponse createRoom(RoomRequest request) {
        if (roomRepository.findByRoomNumber(request.getRoomNumber()).isPresent())
            throw new AppException(ErrorCode.ROOM_NUMBER_EXISTED, request.getRoomNumber());
        RoomType roomType = roomTypeRepository.findById(request.getRoomTypeId())
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_TYPE_NOT_FOUND));
        Room room = roomMapper.toEntity(request);
        room.setRoomType(roomType);
        return roomMapper.toResponse(roomRepository.save(room));
    }

    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll().stream().map(roomMapper::toResponse).toList();
    }

    public RoomResponse getRoomById(Long id) {
        return roomMapper.toResponse(roomRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND, id)));
    }

    public List<RoomResponse> getRoomsByStatus(RoomStatus status) {
        return roomRepository.findByStatus(status).stream().map(roomMapper::toResponse).toList();
    }

    @Transactional
    public RoomResponse updateRoomStatus(Long id, RoomStatus status) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND, id));
        room.setStatus(status);
        return roomMapper.toResponse(roomRepository.save(room));
    }

    public List<RoomResponse> findAvailableRooms(String checkInStr, String checkOutStr) {
        LocalDate checkIn, checkOut;
        try {
            checkIn = LocalDate.parse(checkInStr);
            checkOut = LocalDate.parse(checkOutStr);
        } catch (Exception e) {
            throw new AppException(ErrorCode.INVALID_DATE_FORMAT);
        }
        if (!checkOut.isAfter(checkIn)) throw new AppException(ErrorCode.INVALID_DATE_RANGE);

        LocalDateTime checkInTime = checkIn.atTime(14, 0);
        LocalDateTime checkOutTime = checkOut.atTime(12, 0);

        return roomRepository.findAvailableRooms(
                RoomStatus.AVAILABLE, checkInTime, checkOutTime,
                ReservationStatus.CANCELLED, ReservationStatus.CHECKED_OUT
        ).stream().map(roomMapper::toResponse).toList();
    }

    Room findRoomEntityById(Long id) {
        return roomRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND, id));
    }
}
