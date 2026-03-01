package org.example.hotel_managegmet.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
@Table(name = "services")
@Data
@Entity
public class HotelService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "nvarchar(255)")
    private String name;
    @Column(columnDefinition = "nvarchar(255)")
    private String description;
    private Double price;
    @Column(columnDefinition = "nvarchar(255)")
    private String category;
    private Boolean isActive;

    @OneToMany(mappedBy = "service")
    private Set<ServiceRequest> serviceRequests;
}
