package org.billy.resortmanagementsystem.services;//package miu.edu.resort.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
//import miu.edu.resort.domain.Item;
//import miu.edu.resort.domain.Product;
//import miu.edu.resort.domain.Reservation;
//import miu.edu.resort.dto.ItemDto;
//import miu.edu.resort.repositories.ItemRepository;
//import miu.edu.resort.repositories.ProductRepository;
//import miu.edu.resort.repositories.ReservationRepository;
import org.billy.resortmanagementsystem.domain.Item;
import org.billy.resortmanagementsystem.domain.Product;
import org.billy.resortmanagementsystem.domain.Reservation;
import org.billy.resortmanagementsystem.dto.ItemDto;
import org.billy.resortmanagementsystem.repositories.ItemRepository;
import org.billy.resortmanagementsystem.repositories.ProductRepository;
import org.billy.resortmanagementsystem.repositories.ReservationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ReservationRepository reservationRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public ItemDto register(ItemDto itemDto, Reservation reservation) throws Exception {
        LocalDate checkInDate = itemDto.getCheckinDate();
        LocalDate checkOutDate = itemDto.getCheckoutDate();

        if (checkOutDate.isBefore(checkInDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Check-in date " + checkInDate + " cannot be later than check-out date " + checkOutDate);
        }

        if (checkInDate.isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Check-in can only occur from the current date onwards");
        }

        Product product = productRepository.findById(itemDto.getProduct().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with ID: " + itemDto.getProduct().getId()));

        if (!isRoomAvailable(checkInDate, checkOutDate, product)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product " + product.getName() + " is not available for the selected dates");
        }

        Item item = modelMapper.map(itemDto, Item.class);
        item.setOrder(reservation); // Set the reservation to the Item

        Item savedItem = itemRepository.save(item);
        return modelMapper.map(savedItem, ItemDto.class);
    }

    private boolean isRoomAvailable(LocalDate checkInDate, LocalDate checkOutDate, Product product) {
        List<Item> existingBookings = itemRepository.findByProduct(product);

        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        checkInDate.isEqual(existingBooking.getCheckinDate())
                                ||
                                checkOutDate.isBefore(existingBooking.getCheckoutDate())
                                ||
                                (checkInDate.isAfter(existingBooking.getCheckinDate())
                                        && checkInDate.isBefore(existingBooking.getCheckoutDate()))
                                ||
                                (checkInDate.isBefore(existingBooking.getCheckinDate())
                                        && checkOutDate.isEqual(existingBooking.getCheckoutDate()))
                                ||
                                (checkInDate.isBefore(existingBooking.getCheckinDate())
                                        && checkOutDate.isAfter(existingBooking.getCheckoutDate()))
                                ||
                                (checkInDate.isEqual(existingBooking.getCheckoutDate())
                                        && checkOutDate.isEqual(existingBooking.getCheckinDate()))
                                ||
                                (checkInDate.isEqual(existingBooking.getCheckoutDate())
                                        && checkOutDate.isEqual(checkInDate))
                );
    }

    @Override
    public List<ItemDto> getByReservationId(Integer reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found with id: " + reservationId)
        );
        List<Item> itemList = itemRepository.findByOrder(reservation);
        List<ItemDto> itemDtoList = new ArrayList<>();
        for (Item item : itemList) {
            ItemDto it = modelMapper.map(item, ItemDto.class);
            itemDtoList.add(it);
        }
        return itemDtoList;
    }

    @Override
    public ItemDto updateItem(Integer id, ItemDto itemDto) throws Exception {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NoResourceFoundException(HttpMethod.PUT, "Item not found with ID: " + id));

        LocalDate checkInDate = itemDto.getCheckinDate();
        LocalDate checkOutDate = itemDto.getCheckoutDate();

        int result = checkInDate.compareTo(checkOutDate);
        if (result > 0) {
            throw new Exception("Check in: " + checkInDate + " cannot be later than check out: " + checkOutDate);
        } else if (checkInDate.compareTo(LocalDate.now()) < 0) {
            throw new Exception("Check-in can only occur from the current date onwards");
        }

        Product product = productRepository.findById(itemDto.getProduct().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with ID: " + itemDto.getProduct().getId()));

        if (!isRoomAvailable(checkInDate, checkOutDate, product)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product " + product.getName() + " is not available for the selected dates");
        }

        item.setOrder(itemDto.getOrder());
        item.setOccupants(itemDto.getOccupants());
        item.setCheckinDate(itemDto.getCheckinDate());
        item.setCheckoutDate(itemDto.getCheckoutDate());
        item.setProduct(itemDto.getProduct());

        ItemDto res = modelMapper.map(itemRepository.save(item), ItemDto.class);
        return res;
    }

    @Override
    public void deleteById(Integer itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with ID: " + itemId));

        Reservation reservation = item.getOrder();
        if (reservation != null) {
            reservation.getItems().remove(item);
            reservationRepository.save(reservation);
        }
        itemRepository.delete(item);
    }

    @Override
    public void deleteItemsByReservationId(Integer reservationId) {
        if (!reservationRepository.existsById(reservationId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found with ID: " + reservationId);
        }
        List<Item> items = itemRepository.findByOrderId(reservationId);

        // Remove the reservation reference from each item
        for (Item item : items) {
            item.setOrder(null);
            itemRepository.save(item);
        }

        itemRepository.deleteAll(items);
        reservationRepository.deleteById(reservationId);
    }

    public Item findEntityById(Integer itemId) {

        return itemRepository.findById(itemId).orElse(null);
    }

}