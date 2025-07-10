package com.HomeBooking.HomeBooking.adapter.out.mongo.mapper;

import com.HomeBooking.HomeBooking.adapter.out.mongo.document.HouseDocument;
import com.HomeBooking.HomeBooking.domain.model.House;

public class HouseMapper {

    // *** For save ** //

    public static HouseDocument toDocument(House house) {
        if(house == null) return null;

        HouseDocument doc = new HouseDocument();
        doc.setId(house.getId());
        doc.setAddress(house.getAddress());
        doc.setTitle(house.getTitle());
        doc.setPrice(house.getPrice());
        return doc;
    }

    // *** For read *** //

    public static House toDomain(HouseDocument doc) {
        if (doc == null) return null;

        House house = new House();
        house.setId(doc.getId());
        house.setAddress(doc.getAddress());
        house.setTitle(doc.getTitle());
        house.setPrice(doc.getPrice());
        return house;
    }
}
