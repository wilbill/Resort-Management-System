package org.billy.resortmanagementsystem.controller;

import lombok.AllArgsConstructor;

import org.billy.resortmanagementsystem.domain.Item;
import org.billy.resortmanagementsystem.domain.Reservation;
import org.billy.resortmanagementsystem.dto.ItemDto;
import org.billy.resortmanagementsystem.dto.ReservationDto;
import org.billy.resortmanagementsystem.dto.ReservationDtoForcreate;
import org.billy.resortmanagementsystem.services.ItemService;
import org.billy.resortmanagementsystem.services.ReservationService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final ItemService itemService;
    private final ModelMapper modelMapper;

    @PostMapping
    @Secured("CUSTOMER")
    public ResponseEntity<ReservationDtoForcreate> createReservation(
            @RequestBody ReservationDtoForcreate reservationDto) {
        ReservationDtoForcreate createdReservation = reservationService.create(reservationDto);
        return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
    }

    @PutMapping("/{reservationId}")
    public ResponseEntity<ReservationDto> updateReservation(@PathVariable Integer reservationId,
            @RequestBody ReservationDto reservationDto) {
        ReservationDto updatedReservation = reservationService.update(reservationId, reservationDto);
        return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        List<ReservationDto> reservations = reservationService.getAll();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> getReservationById(@PathVariable Integer id) {
        ReservationDto reservationDto = reservationService.getById(id);
        return new ResponseEntity<>(reservationDto, HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ReservationDto>> getReservationsByCustomer(@PathVariable Integer customerId) {
        List<ReservationDto> reservationList = reservationService.getByCustomerId(customerId);
        return new ResponseEntity<>(reservationList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Integer id) {
        try {
            reservationService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PostMapping("/{reservationId}/items")
    public ResponseEntity<ItemDto> addItemToReservation(@PathVariable Integer reservationId,
                                                        @RequestBody ItemDto itemDto) throws Exception {
        // Find the reservation
        Reservation reservation = reservationService.findEntityById(reservationId);
        if (reservation == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found with ID: " + reservationId);
        }

        // itemDto.setOrder(reservation);

        ItemDto createdItem = itemService.register(itemDto, reservation);

        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @PutMapping("/{reservationId}/items/{itemId}")
    public ResponseEntity<ItemDto> updateItem(@PathVariable Integer reservationId, @PathVariable Integer itemId,
            @RequestBody ItemDto itemDto) throws Exception {
        Item existingItem = itemService.findEntityById(itemId);
        if (existingItem == null || existingItem.getOrder() == null
                || !existingItem.getOrder().getId().equals(reservationId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ItemDto updatedItem = itemService.updateItem(itemId, itemDto);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    @GetMapping("/{reservationId}/items")
    public ResponseEntity<List<ItemDto>> getAllItems(@PathVariable Integer reservationId) {
        List<ItemDto> items = itemService.getByReservationId(reservationId);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/{reservationId}/items/{itemId}")
    public ResponseEntity<ItemDto> getItemById(@PathVariable Integer reservationId, @PathVariable Integer itemId) {
        Item item = itemService.findEntityById(itemId);
        if (item == null || item.getOrder() == null || !item.getOrder().getId().equals(reservationId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ItemDto itemDto = modelMapper.map(item, ItemDto.class);
        return new ResponseEntity<>(itemDto, HttpStatus.OK);
    }

    @DeleteMapping("/{reservationId}/items/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Integer reservationId, @PathVariable Integer itemId) {
        Item item = itemService.findEntityById(itemId);
        if (item == null || item.getOrder() == null || !item.getOrder().getId().equals(reservationId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        itemService.deleteById(itemId);
        reservationService.deleteReservationIfEmpty(reservationId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
