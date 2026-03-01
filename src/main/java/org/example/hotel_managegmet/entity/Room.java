package org.example.hotel_managegmet.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.hotel_managegmet.entity.enums.RoomStatus;

@Data
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Integer roomNumber;

    @Column(nullable = false)
    private Integer floor;

    private RoomStatus status = RoomStatus.AVAILABLE;

    @Column(columnDefinition = "nvarchar(100)")
    private String viewType;

    private Boolean isSmoking = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_type_id", nullable = false)
    private RoomType roomType;
}
