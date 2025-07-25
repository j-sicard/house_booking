package com.HomeBooking.HomeBooking.service;

import com.HomeBooking.HomeBooking.BO.HouseBO;
import com.HomeBooking.HomeBooking.model.HouseMO;

import java.util.List;
import java.util.Optional;

public interface HouseService {

    public HouseMO create(HouseMO house);

    public List<HouseMO> findHouses();

    public void deleteHouseById(String id);

    public Optional<HouseMO> findHouseById(String id);

    public Boolean houseExistedById(String id);
}
