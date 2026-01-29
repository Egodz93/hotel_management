package org.example.hotel_managegmet.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // Guest related
    GUEST_NOT_FOUND("GUEST_001", "Không tìm thấy khách hàng với ID: %s", HttpStatus.NOT_FOUND),
    EMAIL_ALREADY_EXISTS("GUEST_002", "Email %s đã tồn tại trên hệ thống", HttpStatus.CONFLICT),

    // Room related
    ROOM_NOT_FOUND("ROOM_001", "Không tìm thấy phòng với ID: %s", HttpStatus.NOT_FOUND),
    ROOM_TYPE_NOT_FOUND("ROOM_002", "Loại phòng không tồn tại", HttpStatus.NOT_FOUND),
    ROOM_NOT_AVAILABLE("ROOM_003", "Phòng %s hiện không sẵn sàng để đặt", HttpStatus.BAD_REQUEST),
    AMENITY_NOT_FOUND("ROOM_004", "Tiện nghi không tồn tại", HttpStatus.NOT_FOUND),

    // Reservation related
    RESERVATION_NOT_FOUND("RES_001", "Không tìm thấy đơn đặt phòng với ID: %s", HttpStatus.NOT_FOUND),
    INVALID_CHECKOUT("RES_002", "Ngày trả phòng phải sau ngày nhận phòng", HttpStatus.BAD_REQUEST),

    // System
    INTERNAL_ERROR("SYS_001", "Lỗi máy chủ nội bộ", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
