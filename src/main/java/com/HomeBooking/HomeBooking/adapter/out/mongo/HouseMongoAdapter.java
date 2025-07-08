package com.HomeBooking.HomeBooking.adapter.out.mongo;

import com.HomeBooking.HomeBooking.domain.exception.TechnicalDatabaseException;
import com.HomeBooking.HomeBooking.domain.model.House;
import com.HomeBooking.HomeBooking.domain.port.out.HouseRepositoryPort;
import com.mongodb.MongoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Optional;

@Repository
public class HouseMongoAdapter implements HouseRepositoryPort {

    private final HouseMongoRepository mongoRepository;

    public HouseMongoAdapter(HouseMongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(HouseMongoAdapter.class);

    public House create(House house) throws Exception{
        return mongoRepository.save(house);
     }

    public List<House> findHouses() {
        try {
            return mongoRepository.findAll();
        } catch (MongoException e) {
            logger.error("Erreur lors de la récupération des maisons", e);
            throw new TechnicalDatabaseException("Erreur technique lors de la récupération des maisons", e);
        }
    }


    public void deleteHouseById(Long id){
        mongoRepository.deleteById(id);
     }

     public Optional<House> findHouseById(Long id) throws Exception{
        return mongoRepository.findById(id);
     }
}
