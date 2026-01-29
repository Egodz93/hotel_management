package org.example.hotel_managegmet.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter

@Entity
@Table(name = "guests")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private Long idNumber;
    private Integer loyaltyPoints;

    @OneToMany(mappedBy = "guest")
    private List<Reservation> reservations;

}
