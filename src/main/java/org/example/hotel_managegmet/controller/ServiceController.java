package org.example.hotel_managegmet.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.hotel_managegmet.dto.request.ServiceRequest;
import org.example.hotel_managegmet.dto.request.ServiceRequestRequest;
import org.example.hotel_managegmet.dto.response.ApiResponse;
import org.example.hotel_managegmet.dto.response.ServiceRequestResponse;
import org.example.hotel_managegmet.dto.response.ServiceResponse;
import org.example.hotel_managegmet.entity.enums.ServiceRequestStatus;
import org.example.hotel_managegmet.service.HotelServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {
    private final HotelServiceService hotelServiceService;

    @PostMapping
    public ResponseEntity<ApiResponse<ServiceResponse>> createService(@Valid @RequestBody ServiceRequest request, HttpServletRequest req) {
        return created(hotelServiceService.createService(request), "Tạo dịch vụ thành công", req);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ServiceResponse>>> getAllServices(HttpServletRequest req) {
        return ok(hotelServiceService.getAllServices(), "Lấy danh sách dịch vụ thành công", req);
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<ServiceResponse>>> getActiveServices(HttpServletRequest req) {
        return ok(hotelServiceService.getActiveServices(), "Lấy danh sách dịch vụ đang hoạt động thành công", req);
    }

    @PostMapping("/requests")
    public ResponseEntity<ApiResponse<ServiceRequestResponse>> createRequest(@Valid @RequestBody ServiceRequestRequest request, HttpServletRequest req) {
        return created(hotelServiceService.createServiceRequest(request), "Yêu cầu dịch vụ đã được ghi nhận", req);
    }

    @PatchMapping("/requests/{id}/status")
    public ResponseEntity<ApiResponse<ServiceRequestResponse>> updateStatus(
            @PathVariable Long id, @RequestParam ServiceRequestStatus status, HttpServletRequest req) {
        return ok(hotelServiceService.updateServiceRequestStatus(id, status), "Cập nhật trạng thái yêu cầu dịch vụ thành công", req);
    }

    @GetMapping("/requests/reservation/{reservationId}")
    public ResponseEntity<ApiResponse<List<ServiceRequestResponse>>> getByReservation(@PathVariable Long reservationId, HttpServletRequest req) {
        return ok(hotelServiceService.getServiceRequestsByReservation(reservationId), "Lấy danh sách yêu cầu dịch vụ thành công", req);
    }

    private <T> ResponseEntity<ApiResponse<T>> ok(T data, String message, HttpServletRequest req) {
        return ResponseEntity.ok(ApiResponse.<T>builder().code(200).message(message)
                .timestamp(Instant.now().toString()).path(req.getRequestURI()).result(data).build());
    }
    private <T> ResponseEntity<ApiResponse<T>> created(T data, String message, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<T>builder().code(201).message(message)
                .timestamp(Instant.now().toString()).path(req.getRequestURI()).result(data).build());
    }
}
