package org.example.hotel_managegmet.dto.response;

import lombok.Data;
import java.util.Set;

@Data
public class RoomTypeResponse {
    Long id;
    String name;
    String description;
    Double basePrice;
    Integer maxOccupancy;
    String bedType;
    Set<AmenityResponse> amenities;
}
