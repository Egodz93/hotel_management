package org.example.hotel_managegmet.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "guests")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "nvarchar(100)", nullable = false)
    private String firstName;

    @Column(columnDefinition = "nvarchar(100)", nullable = false)
    private String lastName;

    @Column(columnDefinition = "nvarchar(255)", unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false, length = 20)
    private String phone;

    @Column(columnDefinition = "nvarchar(255)")
    private String address;

    @Column(unique = true, nullable = false, length = 20)
    private String idNumber;

    private LocalDate dateOfBirth;

    private Integer loyaltyPoints = 0;

    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL)
    private List<Reservation> reservations;
}
