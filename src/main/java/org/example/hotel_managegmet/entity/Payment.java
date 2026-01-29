package org.example.hotel_managegmet.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
@Table(name ="payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private String paymentMethod;
    private String transactionId;
    private LocalDate paidAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="invoice_id")
    private Invoice invoice;
}
