package com.HomeBooking.HomeBooking.business.impl;

import com.HomeBooking.HomeBooking.BO.HouseBO;
import com.HomeBooking.HomeBooking.FO.HouseFO;
import com.HomeBooking.HomeBooking.mapper.HouseFormMapper;
import com.HomeBooking.HomeBooking.business.HouseBusiness;
import com.HomeBooking.HomeBooking.exceptions.HouseNotFoundException;
import com.HomeBooking.HomeBooking.exceptions.InvalidHouseException;
import com.HomeBooking.HomeBooking.exceptions.TechnicalDatabaseException;
import com.HomeBooking.HomeBooking.service.HouseService;
import com.HomeBooking.HomeBooking.mapper.HouseValidator;
import com.HomeBooking.HomeBooking.mapper.HouseUpdate;
import com.mongodb.MongoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseBusinessImpl implements HouseBusiness {

    private final HouseService houseService;

    public HouseBusinessImpl(HouseService houseService) {
        this.houseService = houseService;     }

    private static final Logger logger = LoggerFactory.getLogger(HouseBusinessImpl.class);



    public HouseBO createHouse(HouseBO houseBO) {
        try {
            HouseValidator.validate(houseBO);
            return houseService.saveHouse(houseBO);
        }catch (MongoException e){
            logger.error("Error creating house", e);
            throw new TechnicalDatabaseException("Technical error while registering the house", e);
        }
    }

    public List<HouseBO> findHouses(){
        try {
            return houseService.findHouses();
        } catch (MongoException e) {
            logger.error("Error retrieving houses", e);
            throw new TechnicalDatabaseException("Technical error while retrieving houses", e);
        }
    }

    public HouseBO findHouseById(String id) {
        try {
            return houseService.findHouseById(id)
                    .orElseThrow(() -> new HouseNotFoundException("House not found: " + id));
        } catch (MongoException e) {
            logger.error("Technical error while retrieving house with id: " + id, e);
            throw new TechnicalDatabaseException("Technical error while retrieving house with id: " + id, e);
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

    public void updateHouse(HouseBO houseBO) {
        HouseBO existingHouse = houseService.findHouseById(houseBO.getId())
                .orElseThrow(() -> new HouseNotFoundException("House not found: " + houseBO.getId()));

        try {
            HouseValidator.validate(HouseUpdate.updateHouseBO(existingHouse, houseBO));

            houseService.saveHouse(existingHouse);

        } catch (InvalidHouseException e) {
            throw e;
        } catch (Exception e) {
            throw new TechnicalDatabaseException("Error while updating house: " + e.getMessage(), e);
        }

    }
}







