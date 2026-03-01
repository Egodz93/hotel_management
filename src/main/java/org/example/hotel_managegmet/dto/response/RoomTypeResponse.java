package org.example.hotel_managegmet.dto.response;
import lombok.Data;
import java.util.Set;
@Data
public class RoomTypeResponse {
    private Long id;
    private String name;
    private String description;
    private Double basePrice;
    private Integer maxOccupancy;
    private String bedType;
    private Set<AmenityResponse> amenities;
}
