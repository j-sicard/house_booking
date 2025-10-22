package com.HomeBooking.HomeBooking.service;

import com.HomeBooking.HomeBooking.BO.HouseBO;
import com.HomeBooking.HomeBooking.model.HouseMO;

import java.util.List;
import java.util.Optional;

public interface HouseService {

    public HouseBO createHouse(HouseBO house);

    public List<HouseBO> findHouses();

    public void deleteHouseById(String id);

    public Optional<HouseBO> findHouseById(String id);

    public Boolean houseExistedById(String id);

    public void updateHouse(HouseBO originalHouse, HouseBO udateHouse);
}
