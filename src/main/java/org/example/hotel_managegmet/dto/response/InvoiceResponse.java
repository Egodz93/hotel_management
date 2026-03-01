package org.example.hotel_managegmet.dto.response;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class InvoiceResponse {
    private Long id;
    private String invoiceNumber;
    private String confirmationNumber;
    private String guestFullName;
    private Double roomCharges;
    private Double serviceCharges;
    private Double serviceChargeFee;
    private Double subTotal;
    private Double taxAmount;
    private Double totalAmount;
    private Double amountPaid;
    private Double amountDue;
    private LocalDateTime issuedAt;
    private List<InvoiceItemResponse> items;
}
