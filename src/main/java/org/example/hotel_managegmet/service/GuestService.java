package org.example.hotel_managegmet.service;

import lombok.RequiredArgsConstructor;
import org.example.hotel_managegmet.dto.request.GuestRequest;
import org.example.hotel_managegmet.dto.response.GuestResponse;
import org.example.hotel_managegmet.entity.Guest;
import org.example.hotel_managegmet.exception.AppException;
import org.example.hotel_managegmet.exception.ErrorCode;
import org.example.hotel_managegmet.mapper.GuestMapper;
import org.example.hotel_managegmet.repository.GuestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuestService {
    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;

    public List<GuestResponse> getAllGuests() {
        return guestRepository.findAll().stream()
                .map(guestMapper::toResponse)
                .collect(Collectors.toList());
    }

    public GuestResponse getGuestById(Long id) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.GUEST_NOT_FOUND, id));
        return guestMapper.toResponse(guest);
    }

    @Transactional
    public GuestResponse createGuest(GuestRequest request) {
        if (guestRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS, request.getEmail());
        }
        Guest guest = guestMapper.toEntity(request);
        return guestMapper.toResponse(guestRepository.save(guest));
    }

    @Transactional
    public GuestResponse updateGuest(Long id, GuestRequest request) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.GUEST_NOT_FOUND, id));

        guestMapper.updateEntityFromRequest(request, guest);
        return guestMapper.toResponse(guestRepository.save(guest));
    }

    @Transactional
    public void addLoyaltyPoints(Long id, Integer points) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.GUEST_NOT_FOUND, id));

        Integer currentPoints = guest.getLoyaltyPoints() != null ? guest.getLoyaltyPoints() : 0;
        guest.setLoyaltyPoints(currentPoints + points);
        guestRepository.save(guest);
    }
}
