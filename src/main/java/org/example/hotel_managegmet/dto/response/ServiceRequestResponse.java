package org.example.hotel_managegmet.dto.response;
import lombok.Data;
import org.example.hotel_managegmet.entity.enums.ServiceRequestStatus;
import java.time.LocalDateTime;
@Data
public class ServiceRequestResponse {
    private Long id;
    private Long reservationId;
    private String confirmationNumber;
    private String serviceName;
    private Double servicePrice;
    private Integer quantity;
    private Double totalCost;
    private ServiceRequestStatus status;
    private String statusDisplay;
    private LocalDateTime requestedAt;
    private LocalDateTime completedAt;
}
