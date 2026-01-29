package org.example.hotel_managegmet.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;
@Data
@Entity
@Table(name = "room_types")
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double basePrice;
    private Integer maxOccupancy;
    private String bedType;

    @OneToMany(mappedBy = "roomType")
    private List<Room> rooms;

    @ManyToMany
    @JoinTable(
            name = "room_type_amenity",
            joinColumns = @JoinColumn(name = "room_type_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private Set<Amenity> amenities;
}
