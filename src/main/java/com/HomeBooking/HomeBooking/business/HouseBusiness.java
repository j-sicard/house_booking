package com.HomeBooking.HomeBooking.business;

import com.HomeBooking.HomeBooking.BO.HouseBO;
import com.HomeBooking.HomeBooking.FO.HouseFO;
import com.HomeBooking.HomeBooking.model.HouseMO;

import java.util.List;

public interface HouseBusiness {

    public HouseBO createHouse(HouseBO houseBO);

    public List<HouseBO> findHouses() ;

    public HouseBO findHouseById(String id);

    public void deleteHouseById(String id);

    public void updateHouse(HouseFO houseFO);
}
