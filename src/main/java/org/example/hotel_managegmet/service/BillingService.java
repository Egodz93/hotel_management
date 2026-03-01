package org.example.hotel_managegmet.service;

import lombok.RequiredArgsConstructor;
import org.example.hotel_managegmet.dto.response.InvoiceItemResponse;
import org.example.hotel_managegmet.dto.response.InvoiceResponse;
import org.example.hotel_managegmet.entity.*;
import org.example.hotel_managegmet.entity.enums.ServiceRequestStatus;
import org.example.hotel_managegmet.exception.AppException;
import org.example.hotel_managegmet.exception.ErrorCode;
import org.example.hotel_managegmet.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillingService {
    private final InvoiceRepository invoiceRepository;
    private final ReservationRepository reservationRepository;
    private final ServiceRequestRepository serviceRequestRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public InvoiceResponse generateInvoice(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new AppException(ErrorCode.RESERVATION_NOT_FOUND, reservationId));

        invoiceRepository.findByReservationId(reservationId).ifPresent(existing ->
        { throw new AppException(ErrorCode.INVOICE_ALREADY_EXISTS, existing.getInvoiceNumber()); });

        List<InvoiceItemResponse> items = new ArrayList<>();

        double roomCharges = 0;
        for (ReservationRoom rr : reservation.getReservationRooms()) {
            roomCharges += rr.getTotalPrice();
            InvoiceItemResponse item = new InvoiceItemResponse();
            item.setType("ROOM");
            item.setDescription("Phòng " + rr.getRoom().getRoomNumber() + " - " + rr.getRoom().getRoomType().getName());
            item.setQuantity(1);
            item.setUnitPrice(rr.getTotalPrice());
            item.setTotal(rr.getTotalPrice());
            items.add(item);
        }

        double serviceCharges = 0;
        List<org.example.hotel_managegmet.entity.ServiceRequest> requests =
                serviceRequestRepository.findByReservationIdAndStatus(reservationId, ServiceRequestStatus.COMPLETED);
        for (org.example.hotel_managegmet.entity.ServiceRequest req : requests) {
            double cost = req.getService().getPrice() * req.getQuantity();
            serviceCharges += cost;
            InvoiceItemResponse item = new InvoiceItemResponse();
            item.setType("SERVICE");
            item.setDescription(req.getService().getName());
            item.setQuantity(req.getQuantity());
            item.setUnitPrice(req.getService().getPrice());
            item.setTotal(cost);
            items.add(item);
        }

        double serviceChargeFee = roomCharges * 0.05;
        double subTotal = roomCharges + serviceCharges + serviceChargeFee;

        double taxAmount = subTotal * 0.10;
        double totalAmount = subTotal + taxAmount;

        String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String randomPart = java.util.UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        String invoiceNumber = "INV-" + datePart + "-" + randomPart;

        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(invoiceNumber);
        invoice.setRoomCharges(roomCharges);
        invoice.setServiceCharges(serviceCharges);
        invoice.setServiceChargeFee(serviceChargeFee);
        invoice.setSubTotal(subTotal);
        invoice.setTaxAmount(taxAmount);
        invoice.setTotalAmount(totalAmount);
        invoice.setIssuedAt(LocalDateTime.now());
        invoice.setReservation(reservation);
        invoiceRepository.save(invoice);

        InvoiceResponse response = new InvoiceResponse();
        response.setId(invoice.getId());
        response.setInvoiceNumber(invoiceNumber);
        response.setConfirmationNumber(reservation.getConfirmationNumber());
        response.setGuestFullName(reservation.getGuest().getFirstName() + " " + reservation.getGuest().getLastName());
        response.setRoomCharges(roomCharges);
        response.setServiceCharges(serviceCharges);
        response.setServiceChargeFee(serviceChargeFee);
        response.setSubTotal(subTotal);
        response.setTaxAmount(taxAmount);
        response.setTotalAmount(totalAmount);
        response.setAmountPaid(0.0);
        response.setAmountDue(totalAmount);
        response.setIssuedAt(invoice.getIssuedAt());
        response.setItems(items);
        return response;
    }

    public InvoiceResponse getInvoice(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND, invoiceId));
        return buildInvoiceResponse(invoice);
    }

    public InvoiceResponse getInvoiceByReservation(Long reservationId) {
        Invoice invoice = invoiceRepository.findByReservationId(reservationId)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_FOUND, reservationId));
        return buildInvoiceResponse(invoice);
    }

    private InvoiceResponse buildInvoiceResponse(Invoice invoice) {
        Double paid = paymentRepository.sumAmountByInvoiceId(invoice.getId());
        InvoiceResponse r = new InvoiceResponse();
        r.setId(invoice.getId());
        r.setInvoiceNumber(invoice.getInvoiceNumber());
        r.setConfirmationNumber(invoice.getReservation().getConfirmationNumber());
        r.setGuestFullName(invoice.getReservation().getGuest().getFirstName() + " " + invoice.getReservation().getGuest().getLastName());
        r.setRoomCharges(invoice.getRoomCharges());
        r.setServiceCharges(invoice.getServiceCharges());
        r.setServiceChargeFee(invoice.getServiceChargeFee());
        r.setSubTotal(invoice.getSubTotal());
        r.setTaxAmount(invoice.getTaxAmount());
        r.setTotalAmount(invoice.getTotalAmount());
        r.setAmountPaid(paid != null ? paid : 0.0);
        r.setAmountDue(invoice.getTotalAmount() - (paid != null ? paid : 0.0));
        r.setIssuedAt(invoice.getIssuedAt());
        return r;
    }
}
