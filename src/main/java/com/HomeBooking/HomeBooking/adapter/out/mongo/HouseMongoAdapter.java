package com.HomeBooking.HomeBooking.adapter.out.mongo;

import com.HomeBooking.HomeBooking.adapter.out.mongo.document.HouseDocument;
import com.HomeBooking.HomeBooking.adapter.out.mongo.mapper.HouseMapper;
import com.HomeBooking.HomeBooking.domain.exception.TechnicalDatabaseException;
import com.HomeBooking.HomeBooking.domain.model.House;
import com.HomeBooking.HomeBooking.domain.port.out.HouseRepositoryPort;
import com.mongodb.MongoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class HouseMongoAdapter implements HouseRepositoryPort {

    private final HouseMongoRepository mongoRepository;

    public HouseMongoAdapter(HouseMongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(HouseMongoAdapter.class);

    public House create(House house) {
        try {
            HouseDocument savedDoc = mongoRepository.save(HouseMapper.toDocument(house));

            return HouseMapper.toDomain(savedDoc);
        } catch (MongoException e) {
            logger.error("Error creating house", e);
            throw new TechnicalDatabaseException("Technical error while registering the house", e);
        }
    }

    public List<House> findHouses() {
        try {
            List<HouseDocument> documents = mongoRepository.findAll();

            List<House> houses = documents.stream()
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
    public Optional<House> findHouseById(String id) {
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
