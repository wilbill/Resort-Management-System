package org.billy.resortmanagementsystem.dto;


import java.util.List;

public class ReservationDtoForcreate {
    private Integer customerId;
    private List<ItemDto> itemDtos;


    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public List<ItemDto> getItemDtos() {
        return itemDtos;
    }

    public void setItemDtos(List<ItemDto> itemDtos) {
        this.itemDtos = itemDtos;
    }
}

