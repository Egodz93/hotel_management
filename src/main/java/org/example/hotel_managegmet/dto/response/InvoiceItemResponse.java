package org.example.hotel_managegmet.dto.response;
import lombok.Data;
@Data
public class InvoiceItemResponse {
    private String type;
    private String description;
    private Integer quantity;
    private Double unitPrice;
    private Double total;
}
