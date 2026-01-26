package org.example.hotel_managegmet.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String confirmationNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;

    @OneToMany(mappedBy = "reservation")
    private List<ReservationRoom> reservationRooms;


}
