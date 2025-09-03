package com.HomeBooking.HomeBooking.utils;

import com.HomeBooking.HomeBooking.BO.HouseBO;
import com.HomeBooking.HomeBooking.FO.HouseFO;

public class HouseFormMapper {
    public static HouseBO toBusiness(HouseFO form) {
        HouseBO house = new HouseBO();
        house.setAddress(form.getAddress());
        house.setTitle(form.getTitle());
        house.setPrice(form.getPrice());
        return house;
    }

    public static HouseFO toForm(HouseBO house) {
        HouseFO form = new HouseFO();
        form.setAddress(house.getAddress());
        form.setTitle(house.getTitle());
        form.setPrice(house.getPrice());
        return form;
    }
}
