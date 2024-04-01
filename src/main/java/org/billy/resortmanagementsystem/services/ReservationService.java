package org.billy.resortmanagementsystem.services;//package miu.edu.resort.services;
//
//import miu.edu.resort.domain.Reservation;
//import miu.edu.resort.dto.ReservationDto;
//import miu.edu.resort.dto.ReservationDtoForcreate;

import org.billy.resortmanagementsystem.domain.Reservation;
import org.billy.resortmanagementsystem.dto.ReservationDto;
import org.billy.resortmanagementsystem.dto.ReservationDtoForcreate;

import java.util.List;

public interface ReservationService {
    ReservationDtoForcreate create(ReservationDtoForcreate revDto);
    List<ReservationDto> getAll();
    ReservationDto getById(Integer id);
    List<ReservationDto> getByCustomerId(Integer customerId);
    void deleteById(Integer id);
    Reservation findEntityById(Integer reservationId);
    ReservationDto update(Integer reservationId, ReservationDto reservationDto);
    void deleteReservationIfEmpty(Integer reservationId);
}
