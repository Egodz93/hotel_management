package org.example.hotel_managegmet.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AmenityRequest {
    @NotBlank(message = "Tên tiện nghi không được để trống")
    private String name;
    private String description;
    private String icon;
}
