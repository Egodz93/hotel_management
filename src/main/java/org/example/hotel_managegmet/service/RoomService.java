package org.example.hotel_managegmet.service;

import lombok.RequiredArgsConstructor;
import org.example.hotel_managegmet.dto.request.*;
import org.example.hotel_managegmet.dto.response.*;
import org.example.hotel_managegmet.entity.*;
import org.example.hotel_managegmet.entity.enums.RoomStatus;
import org.example.hotel_managegmet.exception.AppException;
import org.example.hotel_managegmet.exception.ErrorCode;
import org.example.hotel_managegmet.mapper.RoomMapper;
import org.example.hotel_managegmet.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public RoomResponse createRoom(RoomRequest request) {
        if (roomRepository.findByRoomNumber(request.getRoomNumber()).isPresent()) {
            throw new AppException(ErrorCode.INTERNAL_ERROR, "Số phòng " + request.getRoomNumber() + " đã tồn tại");
        }

        RoomType roomType = roomTypeRepository.findById(request.getRoomTypeId())
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_TYPE_NOT_FOUND));

        Room room = roomMapper.toEntity(request);
        room.setRoomType(roomType);

        Room savedRoom = roomRepository.save(room);
        return roomMapper.toResponse(savedRoom);
    }

    @Transactional
    public AmenityResponse createAmenity(AmenityRequest request) {
        Amenity amenity = roomMapper.toEntity(request);
        Amenity savedAmenity = amenityRepository.save(amenity);
        return roomMapper.toResponse(savedAmenity);
    }

    @Transactional
    public RoomTypeResponse createRoomType(RoomTypeRequest request) {
        RoomType roomType = roomMapper.toEntity(request);

        if (request.getAmenityIds() != null && !request.getAmenityIds().isEmpty()) {
            List<Amenity> amenities = amenityRepository.findAllById(request.getAmenityIds());
            if (amenities.size() != request.getAmenityIds().size()) {
                throw new AppException(ErrorCode.AMENITY_NOT_FOUND);
            }
            roomType.setAmenities(new HashSet<>(amenities));
        }

        RoomType savedType = roomTypeRepository.save(roomType);
        return roomMapper.toResponse(savedType);
    }

    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(roomMapper::toResponse)
                .toList();
    }

    public RoomResponse getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND, id));
        return roomMapper.toResponse(room);
    }

    public List<RoomResponse> getRoomsByStatus(RoomStatus status) {
        return roomRepository.findByStatus(status).stream()
                .map(roomMapper::toResponse)
                .toList();
    }

}
