package com.HomeBooking.HomeBooking.service.impl;

import com.HomeBooking.HomeBooking.model.HouseMO;
import com.HomeBooking.HomeBooking.service.HouseService;
import com.HomeBooking.HomeBooking.utils.HouseMapper;
import com.HomeBooking.HomeBooking.exceptions.TechnicalDatabaseException;
import com.HomeBooking.HomeBooking.BO.HouseBO;
import com.HomeBooking.HomeBooking.repository.HouseMongoRepository;
import com.mongodb.MongoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HouseMongoServiceImpl implements HouseService {

    private final HouseMongoRepository mongoRepository;

    public HouseMongoServiceImpl(HouseMongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(HouseMongoServiceImpl.class);

    public HouseMO create(HouseMO house) {
            return mongoRepository.save(house);
    }

    public List<HouseMO> findHouses() {
            return mongoRepository.findAll();
    }


    public void deleteHouseById(String id){
            mongoRepository.deleteById(id);
     }

    @Override
    public Optional<HouseMO> findHouseById(String id) {
            return mongoRepository.findById(id);
    }

    public Boolean houseExistedById(String id){
        return mongoRepository.existsById(id);
    }

}
