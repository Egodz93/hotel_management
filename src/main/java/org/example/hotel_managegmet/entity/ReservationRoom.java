package org.example.hotel_managegmet.entity;

import jakarta.persistence.*;
@Entity
public class ReservationRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double pricePerNight;
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

}
