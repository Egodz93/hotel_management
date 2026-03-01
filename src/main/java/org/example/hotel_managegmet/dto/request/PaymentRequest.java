package org.example.hotel_managegmet.dto.request;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.example.hotel_managegmet.entity.enums.PaymentMethod;
@Data
public class PaymentRequest {
    @NotNull(message = "ID hóa đơn không được để trống")
    private Long invoiceId;

    @NotNull(message = "Số tiền không được để trống")
    @Min(value = 1, message = "Số tiền phải > 0")
    private Double amount;

    @NotNull(message = "Phương thức thanh toán không được để trống")
    private PaymentMethod paymentMethod;

    private String transactionId;
    private String note;
}
