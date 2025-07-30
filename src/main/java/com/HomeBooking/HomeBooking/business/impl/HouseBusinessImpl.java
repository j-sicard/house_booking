package com.HomeBooking.HomeBooking.business.impl;

import com.HomeBooking.HomeBooking.BO.HouseBO;
import com.HomeBooking.HomeBooking.business.HouseBusiness;
import com.HomeBooking.HomeBooking.exceptions.HouseNotFoundException;
import com.HomeBooking.HomeBooking.exceptions.TechnicalDatabaseException;
import com.HomeBooking.HomeBooking.service.HouseService;
import com.HomeBooking.HomeBooking.utils.HouseMapper;
import com.HomeBooking.HomeBooking.utils.HouseValidator;
import com.mongodb.MongoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HouseBusinessImpl implements HouseBusiness {

    private final HouseService houseService;

    public HouseBusinessImpl(HouseService houseService) {
        this.houseService = houseService;     }

    private static final Logger logger = LoggerFactory.getLogger(HouseBusinessImpl.class);



    public HouseBO createHouse(HouseBO houseBO) {
        try {
            HouseValidator.validate(houseBO);
            return HouseMapper.toDomain(houseService.create(HouseMapper.toDocument(houseBO)));
        }catch (MongoException e){
            logger.error("Error creating house", e);
            throw new TechnicalDatabaseException("Technical error while registering the house", e);
        }
    }

    public List<HouseBO> findHouses(){
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
                    .orElseThrow(() -> new HouseNotFoundException("House not found: " + id));
        } catch (MongoException e) {
            logger.error("Mongo error with id {}", id, e);
            throw new TechnicalDatabaseException("Technical error", e);
        }
    }

    public void deleteHouseById(String id) {
        try {
            if (!houseService.houseExistedById(id)) {
                throw new HouseNotFoundException("House not found: " + id);
            }
            houseService.deleteHouseById(id);
        } catch (MongoException e) {
            logger.error("Technical error while deleting", e);
            throw new TechnicalDatabaseException("MongoDB Technical Error", e);
        }
    }

}
