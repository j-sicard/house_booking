package com.HomeBooking.HomeBooking.controller;

import com.HomeBooking.HomeBooking.DTO.HouseDTO;
import com.HomeBooking.HomeBooking.FO.HouseFO;
import com.HomeBooking.HomeBooking.model.HouseMO;
import com.HomeBooking.HomeBooking.repository.HouseMongoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.tuple;


@SpringBootTest
@Profile("test")
@ActiveProfiles("test")
public class FindHouseByIdTests {

    @Autowired
    HouseMongoRepository houseMongoRepository;

    @Autowired
    HouseController houseController;

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

    private HouseFO houseFoForTest(){
        HouseFO house = new HouseFO();
        house.setTitle("HouseTest");
        house.setAddress("address Test");
        house.setPrice(800.0);

        return house;
    }

    @Test
    void should_IfNotReturnNull(){
        saveThreeHouses();
        assertNotNull(houseController.findHouseById("1"));
    }

    @Test
    void should_IfReturnCorrectlyData() {
        saveThreeHouses();

        ResponseEntity<?> response = houseController.findHouseById("1");
        HouseDTO houseDTO = (HouseDTO) response.getBody();

        assertThat(houseDTO)
                .extracting(HouseDTO::getTitle, HouseDTO::getAddress, HouseDTO::getPrice)
                .containsExactly("House 1", "address 1", 500.0);
    }



}
