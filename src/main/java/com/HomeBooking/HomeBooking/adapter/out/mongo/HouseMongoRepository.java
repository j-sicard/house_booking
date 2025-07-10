package com.HomeBooking.HomeBooking.adapter.out.mongo;

import com.HomeBooking.HomeBooking.adapter.out.mongo.document.HouseDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HouseMongoRepository extends MongoRepository<HouseDocument, String> {
}
