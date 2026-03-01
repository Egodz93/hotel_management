package org.example.hotel_managegmet.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
@Data
@Entity
@Table(name = "amenities")
public class Amenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "nvarchar(255)", nullable = false, unique = true)
    private String name;
    @Column(columnDefinition = "nvarchar(255)")
    private String description;

    private String icon;

    @ManyToMany(mappedBy = "amenities")
    private Set<RoomType> roomTypes;
}
