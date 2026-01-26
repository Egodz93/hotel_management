package org.example.hotel_managegmet.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "guests")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="guest_id")
    private Long Id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer phone;
    private String address;
    private Long idNumber;
    private String loyaltyPoints;

    @OneToMany(mappedBy = "guest")
    private List<Reservation> reservations;

}
