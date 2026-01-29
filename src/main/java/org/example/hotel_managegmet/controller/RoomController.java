package org.example.hotel_managegmet.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.hotel_managegmet.dto.request.AmenityRequest;
import org.example.hotel_managegmet.dto.request.RoomRequest;
import org.example.hotel_managegmet.dto.request.RoomTypeRequest;
import org.example.hotel_managegmet.dto.response.AmenityResponse;
import org.example.hotel_managegmet.dto.response.ApiResponse;
import org.example.hotel_managegmet.dto.response.RoomResponse;
import org.example.hotel_managegmet.dto.response.RoomTypeResponse;
import org.example.hotel_managegmet.entity.enums.RoomStatus;
import org.example.hotel_managegmet.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/amenities")
    public ResponseEntity<ApiResponse<AmenityResponse>> createAmenity(
            @RequestBody AmenityRequest request,
            HttpServletRequest httpRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<AmenityResponse>builder()
                        .code(HttpStatus.CREATED.value())
                        .message("Tạo tiện nghi thành công")
                        .timestamp(Instant.now().toString())
                        .path(httpRequest.getRequestURI())
                        .result(roomService.createAmenity(request))
                        .build()
        );
    }

    @PostMapping("/types")
    public ResponseEntity<ApiResponse<RoomTypeResponse>> createRoomType(
            @RequestBody RoomTypeRequest request,
            HttpServletRequest httpRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<RoomTypeResponse>builder()
                        .code(HttpStatus.CREATED.value())
                        .message("Tạo loại phòng thành công")
                        .timestamp(Instant.now().toString())
                        .path(httpRequest.getRequestURI())
                        .result(roomService.createRoomType(request))
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RoomResponse>> createRoom(
            @RequestBody RoomRequest request,
            HttpServletRequest httpRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<RoomResponse>builder()
                        .code(HttpStatus.CREATED.value())
                        .message("Tạo phòng mới thành công")
                        .timestamp(Instant.now().toString())
                        .path(httpRequest.getRequestURI())
                        .result(roomService.createRoom(request))
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoomResponse>>> getAllRooms(HttpServletRequest httpRequest) {
        return ResponseEntity.ok(
                ApiResponse.<List<RoomResponse>>builder()
                        .code(HttpStatus.OK.value())
                        .message("Lấy danh sách phòng thành công")
                        .timestamp(Instant.now().toString())
                        .path(httpRequest.getRequestURI())
                        .result(roomService.getAllRooms())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoomResponse>> getRoomById(
            @PathVariable Long id,
            HttpServletRequest httpRequest) {
        return ResponseEntity.ok(
                ApiResponse.<RoomResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message("Lấy chi tiết phòng thành công")
                        .timestamp(Instant.now().toString())
                        .path(httpRequest.getRequestURI())
                        .result(roomService.getRoomById(id))
                        .build()
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<RoomResponse>>> getRoomsByStatus(
            @RequestParam RoomStatus status,
            HttpServletRequest httpRequest) {
        return ResponseEntity.ok(
                ApiResponse.<List<RoomResponse>>builder()
                        .code(HttpStatus.OK.value())
                        .message("Lọc danh sách phòng theo trạng thái: " + status)
                        .timestamp(Instant.now().toString())
                        .path(httpRequest.getRequestURI())
                        .result(roomService.getRoomsByStatus(status))
                        .build()
        );
    }
}
