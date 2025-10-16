package com.HomeBooking.HomeBooking.controller;

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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Profile("test")
@ActiveProfiles("test")
public class DeleteHouseByIdTest {

    @Autowired
    HouseController houseController;

    @Autowired
    HouseMongoRepository houseMongoRepository;


    private HouseMO houseMOForTest(){
        HouseMO house = new HouseMO();
        house.setTitle("HouseTest");
        house.setAddress("address Test");
        house.setPrice(800.0);

        return house;
    }

    private  HouseMO createHouseMOFOrTest(){
        return  houseMongoRepository.save(houseMOForTest());
    }

    @AfterEach
    void tearDown() {
        houseMongoRepository.deleteAll();
    }

    @BeforeEach
    void cleanDatabase() {
        houseMongoRepository.deleteAll();
    }

    @Test
    void should_IfHouseCorrectlyDelete(){
        HouseMO houseMO = createHouseMOFOrTest();
        assertTrue(houseMongoRepository.findById(houseMO.getId()).isPresent());

        houseController.deleteHouse(houseMO.getId());
        assertFalse(houseMongoRepository.findById(houseMO.getId()).isPresent());
    }

    @Test
    void deleteHouse_WhenHouseExists_ShouldReturnOk(){
        HouseMO houseMO = createHouseMOFOrTest();

        ResponseEntity<?> response = houseController.deleteHouse(houseMO.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    void deleteHouse_WhenHouseExists_ShouldReturnSuccessMessage() {
        HouseMO houseMO = createHouseMOFOrTest();

        ResponseEntity<?> response = houseController.deleteHouse(houseMO.getId());

        assertEquals("House deleted successfully", response.getBody());
    }

    @Test
    void deleteHouse_WhenHouseNotExist_ShouldReturnStatusError(){
        ResponseEntity<?> response = houseController.deleteHouse("1");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}

