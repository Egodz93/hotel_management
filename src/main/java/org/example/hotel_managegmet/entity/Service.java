package org.example.hotel_managegmet.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
@Data
@Entity
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;
    private String name;
    private String description;
    private Double price;
    private String category;
    private Boolean isActive;

    @OneToMany(mappedBy = "service")
    private Set<ServiceRequest> serviceRequests;
}
