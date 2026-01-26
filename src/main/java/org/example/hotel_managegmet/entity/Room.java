package org.example.hotel_managegmet.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;
    private Integer roomNumber;
    private Integer floor;
    private String viewType;
    private Boolean isSmoking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomtype_id")
    private RoomType roomType;
}
