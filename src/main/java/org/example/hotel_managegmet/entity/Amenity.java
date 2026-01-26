package org.example.hotel_managegmet.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Amenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String icon;

    @ManyToMany(mappedBy = "amenities")
    private Set<RoomType> roomTypes;
}
