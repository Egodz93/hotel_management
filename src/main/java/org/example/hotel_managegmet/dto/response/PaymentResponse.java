package org.example.hotel_managegmet.dto.response;
import lombok.Data;
import org.example.hotel_managegmet.entity.enums.PaymentMethod;
import java.time.LocalDateTime;
@Data
public class PaymentResponse {
    private Long id;
    private Double amount;
    private PaymentMethod paymentMethod;
    private String paymentMethodDisplay;
    private String transactionId;
    private LocalDateTime paidAt;
    private String note;
}
