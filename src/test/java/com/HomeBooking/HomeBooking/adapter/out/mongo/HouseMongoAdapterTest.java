package com.HomeBooking.HomeBooking.adapter.out.mongo;

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
        House house = new House(1L,"House", "address here", 400.0);
        return house;
    }

    public List<House> housesForTest() {
        List<House> houses = new ArrayList<>();
        houses.add(new House(2L, "House 1", "address 1", 500.0));
        houses.add(new House(3L, "House 2", "address 2", 600.0));
        houses.add(new House(4L, "House 3", "address 3", 700.0));
        return houses;
    }

    private void saveThreeTestHouses() {
        houseMongoRepository.save(housesForTest().get(0));
        houseMongoRepository.save(housesForTest().get(1));
        houseMongoRepository.save(housesForTest().get(2));
    }

    @BeforeEach
    void cleanDatabase() {
        houseMongoRepository.deleteAll();
    }


    @BeforeEach
    void createData(){
        saveThreeTestHouses();
    }

    // *** create *** //

    @Test
    void shouldCreateHouseData() throws Exception{
        int before = houseMongoRepository.findAll().size();

        houseMongoAdapter.create(houseForTest());

        assertTrue(before +1 == houseMongoRepository.findAll().size());
    }

    @Test
    void shouldCreateHouseWithCorrectTitle() throws Exception{
        houseMongoAdapter.create(houseForTest());

        assertTrue(houseMongoRepository.findById(1L).get().getTitle().equals("House"));
    }

    @Test
    void shouldCreateHouseWithCorrectAddress() throws Exception{
        houseMongoAdapter.create(houseForTest());

        assertTrue(houseMongoRepository.findById(1L).get().getAddress().equals("address here"));
    }

    @Test
    void shouldCreateHouseWithCorrectPrice() throws Exception{
        houseMongoAdapter.create(houseForTest());

        assertTrue(houseMongoRepository.findById(1L).get().getPrice().equals(400.0));
    }

    // *** findHouses *** //

    @Test
    void shouldReturnAllHouses() throws Exception{
        assertEquals(3, houseMongoAdapter.findHouses().size());
    }

    @Test
    void shouldReturnHousesWithCorrectTitles() throws Exception {
        assertTrue(houseMongoAdapter.findHouses().stream().map(House::getTitle).toList().containsAll(List.of("House 1", "House 2", "House 3")));
    }

    @Test
    void shouldReturnHousesWithCorrectAddress() throws Exception {
        assertTrue(houseMongoAdapter.findHouses().stream().map(House::getAddress).toList().containsAll(List.of("address 1", "address 2", "address 3")));
    }

    @Test
    void shouldReturnHousesWithCorrectAdPrice() throws Exception {
        assertTrue(houseMongoAdapter.findHouses().stream().map(House::getPrice).toList().containsAll(List.of(500.0, 600.0, 700.0)));
    }

    // *** deleteHouseById *** //

    @Test
    void shouldIdHouseDeletedCorrectly(){
        int houseCountBeforeTest = houseMongoRepository.findAll().size();

        houseMongoAdapter.deleteHouseById(2L);

        assertTrue(houseCountBeforeTest == houseMongoRepository.findAll().size() + 1);
    }

    @Test
    void shouldDeleteHouseWithCorrectId(){
        houseMongoAdapter.deleteHouseById(2L);

        assertFalse(houseMongoRepository.findAll().stream().map(House :: getId).toList().contains(2L));
    }

    // *** findByHouseId *** //

    @Test
    void shouldReturnCorrectHouseWhenIdExists() throws Exception{
        assertEquals(2L, houseMongoAdapter.findHouseById(2L).get().getId());
    }

    @Test
    void shouldFindHouseById() throws Exception {
        assertTrue(houseMongoAdapter.findHouseById(2L).isPresent());
    }

    @Test
    void shouldReturnEmptyWhenHouseIdDoesNotExist() throws Exception{
        Optional<House> result = houseMongoAdapter.findHouseById(99L);

        assertTrue(result.isEmpty());
    }

}
