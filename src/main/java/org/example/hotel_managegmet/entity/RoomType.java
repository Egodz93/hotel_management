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

    @Column(columnDefinition = "nvarchar(100)", nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "nvarchar(500)")
    private String description;

    @Column(nullable = false)
    private Double basePrice;

    @Column(nullable = false)
    private Integer maxOccupancy;

    @Column(columnDefinition = "nvarchar(50)")
    private String bedType;

    @OneToMany(mappedBy = "roomType")
    private List<Room> rooms;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "room_type_amenity",
            joinColumns = @JoinColumn(name = "room_type_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private Set<Amenity> amenities;
}
