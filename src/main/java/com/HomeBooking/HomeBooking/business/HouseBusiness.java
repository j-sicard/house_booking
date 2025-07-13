package com.HomeBooking.HomeBooking.business;

import com.HomeBooking.HomeBooking.BO.HouseBO;

import java.util.List;
import java.util.Optional;

public interface HouseBusiness {

    public List<HouseBO> findHouse();

    public HouseBO findHouseById(String id);
}
