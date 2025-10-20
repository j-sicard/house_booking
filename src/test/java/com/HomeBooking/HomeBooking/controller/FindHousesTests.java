package com.HomeBooking.HomeBooking.controller;

import com.HomeBooking.HomeBooking.DTO.HouseDTO;
import com.HomeBooking.HomeBooking.FO.HouseFO;
import com.HomeBooking.HomeBooking.model.HouseMO;
import com.HomeBooking.HomeBooking.repository.HouseMongoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Profile("test")
@ActiveProfiles("test")
public class FindHousesTests  {

    @Autowired
    HouseController houseController;

    @Autowired
    HouseMongoRepository houseMongoRepository;

    public List<HouseMO> listHouseMOForTest() {
        List<HouseMO> houses = new ArrayList<>();
        houses.add(new HouseMO("1", "House 1", "Address 1", 500.0));
        houses.add(new HouseMO("2", "House 2", "Address 2", 600.0));
        houses.add(new HouseMO("3", "House 3", "Address 3", 700.0));
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

    @AfterEach
    void tearDown() {
        houseMongoRepository.deleteAll();
    }

    @BeforeEach
    void cleanDatabase() {
        houseMongoRepository.deleteAll();
    }

    // *** FindHouses *** //

    @Test
    void findHouses_shouldNotReturnNull(){
        saveThreeHouses();

        assertNotNull( houseController.findHouses());
    }

    @Test
    void findHouses_shouldReturnCorrectHousesSize(){
        saveThreeHouses();

        ResponseEntity<?> response = houseController.findHouses();

        @SuppressWarnings("unchecked")
        List<HouseDTO> houses = (List<HouseDTO>) response.getBody();

        assertEquals(3, houses.size());
    }

    @Test
    void findHouses_shouldFindCorrectHousesFields() {
        saveThreeHouses();

        ResponseEntity<?> response = houseController.findHouses();

        @SuppressWarnings("unchecked")
        List<HouseDTO> houses = (List<HouseDTO>) response.getBody();

        assertThat(houses)
                .extracting(HouseDTO::getTitle, HouseDTO::getAddress, HouseDTO::getPrice)
                .usingComparatorForType(String.CASE_INSENSITIVE_ORDER, String.class)
                .containsExactlyInAnyOrder(
                        tuple("House 1", "Address 1", 500.0),
                        tuple("House 2", "Address 2", 600.0),
                        tuple("House 3", "Address 3", 700.0)
                );

    }


}
