package com.HomeBooking.HomeBooking.business.impl;

import com.HomeBooking.HomeBooking.BO.HouseBO;
import com.HomeBooking.HomeBooking.business.HouseBusiness;
import com.HomeBooking.HomeBooking.exceptions.dataexception.HouseNotFoundException;
import com.HomeBooking.HomeBooking.exceptions.dataexception.TechnicalDatabaseException;
import com.HomeBooking.HomeBooking.service.HouseService;
import com.HomeBooking.HomeBooking.utils.HouseMapper;
import com.mongodb.MongoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HouseBusinessImpl implements HouseBusiness {

    private final HouseService houseService;

    public HouseBusinessImpl(HouseService houseService) {
        this.houseService = houseService;     }

    private static final Logger logger = LoggerFactory.getLogger(HouseBusinessImpl.class);

    public List<HouseBO> findHouse(){
        try {
            return  houseService.findHouses().stream()
                    .map(HouseMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (MongoException e) {
            logger.error("Error retrieving houses", e);
            throw new TechnicalDatabaseException("Technical error while retrieving houses", e);
        }
    }

    public HouseBO findHouseById(String id) {
        try {
            return houseService.findHouseById(id)
                    .map(HouseMapper::toDomain)
                    .orElseThrow(() -> new HouseNotFoundException("House not found : " + id));
        } catch (MongoException e) {
            logger.error("Mongo error with id {}", id, e);
            throw new TechnicalDatabaseException("Technical error", e);
        }
    }
}
