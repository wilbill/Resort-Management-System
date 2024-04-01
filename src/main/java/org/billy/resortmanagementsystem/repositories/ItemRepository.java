package org.billy.resortmanagementsystem.repositories;

import org.billy.resortmanagementsystem.domain.Item;
import org.billy.resortmanagementsystem.domain.Product;
import org.billy.resortmanagementsystem.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    @Query("SELECT i FROM Item i WHERE i.checkinDate <= :checkinDate OR i.checkoutDate > :checkoutDate AND i.product = :product ORDER BY i.checkinDate")
    List<Item> findByCheckinDateRangeAndProduct(LocalDate checkinDate, LocalDate checkoutDate, Product product);
    List<Item> findByOrder(Reservation order);
    void deleteByOrderId(Integer reservationId);
    List<Item> findByOrderId(Integer reservationId);
    long countByOrderId(Integer reservationId);
    List<Item> findByProduct(Product product);

}


