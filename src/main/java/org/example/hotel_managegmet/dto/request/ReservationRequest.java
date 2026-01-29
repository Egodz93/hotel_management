package org.example.hotel_managegmet.dto.request;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReservationRequest {
    private Long guestId;
    private List<Long> roomIds;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
}
