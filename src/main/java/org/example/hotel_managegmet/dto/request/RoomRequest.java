package org.example.hotel_managegmet.dto.request;

import lombok.Data;
import org.example.hotel_managegmet.entity.enums.RoomStatus;

@Data
public class RoomRequest {
    String roomNumber;
    Integer floor;
    RoomStatus status;
    String viewType;
    Boolean isSmoking;
    Long roomTypeId;
}
