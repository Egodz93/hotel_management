package org.example.hotel_managegmet.dto.request;
import jakarta.validation.constraints.*;
import lombok.Data;
@Data
public class ServiceRequest {
    @NotBlank(message = "Tên dịch vụ không được để trống")
    private String name;
    private String description;
    @NotNull(message = "Giá dịch vụ không được để trống")
    @Min(value = 0)
    private Double price;
    private String category;
    private Boolean isActive = true;
}
