package org.example.hotel_managegmet.dto.request;

import lombok.Data;
import java.util.Set;

@Data
public class RoomTypeRequest {
    String name;
    String description;
    Double basePrice;
    Integer maxOccupancy;
    String bedType;
    Set<Long> amenityIds;
}
