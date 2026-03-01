package org.example.hotel_managegmet.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.hotel_managegmet.dto.request.ReservationRequest;
import org.example.hotel_managegmet.dto.response.ApiResponse;
import org.example.hotel_managegmet.dto.response.ReservationResponse;
import org.example.hotel_managegmet.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ApiResponse<ReservationResponse>> createReservation(
            @Valid @RequestBody ReservationRequest request,
            HttpServletRequest httpRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<ReservationResponse>builder()
                        .code(HttpStatus.CREATED.value())
                        .message("Tạo đơn đặt phòng thành công")
                        .timestamp(Instant.now().toString())
                           .path(httpRequest.getRequestURI())
                        .result(reservationService.createReservation(request))
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReservationResponse>> getReservation(
            @PathVariable Long id, HttpServletRequest httpRequest) {
        return ResponseEntity.ok(
                ApiResponse.<ReservationResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message("Lấy thông tin đơn đặt phòng thành công")
                        .result(reservationService.getById(id))
                        .build()
        );
    }

    @PatchMapping("/{id}/check-in")
    public ResponseEntity<ApiResponse<Void>> checkIn(
            @PathVariable Long id, HttpServletRequest httpRequest) {
        reservationService.checkIn(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .code(HttpStatus.OK.value())
                        .message("Check-in thành công. Phòng đã chuyển sang OCCUPIED")
                        .timestamp(Instant.now().toString())
                        .path(httpRequest.getRequestURI())
                        .build()
        );
    }

    @PatchMapping("/{id}/check-out")
    public ResponseEntity<ApiResponse<Void>> checkOut(
            @PathVariable Long id, HttpServletRequest httpRequest) {
        reservationService.checkOut(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .code(HttpStatus.OK.value())
                        .message("Check-out thành công. Phòng đã chuyển sang CLEANING")
                        .timestamp(Instant.now().toString())
                        .path(httpRequest.getRequestURI())
                        .build()
        );
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelReservation(
            @PathVariable Long id, HttpServletRequest httpRequest) {
        reservationService.cancelReservation(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .code(HttpStatus.OK.value())
                        .message("Hủy đơn đặt phòng thành công")
                        .timestamp(Instant.now().toString())
                        .path(httpRequest.getRequestURI())
                        .build()
        );
    }
}
