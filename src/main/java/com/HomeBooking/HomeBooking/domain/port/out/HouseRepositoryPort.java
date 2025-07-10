package com.HomeBooking.HomeBooking.domain.port.out;

import com.HomeBooking.HomeBooking.domain.model.House;

import java.util.List;
import java.util.Optional;

public interface HouseRepositoryPort {

    public House create(House house);

    public List<House> findHouses();

    public void deleteHouseById(String id);

    public Optional<House> findHouseById(String id);
}
