package org.billy.resortmanagementsystem.services;//package miu.edu.resort.services;
//
//import miu.edu.resort.domain.Item;
//import miu.edu.resort.domain.Reservation;
//import miu.edu.resort.dto.ItemDto;

import org.billy.resortmanagementsystem.domain.Item;
import org.billy.resortmanagementsystem.domain.Reservation;
import org.billy.resortmanagementsystem.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto register(ItemDto itemDto, Reservation reservation) throws Exception;
    List<ItemDto> getByReservationId(Integer reservationId);
    ItemDto updateItem(Integer id, ItemDto itemDto) throws Exception;
    void deleteById(Integer id);
    Item findEntityById(Integer itemId);
    void deleteItemsByReservationId(Integer reservationId);
}
