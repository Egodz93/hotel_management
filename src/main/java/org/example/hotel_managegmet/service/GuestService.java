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

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestService {
    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;

    public List<GuestResponse> getAllGuests() {
        return guestRepository.findAll().stream().map(guestMapper::toResponse).toList();
    }

    public GuestResponse getGuestById(Long id) {
        return guestMapper.toResponse(findById(id));
    }

    @Transactional
    public GuestResponse createGuest(GuestRequest request) {

        validateAge(request.getDateOfBirth());

        if (guestRepository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS, request.getEmail());
        if (guestRepository.existsByPhone(request.getPhone()))
            throw new AppException(ErrorCode.PHONE_ALREADY_EXISTS, request.getPhone());
        if (guestRepository.existsByIdNumber(request.getIdNumber()))
            throw new AppException(ErrorCode.ID_NUMBER_ALREADY_EXISTS, request.getIdNumber());

        Guest guest = guestMapper.toEntity(request);
        guest.setLoyaltyPoints(0);
        return guestMapper.toResponse(guestRepository.save(guest));
    }

    @Transactional
    public GuestResponse updateGuest(Long id, GuestRequest request) {
        Guest guest = findById(id);
        validateAge(request.getDateOfBirth());

        if (!guest.getEmail().equals(request.getEmail()) && guestRepository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS, request.getEmail());
        if (!guest.getPhone().equals(request.getPhone()) && guestRepository.existsByPhone(request.getPhone()))
            throw new AppException(ErrorCode.PHONE_ALREADY_EXISTS, request.getPhone());
        if (!guest.getIdNumber().equals(request.getIdNumber()) && guestRepository.existsByIdNumber(request.getIdNumber()))
            throw new AppException(ErrorCode.ID_NUMBER_ALREADY_EXISTS, request.getIdNumber());

        guestMapper.updateEntityFromRequest(request, guest);
        return guestMapper.toResponse(guestRepository.save(guest));
    }

    @Transactional
    public GuestResponse addLoyaltyPoints(Long id, Integer points) {
        Guest guest = findById(id);
        int current = guest.getLoyaltyPoints() != null ? guest.getLoyaltyPoints() : 0;
        guest.setLoyaltyPoints(current + points);
        return guestMapper.toResponse(guestRepository.save(guest));
    }

    @Transactional
    public void deleteGuest(Long id) {
        if (!guestRepository.existsById(id)) throw new AppException(ErrorCode.GUEST_NOT_FOUND, id);
        guestRepository.deleteById(id);
    }

    public Guest findById(Long id) {
        return guestRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.GUEST_NOT_FOUND, id));
    }

    private void validateAge(LocalDate dob) {
        if (dob != null && Period.between(dob, LocalDate.now()).getYears() < 18)
            throw new AppException(ErrorCode.GUEST_UNDERAGE);
    }
}
