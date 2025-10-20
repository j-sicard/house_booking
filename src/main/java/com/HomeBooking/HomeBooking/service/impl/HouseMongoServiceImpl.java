package com.HomeBooking.HomeBooking.service.impl;

import com.HomeBooking.HomeBooking.BO.HouseBO;
import com.HomeBooking.HomeBooking.model.HouseMO;
import com.HomeBooking.HomeBooking.service.HouseService;
import com.HomeBooking.HomeBooking.repository.HouseMongoRepository;
import com.HomeBooking.HomeBooking.utils.HouseMongoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HouseMongoServiceImpl implements HouseService {

    private final HouseMongoRepository mongoRepository;

    private static final Logger logger = LoggerFactory.getLogger(HouseMongoServiceImpl.class);

    public HouseMongoServiceImpl(HouseMongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    public HouseBO createHouse(HouseBO house) {
            return HouseMongoMapper.toDomain(mongoRepository.save(HouseMongoMapper.toDocument(house))) ;
    }

    @Override
    public List<HouseBO> findHouses() {
        return mongoRepository.findAll()
                .stream()
                .map(HouseMongoMapper::toDomain)
                .toList();
    }

    public void deleteHouseById(String id){
            mongoRepository.deleteById(id);
     }


    public Optional<HouseBO> findHouseById(String id) {
        return mongoRepository.findById(id)
                .map(HouseMongoMapper::toDomain);
    }

    public Boolean houseExistedById(String id){
        return mongoRepository.existsById(id);
    }
}
