package org.example.hotel_managegmet.repository;
import org.example.hotel_managegmet.entity.ReservationRoom;
import org.example.hotel_managegmet.entity.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
@Repository
public interface ReservationRoomRepository extends JpaRepository<ReservationRoom, Long> {
    @Query("SELECT CASE WHEN COUNT(rr) > 0 THEN true ELSE false END " +
            "FROM ReservationRoom rr JOIN rr.reservation r " +
            "WHERE rr.room.id = :roomId " +
            "AND r.status NOT IN (:cancelledStatus, :checkedOutStatus) " +
            "AND (:checkIn < r.checkOutDate AND :checkOut > r.checkInDate)")
    boolean existsByRoomIdAndDateRange(
            @Param("roomId") Long roomId,
            @Param("checkIn") LocalDateTime checkIn,
            @Param("checkOut") LocalDateTime checkOut,
            @Param("cancelledStatus") ReservationStatus cancelledStatus,
            @Param("checkedOutStatus") ReservationStatus checkedOutStatus);
}
