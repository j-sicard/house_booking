package com.HomeBooking.HomeBooking.service;

import com.HomeBooking.HomeBooking.BO.HouseBO;

import java.util.List;
import java.util.Optional;

public interface HouseService {

    public HouseBO create(HouseBO house);

    public List<HouseBO> findHouses();

    public void deleteHouseById(String id);

    public Optional<HouseBO> findHouseById(String id);
}
