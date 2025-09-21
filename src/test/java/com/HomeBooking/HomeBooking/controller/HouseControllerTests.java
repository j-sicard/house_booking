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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

    // *** CreateHouseController

    @Test
    void createHouse_shouldInsertOneHouseInDatabase() {
        houseController.createHouse(houseFoForTest());

        assertThat(houseMongoRepository.findAll()).hasSize(1);
    }

    @Test
    void createHouse_shouldSaveCorrectHouseFields() {
        houseController.createHouse(houseFoForTest());

        assertThat(houseMongoRepository.findAll())
                .extracting(HouseMO::getTitle, HouseMO::getAddress, HouseMO::getPrice)
                .containsExactly(
                        org.assertj.core.api.Assertions.tuple("HouseTest", "address Test", 800.0)
                );
    }

    @Test
    void createHouse_shouldReturnNotNullResponse() {
        ResponseEntity<?> response = houseController.createHouse(houseFoForTest());

        assertNotNull(response);
    }

    @Test
    void createHouse_shouldReturnCreatedStatus() {
        ResponseEntity<?> response = houseController.createHouse(houseFoForTest());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void createHouse_shouldReturnHouseDto() {
        ResponseEntity<?> response = houseController.createHouse(houseFoForTest());

        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof HouseDTO);
    }


    @Test
    void createHouse_shouldReturnHouseDtoWithCorrectValues() {
        ResponseEntity<?> response = houseController.createHouse(houseFoForTest());

        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof HouseDTO);

        HouseDTO houseDto = (HouseDTO) response.getBody();
        assertEquals("HouseTest", houseDto.getTitle());
        assertEquals("address Test", houseDto.getAddress());
        assertEquals(800.0, houseDto.getPrice());
    }


}