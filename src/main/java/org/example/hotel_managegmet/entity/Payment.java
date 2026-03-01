package org.example.hotel_managegmet.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.hotel_managegmet.entity.enums.PaymentMethod;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Column(length = 100)
    private String transactionId;

    private LocalDateTime paidAt;

    @Column(columnDefinition = "nvarchar(255)")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;
}
