package org.example.hotel_managegmet.repository;
import org.example.hotel_managegmet.entity.ServiceRequest;
import org.example.hotel_managegmet.entity.enums.ServiceRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
    List<ServiceRequest> findByReservationId(Long reservationId);
    List<ServiceRequest> findByReservationIdAndStatus(Long reservationId, ServiceRequestStatus status);
}
