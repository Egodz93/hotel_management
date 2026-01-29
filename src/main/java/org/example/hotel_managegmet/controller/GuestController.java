package org.example.hotel_managegmet.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.hotel_managegmet.dto.request.GuestRequest;
import org.example.hotel_managegmet.dto.response.ApiResponse;
import org.example.hotel_managegmet.dto.response.GuestResponse;
import org.example.hotel_managegmet.service.GuestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/guests")
@RequiredArgsConstructor
public class GuestController {
    private final GuestService guestService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<GuestResponse>>> getAllGuests(HttpServletRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<List<GuestResponse>>builder()
                        .code(HttpStatus.OK.value())
                        .message("Lấy danh sách khách hàng thành công")
                        .timestamp(Instant.now().toString())
                        .path(request.getRequestURI())
                        .result(guestService.getAllGuests())
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GuestResponse>> createGuest(
            @RequestBody GuestRequest guestRequest,
            HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<GuestResponse>builder()
                        .code(HttpStatus.CREATED.value())
                        .message("Tạo mới khách hàng thành công")
                        .timestamp(Instant.now().toString())
                        .path(request.getRequestURI())
                        .result(guestService.createGuest(guestRequest))
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GuestResponse>> updateGuest(
            @PathVariable Long id,
            @RequestBody GuestRequest guestRequest,
            HttpServletRequest request) {
        return ResponseEntity.ok(
                ApiResponse.<GuestResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message("Cập nhật thông tin khách hàng thành công")
                        .timestamp(Instant.now().toString())
                        .path(request.getRequestURI())
                        .result(guestService.updateGuest(id, guestRequest))
                        .build()
        );
    }

    @PatchMapping("/{id}/loyalty")
    public ResponseEntity<ApiResponse<Void>> addPoints(
            @PathVariable Long id,
            @RequestParam Integer points,
            HttpServletRequest request) {
        guestService.addLoyaltyPoints(id, points);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .code(HttpStatus.OK.value())
                        .message("Cộng điểm tích lũy thành công")
                        .timestamp(Instant.now().toString())
                        .path(request.getRequestURI())
                        .build()
        );
    }
}
