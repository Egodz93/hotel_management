package org.example.hotel_managegmet.repository;
import org.example.hotel_managegmet.entity.Room;
import org.example.hotel_managegmet.entity.enums.ReservationStatus;
import org.example.hotel_managegmet.entity.enums.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomNumber(Integer roomNumber);
    List<Room> findByStatus(RoomStatus status);
    List<Room> findByRoomTypeId(Long roomTypeId);

    @Query("SELECT r FROM Room r " +
            "WHERE r.status = :availableStatus " +
            "AND r.id NOT IN (" +
            "  SELECT rr.room.id FROM ReservationRoom rr " +
            "  JOIN rr.reservation res " +
            "  WHERE res.status NOT IN (:cancelledStatus, :checkedOutStatus) " +
            "  AND (res.checkInDate < :checkOut AND res.checkOutDate > :checkIn)" +
            ")")
    List<Room> findAvailableRooms(
            @Param("availableStatus") RoomStatus availableStatus,
            @Param("checkIn") LocalDateTime checkIn,
            @Param("checkOut") LocalDateTime checkOut,
            @Param("cancelledStatus") ReservationStatus cancelledStatus,
            @Param("checkedOutStatus") ReservationStatus checkedOutStatus
    );

    @Query("SELECT r FROM Room r WHERE r.roomType.id = :typeId AND r.roomType.maxOccupancy >= :guests AND r.status = :status")
    List<Room> findByTypeAndCapacity(@Param("typeId") Long typeId, @Param("guests") int guests, @Param("status") RoomStatus status);
}
