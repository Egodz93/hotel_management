package org.example.hotel_managegmet.repository;
import org.example.hotel_managegmet.entity.HotelService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface ServiceRepository extends JpaRepository<HotelService, Long> {
    List<HotelService> findByIsActiveTrue();
    List<HotelService> findByCategory(String category);
}
