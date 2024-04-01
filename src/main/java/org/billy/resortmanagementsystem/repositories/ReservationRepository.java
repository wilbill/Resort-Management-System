package org.billy.resortmanagementsystem.repositories;

import org.billy.resortmanagementsystem.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findAllByCustomerId(Integer id);
}
