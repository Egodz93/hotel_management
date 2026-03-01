package org.example.hotel_managegmet.dto.request;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.hotel_managegmet.entity.enums.RoomStatus;
@Data
public class UpdateRoomStatusRequest {
    @NotNull(message = "Trạng thái phòng không được để trống")
    private RoomStatus status;
}
