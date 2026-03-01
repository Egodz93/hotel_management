package org.example.hotel_managegmet.repository;
import org.example.hotel_managegmet.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    Optional<Guest> findByEmail(String email);
    Optional<Guest> findByPhone(String phone);
    Optional<Guest> findByIdNumber(String idNumber);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByIdNumber(String idNumber);
}
