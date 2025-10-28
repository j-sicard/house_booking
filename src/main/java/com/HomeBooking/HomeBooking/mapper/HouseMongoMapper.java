package com.HomeBooking.HomeBooking.mapper;

import com.HomeBooking.HomeBooking.model.HouseMO;
import com.HomeBooking.HomeBooking.BO.HouseBO;

public class HouseMongoMapper {

    // *** For save ** //

    public static HouseMO toDocument(HouseBO house) {
        if(house == null) return null;

        HouseMO doc = new HouseMO();
        doc.setId(house.getId());
        doc.setAddress(house.getAddress());
        doc.setTitle(house.getTitle());
        doc.setPrice(house.getPrice());
        return doc;
    }

    // *** For read *** //

    public static HouseBO toDomain(HouseMO doc) {
        if (doc == null) return null;

        HouseBO house = new HouseBO();
        house.setId(doc.getId());
        house.setAddress(doc.getAddress());
        house.setTitle(doc.getTitle());
        house.setPrice(doc.getPrice());
        return house;
    }
}
