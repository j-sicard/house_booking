package com.HomeBooking.HomeBooking.adapter.out.mongo;

import com.HomeBooking.HomeBooking.domain.model.House;
import com.HomeBooking.HomeBooking.domain.port.out.HouseRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HouseMongoAdapter implements HouseRepositoryPort {

    private final HouseMongoRepository mongoRepository;

    public HouseMongoAdapter(HouseMongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

     public House create(House house) throws Exception{
        return mongoRepository.save(house);
     }

     public List<House> findHouses() throws  Exception{
        return mongoRepository.findAll();
     }

     public void deleteHouseById(Long id){
        mongoRepository.deleteById(id);
     }
}
