package org.example.hotel_managegmet.dto.response;
import lombok.Data;
@Data
public class ServiceResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String category;
    private Boolean isActive;
}
