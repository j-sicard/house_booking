package com.HomeBooking.HomeBooking.controller;

import com.HomeBooking.HomeBooking.model.HouseMO;
import com.HomeBooking.HomeBooking.repository.HouseMongoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Profile("test")
@ActiveProfiles("test")
public class HouseControllerTests {

    @Autowired
    HouseController houseController;

    @Autowired
    HouseMongoRepository houseMongoRepository;

    public List<HouseMO> listHouseMOForTest() {
        List<HouseMO> houses = new ArrayList<>();
        houses.add(new HouseMO("1", "House 1", "address 1", 500.0));
        houses.add(new HouseMO("2", "House 2", "address 2", 600.0));
        houses.add(new HouseMO("3", "House 3", "address 3", 700.0));
        return houses;
    }


    private void saveThreeHouses() {
        houseMongoRepository.save(listHouseMOForTest().get(0));
        houseMongoRepository.save(listHouseMOForTest().get(1));
        houseMongoRepository.save(listHouseMOForTest().get(2));
    }

    @BeforeEach
    void cleanDatabase() {
        houseMongoRepository.deleteAll();
    }


    @Test
    void findHousesTest() {
        houseController.findHouses();

        assertTrue(true);
    }
}