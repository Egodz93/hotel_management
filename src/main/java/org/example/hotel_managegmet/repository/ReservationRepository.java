package org.example.hotel_managegmet.repository;

import org.example.hotel_managegmet.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByConfirmationNumber(String confirmationNumber);
}
