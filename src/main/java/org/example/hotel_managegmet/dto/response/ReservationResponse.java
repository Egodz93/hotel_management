package org.example.hotel_managegmet.dto.response;

import lombok.Data;
import org.example.hotel_managegmet.entity.enums.ReservationStatus;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReservationResponse {
    private Long id;
    private String confirmationNumber;
    private String guestFullName;
    private List<String> roomNumbers;;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private ReservationStatus status;
}
