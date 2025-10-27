package com.HomeBooking.HomeBooking.Mapper;

import com.HomeBooking.HomeBooking.BO.HouseBO;

public class HouseUpdate {

    public static HouseBO updateHouseBO(HouseBO existingHouse, HouseBO updatingHouse){

        existingHouse.setTitle(updatingHouse.getTitle());
        existingHouse.setAddress(updatingHouse.getAddress());
        existingHouse.setPrice(updatingHouse.getPrice());

        return existingHouse;
    }
}
