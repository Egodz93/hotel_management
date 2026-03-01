package org.example.hotel_managegmet.dto.request;
import jakarta.validation.constraints.*;
import lombok.Data;
@Data
public class ServiceRequestRequest {
    @NotNull(message = "ID đặt phòng không được để trống")
    private Long reservationId;

    @NotNull(message = "ID dịch vụ không được để trống")
    private Long serviceId;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải >= 1")
    private Integer quantity;
}
