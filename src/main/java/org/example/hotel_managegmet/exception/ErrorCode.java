package org.example.hotel_managegmet.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // Guest
    GUEST_NOT_FOUND("GUEST_001", "Không tìm thấy khách hàng với ID: %s", HttpStatus.NOT_FOUND),
    EMAIL_ALREADY_EXISTS("GUEST_002", "Email '%s' đã tồn tại trong hệ thống", HttpStatus.CONFLICT),
    PHONE_ALREADY_EXISTS("GUEST_003", "Số điện thoại '%s' đã tồn tại trong hệ thống", HttpStatus.CONFLICT),
    ID_NUMBER_ALREADY_EXISTS("GUEST_004", "Số CMND/CCCD '%s' đã tồn tại trong hệ thống", HttpStatus.CONFLICT),
    GUEST_UNDERAGE("GUEST_005", "Khách hàng phải đủ 18 tuổi trở lên để đặt phòng", HttpStatus.BAD_REQUEST),

    // Room
    ROOM_NOT_FOUND("ROOM_001", "Không tìm thấy phòng với ID: %s", HttpStatus.NOT_FOUND),
    ROOM_TYPE_NOT_FOUND("ROOM_002", "Loại phòng không tồn tại", HttpStatus.NOT_FOUND),
    ROOM_NOT_AVAILABLE("ROOM_003", "Phòng %s đã có lịch đặt trong khoảng thời gian này", HttpStatus.CONFLICT),
    AMENITY_NOT_FOUND("ROOM_004", "Tiện nghi không tồn tại", HttpStatus.NOT_FOUND),
    INVALID_DATE_RANGE("ROOM_005", "Ngày check-out phải sau ngày check-in", HttpStatus.BAD_REQUEST),
    ROOM_NUMBER_EXISTED("ROOM_006", "Số phòng %s đã tồn tại trong hệ thống", HttpStatus.CONFLICT),
    INVALID_DATE_FORMAT("ROOM_007", "Định dạng ngày không hợp lệ (Chuẩn: YYYY-MM-DD)", HttpStatus.BAD_REQUEST),

    // Reservation
    RESERVATION_NOT_FOUND("RES_001", "Không tìm thấy đơn đặt phòng với ID: %s", HttpStatus.NOT_FOUND),
    STAY_TOO_LONG("RES_002", "Thời gian lưu trú không được vượt quá 30 đêm", HttpStatus.BAD_REQUEST),
    CANNOT_MODIFY_RESERVATION("RES_003", "Chỉ có thể chỉnh sửa đơn ở trạng thái PENDING hoặc CONFIRMED", HttpStatus.BAD_REQUEST),
    CANNOT_CANCEL_RESERVATION("RES_004", "Không thể hủy đơn khi khách đang ở hoặc đã trả phòng", HttpStatus.BAD_REQUEST),
    CANNOT_CHECKIN("RES_005", "Chỉ có thể check-in khi đơn ở trạng thái PENDING hoặc CONFIRMED", HttpStatus.BAD_REQUEST),
    CANNOT_CHECKOUT("RES_006", "Khách hàng chưa check-in, không thể check-out", HttpStatus.BAD_REQUEST),
    CHECKIN_TOO_EARLY("RES_007", "Chưa đến giờ nhận phòng (check-in time: %s)", HttpStatus.BAD_REQUEST),
    CHECKIN_DATE_PAST("RES_008", "Ngày check-in không được là ngày trong quá khứ", HttpStatus.BAD_REQUEST),

    // Invoice & Payment
    INVOICE_NOT_FOUND("INV_001", "Không tìm thấy hóa đơn với ID: %s", HttpStatus.NOT_FOUND),
    INVOICE_ALREADY_EXISTS("INV_002", "Đơn đặt phòng này đã có hóa đơn (Invoice #%s)", HttpStatus.CONFLICT),
    PAYMENT_EXCEEDS_TOTAL("PAY_001", "Số tiền thanh toán vượt quá tổng hóa đơn", HttpStatus.BAD_REQUEST),

    // Service
    SERVICE_NOT_FOUND("SVC_001", "Không tìm thấy dịch vụ với ID: %s", HttpStatus.NOT_FOUND),
    SERVICE_INACTIVE("SVC_002", "Dịch vụ này hiện không hoạt động", HttpStatus.BAD_REQUEST),
    SERVICE_REQUEST_NOT_FOUND("SVC_003", "Không tìm thấy yêu cầu dịch vụ với ID: %s", HttpStatus.NOT_FOUND),

    // System
    INTERNAL_ERROR("SYS_001", "Lỗi hệ thống: %s", HttpStatus.INTERNAL_SERVER_ERROR),
    SOME_ROOMS_NOT_FOUND("SYS_002", "Một số ID phòng không tồn tại trong hệ thống", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
