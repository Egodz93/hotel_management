package org.example.hotel_managegmet.dto.request;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.Set;
@Data
public class RoomTypeRequest {
    @NotBlank(message = "Tên loại phòng không được để trống")
    private String name;
    private String description;
    @NotNull(message = "Giá cơ bản không được để trống")
    @Min(value = 0, message = "Giá phải >= 0")
    private Double basePrice;
    @NotNull(message = "Sức chứa tối đa không được để trống")
    @Min(value = 1)
    private Integer maxOccupancy;
    private String bedType;
    private Set<Long> amenityIds;
}
