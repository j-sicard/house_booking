package com.HomeBooking.HomeBooking.adapter.out.mongo;

import com.HomeBooking.HomeBooking.adapter.out.mongo.document.HouseDocument;
import com.HomeBooking.HomeBooking.domain.model.House;
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
public class HouseMongoAdapterTest {

    @Autowired
    private  HouseMongoAdapter houseMongoAdapter;

    @Autowired
    private HouseMongoRepository houseMongoRepository;

    public House houseForTest(){
        House house = new House("1","House", "address here", 400.0);
        return house;
    }

    public List<HouseDocument> houseDocumentsForTest() {
        List<HouseDocument> documents = new ArrayList<>();
        documents.add(new HouseDocument("2", "House 1", "address 1", 500.0));
        documents.add(new HouseDocument("3", "House 2", "address 2", 600.0));
        documents.add(new HouseDocument("4", "House 3", "address 3", 700.0));
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

        houseMongoAdapter.create(houseForTest());

        assertTrue(before +1 == houseMongoRepository.findAll().size());
    }

    @Test
    void shouldCreateHouseWithCorrectTitle(){
        houseMongoAdapter.create(houseForTest());

        assertTrue(houseMongoRepository.findById("1").get().getTitle().equals("House"));
    }

    @Test
    void shouldCreateHouseWithCorrectAddress(){
        houseMongoAdapter.create(houseForTest());

        assertTrue(houseMongoRepository.findById("1").get().getAddress().equals("address here"));
    }

    @Test
    void shouldCreateHouseWithCorrectPrice(){
        houseMongoAdapter.create(houseForTest());

        assertTrue(houseMongoRepository.findById("1").get().getPrice().equals(400.0));
    }

    // *** findHouses *** //

    @Test
    void shouldReturnAllHouses(){
        assertEquals(3, houseMongoAdapter.findHouses().size());
    }

    @Test
    void shouldReturnHousesWithCorrectTitles() {
        assertTrue(houseMongoAdapter.findHouses().stream().map(House::getTitle).toList().containsAll(List.of("House 1", "House 2", "House 3")));
    }

    @Test
    void shouldReturnHousesWithCorrectAddress() {
        assertTrue(houseMongoAdapter.findHouses().stream().map(House::getAddress).toList().containsAll(List.of("address 1", "address 2", "address 3")));
    }

    @Test
    void shouldReturnHousesWithCorrectAdPrice() {
        assertTrue(houseMongoAdapter.findHouses().stream().map(House::getPrice).toList().containsAll(List.of(500.0, 600.0, 700.0)));
    }

    // *** deleteHouseById *** //

    @Test
    void shouldIdHouseDeletedCorrectly() {
        int houseCountBeforeTest = houseMongoRepository.findAll().size();

        houseMongoAdapter.deleteHouseById("2");

        assertTrue(houseCountBeforeTest == houseMongoRepository.findAll().size() + 1);
    }

    @Test
    void shouldDeleteHouseWithCorrectId() {
        houseMongoAdapter.deleteHouseById("2");

        assertFalse(houseMongoRepository.findAll().stream().map(HouseDocument :: getId).toList().contains("2"));
    }

    // *** findByHouseId *** //

    @Test
    void shouldReturnCorrectHouseWhenIdExists() {
        assertEquals("2", houseMongoAdapter.findHouseById("2").get().getId());
    }

    @Test
    void shouldFindHouseById() {
        assertTrue(houseMongoAdapter.findHouseById("2").isPresent());
    }

    @Test
    void shouldReturnEmptyWhenHouseIdDoesNotExist() {
        Optional<House> result = houseMongoAdapter.findHouseById("99");

        assertTrue(result.isEmpty());
    }

}
