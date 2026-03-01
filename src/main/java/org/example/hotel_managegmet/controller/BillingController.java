package org.example.hotel_managegmet.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.hotel_managegmet.dto.response.ApiResponse;
import org.example.hotel_managegmet.dto.response.InvoiceResponse;
import org.example.hotel_managegmet.service.BillingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/billing")
@RequiredArgsConstructor
public class BillingController {
    private final BillingService billingService;

    @PostMapping("/invoice/{reservationId}")
    public ResponseEntity<ApiResponse<InvoiceResponse>> generateInvoice(
            @PathVariable Long reservationId,
            HttpServletRequest req) {
        return ResponseEntity.ok(ApiResponse.<InvoiceResponse>builder().code(200)
                .message("Hóa đơn đã được tạo thành công")
                .timestamp(Instant.now().toString())
                .path(req.getRequestURI())
                .result(billingService.generateInvoice(reservationId))
                .build());
    }

    @GetMapping("/invoice/{invoiceId}")
    public ResponseEntity<ApiResponse<InvoiceResponse>> getInvoice(@PathVariable Long invoiceId, HttpServletRequest req) {
        return ResponseEntity.ok(ApiResponse.<InvoiceResponse>builder().code(200)
                .message("Lấy hóa đơn thành công")
                .timestamp(Instant.now().toString())
                .path(req.getRequestURI())
                .result(billingService.getInvoice(invoiceId))
                .build());
    }

    @GetMapping("/invoice/reservation/{reservationId}")
    public ResponseEntity<ApiResponse<InvoiceResponse>> getInvoiceByReservation(@PathVariable Long reservationId, HttpServletRequest req) {
        return ResponseEntity.ok(ApiResponse.<InvoiceResponse>builder().code(200)
                .message("Lấy hóa đơn theo đơn đặt phòng thành công")
                .timestamp(Instant.now().toString())
                .path(req.getRequestURI())
                .result(billingService.getInvoiceByReservation(reservationId))
                .build());
    }
}
