package org.billy.resortmanagementsystem.services;//package miu.edu.resort.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
//import miu.edu.resort.domain.*;
//import miu.edu.resort.dto.ItemDto;
//import miu.edu.resort.dto.ReservationDto;
//import miu.edu.resort.dto.ReservationDtoForcreate;
//import miu.edu.resort.repositories.ItemRepository;
//import miu.edu.resort.repositories.ReservationRepository;
//import miu.edu.resort.repositories.UserRepository;
//import miu.edu.resort.services.security.CustomUserDetails;

import org.billy.resortmanagementsystem.domain.*;
import org.billy.resortmanagementsystem.dto.ItemDto;
import org.billy.resortmanagementsystem.dto.ReservationDto;
import org.billy.resortmanagementsystem.dto.ReservationDtoForcreate;
import org.billy.resortmanagementsystem.repositories.ItemRepository;
import org.billy.resortmanagementsystem.repositories.ReservationRepository;
import org.billy.resortmanagementsystem.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ModelMapper modelMapper;
    private final ItemService itemService;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public ReservationDtoForcreate create(ReservationDtoForcreate revDto) {
        // Find the existing customer
        Customer customer = userRepository.findById(revDto.getCustomerId())
                .map(user -> (Customer) user)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Customer not found with id: " + revDto.getCustomerId()));

        // Create a new reservation instance
        Reservation reservation = new Reservation();
        reservation.setCustomer(customer);
        reservation.setStatus(Status.PLACED);

        // Process and add items to the reservation
        if (revDto.getItemDtos() != null && !revDto.getItemDtos().isEmpty()) {
            for (ItemDto itemDto : revDto.getItemDtos()) {
                // Map DTO to entity
                Item item = modelMapper.map(itemDto, Item.class);
                // Add item to reservation
                reservation.addItem(item);
            }
        }

        // Save the reservation
        Reservation savedReservation = reservationRepository.save(reservation);

        // Return the mapped DTO
        return modelMapper.map(savedReservation, ReservationDtoForcreate.class);
    }

    @Override
    public Reservation findEntityById(Integer reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation doesn't exist"));
        // checkAuthority(reservation.getCustomer());
        return reservation;
    }

    @Override
    public ReservationDto update(Integer reservationId, ReservationDto reservationDto) {
        Reservation reservation = findEntityById(reservationId);
        if (reservation == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found with id: " + reservationId);
        }

        // checkAuthority(reservation.getCustomer());

        modelMapper.map(reservationDto, reservation);

        Reservation updatedReservation = reservationRepository.save(reservation);

        return modelMapper.map(updatedReservation, ReservationDto.class);
    }

    @Override
    public List<ReservationDto> getAll() {
        List<Reservation> reservationList = reservationRepository.findAll();
        List<ReservationDto> reservationDtoList = new ArrayList<>(reservationList.size());
        reservationList.forEach(
                reservation -> {
                    ReservationDto rd = modelMapper.map(reservation, ReservationDto.class);
                    reservationDtoList.add(rd);
                });
        return reservationDtoList;
    }

    @Override
    public ReservationDto getById(Integer id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Reservation not found with ID: " + id));
        return modelMapper.map(reservation, ReservationDto.class);
    }

    @Override
    @Transactional
    public List<ReservationDto> getByCustomerId(Integer customerId) {
        List<Reservation> reservationList = reservationRepository.findAllByCustomerId(customerId);
        if (reservationList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No reservations found for Customer ID: " + customerId);
        }
        // checkAuthority(reservationList.getFirst().getCustomer());

        return reservationList.stream()
                .map(reservation -> modelMapper.map(reservation, ReservationDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found with ID: " + id);
        }

        itemService.deleteItemsByReservationId(id);

        reservationRepository.deleteById(id);
    }

    public static void checkAuthority(User requriedUser) {
        try {
            User authUser = (User) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            if (!authUser.isAdmin() || !requriedUser.getUserName().equals(authUser.getUserName()))
                throw new AccessDeniedException("User not allowed");
        } catch (NullPointerException e) {
            throw new AccessDeniedException("User not allowed");
        }
    }

    @Transactional
    @Override
    public void deleteReservationIfEmpty(Integer reservationId) {
        long itemCount = itemRepository.countByOrderId(reservationId);
        if (itemCount == 0) {
            reservationRepository.deleteById(reservationId);
        }
    }

}
