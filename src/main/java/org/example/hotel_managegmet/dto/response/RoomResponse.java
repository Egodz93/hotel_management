package org.example.hotel_managegmet.dto.response;
import lombok.Data;
import org.example.hotel_managegmet.entity.enums.RoomStatus;
@Data
public class RoomResponse {
    private Long id;
    private Integer roomNumber;
    private Integer floor;
    private RoomStatus status;
    private String statusDisplay;
    private String viewType;
    private Boolean isSmoking;
    private Long roomTypeId;
    private String roomTypeName;
    private Double basePrice;
    private Integer maxOccupancy;
}
