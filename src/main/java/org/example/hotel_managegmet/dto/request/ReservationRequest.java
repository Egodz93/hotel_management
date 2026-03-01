package org.example.hotel_managegmet.dto.request;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class ReservationRequest {
    @NotNull(message = "ID khách hàng không được để trống")
    private Long guestId;

    @NotEmpty(message = "Phải chọn ít nhất một phòng")
    private List<Long> roomIds;

    @NotNull(message = "Ngày check-in không được để trống")
    @Future(message = "Ngày check-in phải là ngày tương lai")
    private LocalDateTime checkInDate;

    @NotNull(message = "Ngày check-out không được để trống")
    private LocalDateTime checkOutDate;
}
