package org.example.hotel_managegmet.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.hotel_managegmet.entity.enums.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Data
@Entity
@Table(name ="reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String confirmationNumber;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;

    @OneToMany(mappedBy = "reservation")
    private List<ReservationRoom> reservationRooms;


}
