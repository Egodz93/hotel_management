package org.example.hotel_managegmet.service;

import lombok.RequiredArgsConstructor;
import org.example.hotel_managegmet.dto.request.ServiceRequest;
import org.example.hotel_managegmet.dto.request.ServiceRequestRequest;
import org.example.hotel_managegmet.dto.response.ServiceRequestResponse;
import org.example.hotel_managegmet.dto.response.ServiceResponse;
import org.example.hotel_managegmet.entity.HotelService;
import org.example.hotel_managegmet.entity.Reservation;
import org.example.hotel_managegmet.entity.enums.ServiceRequestStatus;
import org.example.hotel_managegmet.exception.AppException;
import org.example.hotel_managegmet.exception.ErrorCode;
import org.example.hotel_managegmet.mapper.ServiceMapper;
import org.example.hotel_managegmet.repository.ReservationRepository;
import org.example.hotel_managegmet.repository.ServiceRepository;
import org.example.hotel_managegmet.repository.ServiceRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelServiceService {
    private final ServiceRepository serviceRepository;
    private final ServiceRequestRepository serviceRequestRepository;
    private final ReservationRepository reservationRepository;
    private final ServiceMapper serviceMapper;

    @Transactional
    public ServiceResponse createService(ServiceRequest request) {
        return serviceMapper.toResponse(serviceRepository.save(serviceMapper.toEntity(request)));
    }

    public List<ServiceResponse> getAllServices() {
        return serviceRepository.findAll().stream().map(serviceMapper::toResponse).toList();
    }

    public List<ServiceResponse> getActiveServices() {
        return serviceRepository.findByIsActiveTrue().stream().map(serviceMapper::toResponse).toList();
    }

    public List<ServiceResponse> getServicesByCategory(String category) {
        return serviceRepository.findByCategory(category).stream().map(serviceMapper::toResponse).toList();
    }

    @Transactional
    public ServiceRequestResponse createServiceRequest(ServiceRequestRequest request) {
        Reservation reservation = reservationRepository.findById(request.getReservationId())
                .orElseThrow(() -> new AppException(ErrorCode.RESERVATION_NOT_FOUND, request.getReservationId()));

        HotelService service = serviceRepository.findById(request.getServiceId())
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND, request.getServiceId()));

        if (!service.getIsActive()) throw new AppException(ErrorCode.SERVICE_INACTIVE);

        org.example.hotel_managegmet.entity.ServiceRequest sr = new org.example.hotel_managegmet.entity.ServiceRequest();
        sr.setReservation(reservation);
        sr.setService(service);
        sr.setQuantity(request.getQuantity());
        sr.setStatus(ServiceRequestStatus.PENDING);
        sr.setRequestedAt(LocalDateTime.now());

        return serviceMapper.toResponse(serviceRequestRepository.save(sr));
    }

    @Transactional
    public ServiceRequestResponse updateServiceRequestStatus(Long id, ServiceRequestStatus status) {
        org.example.hotel_managegmet.entity.ServiceRequest sr = serviceRequestRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_REQUEST_NOT_FOUND, id));
        sr.setStatus(status);
        if (status == ServiceRequestStatus.COMPLETED) sr.setCompletedAt(LocalDateTime.now());
        return serviceMapper.toResponse(serviceRequestRepository.save(sr));
    }

    public List<ServiceRequestResponse> getServiceRequestsByReservation(Long reservationId) {
        return serviceRequestRepository.findByReservationId(reservationId)
                .stream().map(serviceMapper::toResponse).toList();
    }
}
