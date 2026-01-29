package org.example.hotel_managegmet.controller;

import jakarta.servlet.http.HttpServletRequest;
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
            @RequestBody ReservationRequest request,
            HttpServletRequest httpRequest) {

        ReservationResponse result = reservationService.createReservation(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<ReservationResponse>builder()
                        .code(HttpStatus.CREATED.value())
                        .message("Tạo đơn đặt phòng thành công")
                        .timestamp(Instant.now().toString())
                        .path(httpRequest.getRequestURI())
                        .result(result)
                        .build()
        );
    }


    @PatchMapping("/{id}/check-in")
    public ResponseEntity<ApiResponse<Void>> checkIn(
            @PathVariable Long id,
            HttpServletRequest httpRequest) {

        reservationService.checkIn(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .code(HttpStatus.OK.value())
                        .message("Thực hiện Check-in thành công. Phòng đã chuyển sang trạng thái OCCUPIED")
                        .timestamp(Instant.now().toString())
                        .path(httpRequest.getRequestURI())
                        .build()
        );
    }

    @PatchMapping("/{id}/check-out")
    public ResponseEntity<ApiResponse<Void>> checkOut(
            @PathVariable Long id,
            HttpServletRequest httpRequest) {

        reservationService.checkOut(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .code(HttpStatus.OK.value())
                        .message("Thực hiện Check-out thành công. Phòng đã chuyển sang trạng thái CLEANING")
                        .timestamp(Instant.now().toString())
                        .path(httpRequest.getRequestURI())
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> cancelReservation(
            @PathVariable Long id,
            HttpServletRequest httpRequest) {

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
