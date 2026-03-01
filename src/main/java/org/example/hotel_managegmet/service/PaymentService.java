package org.example.hotel_managegmet.service;

import lombok.RequiredArgsConstructor;
import org.example.hotel_managegmet.dto.request.PaymentRequest;
import org.example.hotel_managegmet.dto.response.PaymentResponse;
import org.example.hotel_managegmet.entity.Invoice;
import org.example.hotel_managegmet.entity.Payment;
import org.example.hotel_managegmet.exception.AppException;
import org.example.hotel_managegmet.exception.ErrorCode;
import org.example.hotel_managegmet.repository.InvoiceRepository;
import org.example.hotel_managegmet.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;

    @Transactional
    public PaymentResponse processPayment(PaymentRequest request) {
        Invoice invoice = invoiceRepository.findById(request.getInvoiceId())
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND, request.getInvoiceId()));

        Double alreadyPaid = paymentRepository.sumAmountByInvoiceId(invoice.getId());
        double remaining = invoice.getTotalAmount() - (alreadyPaid != null ? alreadyPaid : 0.0);
        if (request.getAmount() > remaining + 0.01)
            throw new AppException(ErrorCode.PAYMENT_EXCEEDS_TOTAL);

        Payment payment = new Payment();
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setTransactionId(request.getTransactionId());
        payment.setNote(request.getNote());
        payment.setPaidAt(LocalDateTime.now());
        payment.setInvoice(invoice);

        Payment saved = paymentRepository.save(payment);
        return toResponse(saved);
    }

    public List<PaymentResponse> getPaymentsByInvoice(Long invoiceId) {
        return paymentRepository.findByInvoiceId(invoiceId).stream().map(this::toResponse).toList();
    }

    private PaymentResponse toResponse(Payment p) {
        PaymentResponse r = new PaymentResponse();
        r.setId(p.getId());
        r.setAmount(p.getAmount());
        r.setPaymentMethod(p.getPaymentMethod());
        r.setPaymentMethodDisplay(p.getPaymentMethod().getDisplayName());
        r.setTransactionId(p.getTransactionId());
        r.setPaidAt(p.getPaidAt());
        r.setNote(p.getNote());
        return r;
    }
}
