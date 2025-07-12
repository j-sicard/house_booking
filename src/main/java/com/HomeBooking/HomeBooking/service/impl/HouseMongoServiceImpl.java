package com.HomeBooking.HomeBooking.service.impl;

import com.HomeBooking.HomeBooking.model.HouseMO;
import com.HomeBooking.HomeBooking.service.HouseService;
import com.HomeBooking.HomeBooking.utils.HouseMapper;
import com.HomeBooking.HomeBooking.exceptions.dataexception.TechnicalDatabaseException;
import com.HomeBooking.HomeBooking.BO.HouseBO;
import com.HomeBooking.HomeBooking.repository.HouseMongoRepository;
import com.mongodb.MongoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class HouseMongoServiceImpl implements HouseService {

    private final HouseMongoRepository mongoRepository;

    public HouseMongoServiceImpl(HouseMongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(HouseMongoServiceImpl.class);

    public HouseBO create(HouseBO house) {
        try {
            HouseMO savedDoc = mongoRepository.save(HouseMapper.toDocument(house));

            return HouseMapper.toDomain(savedDoc);
        } catch (MongoException e) {
            logger.error("Error creating house", e);
            throw new TechnicalDatabaseException("Technical error while registering the house", e);
        }
    }

    public List<HouseBO> findHouses() {
        try {
            List<HouseMO> documents = mongoRepository.findAll();

            List<HouseBO> houses = documents.stream()
                    .map(HouseMapper::toDomain)
                    .collect(Collectors.toList());
            return houses;
        } catch (MongoException e) {
            logger.error("Error retrieving houses", e);
            throw new TechnicalDatabaseException("Technical error while retrieving houses", e);
        }
    }


    public void deleteHouseById(String id){
        try{
            mongoRepository.deleteById(id);
        }catch (MongoException e) {
            logger.error("Error deleting house", e);
            throw new TechnicalDatabaseException("Technical error while deleting house", e);
        }
     }

    @Override
    public Optional<HouseBO> findHouseById(String id) {
        try {
            return mongoRepository.findById(id)
                    .map(HouseMapper::toDomain);
        } catch (MongoException e) {
            logger.error("Error retrieving house with id: {}", id, e);
            throw new TechnicalDatabaseException(
                    "Technical error while retrieving house with id: " + id, e);
        }
    }

}
