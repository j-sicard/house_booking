package com.HomeBooking.HomeBooking.domain.port.out;

import com.HomeBooking.HomeBooking.domain.model.House;

import java.util.List;
import java.util.Optional;

public interface HouseRepositoryPort {

    public House create(House house) throws Exception;

    public List<House> findHouses() throws Exception;

    public void deleteHouseById(Long id) throws Exception;

    public Optional<House> findHouseById(Long id) throws Exception;
}
