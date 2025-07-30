package com.HomeBooking.HomeBooking.business;

import com.HomeBooking.HomeBooking.BO.HouseBO;

import java.util.List;

public interface HouseBusiness {

    public HouseBO createHouse(HouseBO houseBO);

    public List<HouseBO> findHouses
            ();

    public HouseBO findHouseById(String id);

    public void deleteHouseById(String id);
}
