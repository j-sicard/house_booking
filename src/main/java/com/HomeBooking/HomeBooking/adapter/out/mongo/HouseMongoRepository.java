package com.HomeBooking.HomeBooking.adapter.out.mongo;

import com.HomeBooking.HomeBooking.domain.model.House;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HouseMongoRepository extends MongoRepository<House, Long> {
}
