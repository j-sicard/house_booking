package com.HomeBooking.HomeBooking.repository;

import com.HomeBooking.HomeBooking.model.HouseMO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HouseMongoRepository extends MongoRepository<HouseMO, String> {
}
