package org.example.hotel_managegmet.dto.request;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.example.hotel_managegmet.entity.enums.RoomStatus;
@Data
public class RoomRequest {
    @NotNull(message = "Số phòng không được để trống")
    @Min(value = 1, message = "Số phòng phải >= 1")
    private Integer roomNumber;

    @NotNull(message = "Tầng không được để trống")
    @Min(value = 1)
    private Integer floor;

    private RoomStatus status = RoomStatus.AVAILABLE;
    private String viewType;
    private Boolean isSmoking = false;

    @NotNull(message = "Loại phòng không được để trống")
    private Long roomTypeId;
}
