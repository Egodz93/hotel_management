package org.example.hotel_managegmet.repository;

import org.example.hotel_managegmet.entity.ReservationRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRoomRepository extends JpaRepository<ReservationRoom, Long> {
}
