package com.HomeBooking.HomeBooking.controller;

import com.HomeBooking.HomeBooking.FO.HouseFO;
import com.HomeBooking.HomeBooking.model.HouseMO;
import com.HomeBooking.HomeBooking.repository.HouseMongoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Profile("test")
@ActiveProfiles("test")
public class UpdateHouseTests {

    @Autowired
    HouseController houseController;

    @Autowired
    HouseMongoRepository houseMongoRepository;

    private HouseFO houseFoForTest(){
        HouseFO house = new HouseFO();
        house.setTitle("UpdateHouseForTest");
        house.setAddress("address Test");
        house.setPrice(800.0);

        return house;
    }

    private HouseMO houseMoForTest(){
        HouseMO house = new HouseMO();
        house.setTitle("HouseTest");
        house.setAddress("address Test");
        house.setPrice(800.0);

        return house;
    }

}
