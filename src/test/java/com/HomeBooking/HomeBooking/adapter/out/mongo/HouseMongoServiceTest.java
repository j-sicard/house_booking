package com.HomeBooking.HomeBooking.adapter.out.mongo;

import com.HomeBooking.HomeBooking.model.HouseMO;
import com.HomeBooking.HomeBooking.BO.HouseBO;
import com.HomeBooking.HomeBooking.repository.HouseMongoRepository;
import com.HomeBooking.HomeBooking.service.impl.HouseMongoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Profile("test")
@ActiveProfiles("test")
public class HouseMongoServiceTest {

    @Autowired
    private HouseMongoServiceImpl houseMongoService;

    @Autowired
    private HouseMongoRepository houseMongoRepository;

    public HouseBO houseForTest(){
        HouseBO house = new HouseBO("1","House", "address here", 400.0);
        return house;
    }

    public List<HouseMO> houseDocumentsForTest() {
        List<HouseMO> documents = new ArrayList<>();
        documents.add(new HouseMO("2", "House 1", "address 1", 500.0));
        documents.add(new HouseMO("3", "House 2", "address 2", 600.0));
        documents.add(new HouseMO("4", "House 3", "address 3", 700.0));
        return documents;
    }

    private void saveThreeHouseDocuments() {
        houseMongoRepository.save(houseDocumentsForTest().get(0));
        houseMongoRepository.save(houseDocumentsForTest().get(1));
        houseMongoRepository.save(houseDocumentsForTest().get(2));
    }

    @BeforeEach
    void cleanDatabase() {
        houseMongoRepository.deleteAll();
    }


    @BeforeEach
    void createData(){
        saveThreeHouseDocuments();
    }

    // *** create *** //

    @Test
    void shouldCreateHouseData(){
        int before = houseMongoRepository.findAll().size();

        houseMongoService.create(houseForTest());

        assertTrue(before +1 == houseMongoRepository.findAll().size());
    }

    @Test
    void shouldCreateHouseWithCorrectTitle(){
        houseMongoService.create(houseForTest());

        assertTrue(houseMongoRepository.findById("1").get().getTitle().equals("House"));
    }

    @Test
    void shouldCreateHouseWithCorrectAddress(){
        houseMongoService.create(houseForTest());

        assertTrue(houseMongoRepository.findById("1").get().getAddress().equals("address here"));
    }

    @Test
    void shouldCreateHouseWithCorrectPrice(){
        houseMongoService.create(houseForTest());

        assertTrue(houseMongoRepository.findById("1").get().getPrice().equals(400.0));
    }

    // *** findHouses *** //

    @Test
    void shouldReturnAllHouses(){
        assertEquals(3, houseMongoService.findHouses().size());
    }

    @Test
    void shouldReturnHousesWithCorrectTitles() {
        assertTrue(houseMongoService.findHouses().stream().map(HouseBO::getTitle).toList().containsAll(List.of("House 1", "House 2", "House 3")));
    }

    @Test
    void shouldReturnHousesWithCorrectAddress() {
        assertTrue(houseMongoService.findHouses().stream().map(HouseBO::getAddress).toList().containsAll(List.of("address 1", "address 2", "address 3")));
    }

    @Test
    void shouldReturnHousesWithCorrectAdPrice() {
        assertTrue(houseMongoService.findHouses().stream().map(HouseBO::getPrice).toList().containsAll(List.of(500.0, 600.0, 700.0)));
    }

    // *** deleteHouseById *** //

    @Test
    void shouldIdHouseDeletedCorrectly() {
        int houseCountBeforeTest = houseMongoRepository.findAll().size();

        houseMongoService.deleteHouseById("2");

        assertTrue(houseCountBeforeTest == houseMongoRepository.findAll().size() + 1);
    }

    @Test
    void shouldDeleteHouseWithCorrectId() {
        houseMongoService.deleteHouseById("2");

        assertFalse(houseMongoRepository.findAll().stream().map(HouseMO:: getId).toList().contains("2"));
    }

    // *** findByHouseId *** //

    @Test
    void shouldReturnCorrectHouseWhenIdExists() {
        assertEquals("2", houseMongoService.findHouseById("2").get().getId());
    }

    @Test
    void shouldFindHouseById() {
        assertTrue(houseMongoService.findHouseById("2").isPresent());
    }

    @Test
    void shouldReturnEmptyWhenHouseIdDoesNotExist() {
        Optional<HouseBO> result = houseMongoService.findHouseById("99");

        assertTrue(result.isEmpty());
    }

}
