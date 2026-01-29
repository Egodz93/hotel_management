package org.example.hotel_managegmet.dto.response;

import lombok.Data;
import org.example.hotel_managegmet.entity.enums.RoomStatus;

@Data
public class RoomResponse {
    Long id;
    String roomNumber;
    Integer floor;
    RoomStatus status;
    String viewType;
    Boolean isSmoking;
    String roomTypeName;
}
