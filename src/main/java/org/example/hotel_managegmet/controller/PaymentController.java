package org.example.hotel_managegmet.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.hotel_managegmet.dto.request.PaymentRequest;
import org.example.hotel_managegmet.dto.response.ApiResponse;
import org.example.hotel_managegmet.dto.response.PaymentResponse;
import org.example.hotel_managegmet.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> pay(
            @Valid @RequestBody PaymentRequest request,
            HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<PaymentResponse>builder()
                        .code(201)
                        .message("Thanh toán đã được ghi nhận")
                        .timestamp(Instant.now().toString())
                        .path(req.getRequestURI())
                        .result(paymentService.processPayment(request))
                        .build());
    }

    @GetMapping("/invoice/{invoiceId}")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getByInvoice(
            @PathVariable Long invoiceId,
            HttpServletRequest req) {
        return ResponseEntity.ok(
                ApiResponse.<List<PaymentResponse>>builder()
                        .code(200)
                        .message("Lấy danh sách thanh toán thành công")
                        .timestamp(Instant.now().toString())
                        .path(req.getRequestURI())
                        .result(paymentService.getPaymentsByInvoice(invoiceId))
                        .build());
    }
}
