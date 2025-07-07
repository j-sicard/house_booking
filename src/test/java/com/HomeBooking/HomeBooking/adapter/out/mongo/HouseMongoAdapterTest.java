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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Profile("test")
@ActiveProfiles("test")
public class HouseMongoAdapterTest {

    @Autowired
    private  HouseMongoAdapter houseMongoAdapter;

    @Autowired
    private HouseMongoRepository houseMongoRepository;

    @BeforeEach
    void cleanDatabase() {
        houseMongoRepository.deleteAll();
    }

    public House houseForTest(){
        House house = new House(1L,"House", "address here", 500.0);
        return house;
    }

    public List<House> housesForTest() {
        List<House> houses = new ArrayList<>();
        houses.add(new House(1L, "House 1", "address 1", 500.0));
        houses.add(new House(2L, "House 2", "address 2", 600.0));
        houses.add(new House(3L, "House 3", "address 3", 700.0));
        return houses;
    }

    private void saveThreeTestHouses() {
        houseMongoRepository.save(housesForTest().get(0));
        houseMongoRepository.save(housesForTest().get(1));
        houseMongoRepository.save(housesForTest().get(2));
    }

    // *** create *** //

    @Test
    void shouldCreateHouseData() throws Exception{
        int before = houseMongoRepository.findAll().size();
        assertTrue( before == 0);

        houseMongoAdapter.create(houseForTest());
        int after = houseMongoRepository.findAll().size();

        assertTrue(before +1 == after);
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

        assertTrue(houseMongoRepository.findById(1L).get().getPrice().equals(500.0));
    }

    // *** findHouses *** //

    @Test
    void shouldReturnAllHouses() throws Exception{
        saveThreeTestHouses();

        assertEquals(3, houseMongoAdapter.findHouses().size());
    }

    @Test
    void shouldReturnHousesWithCorrectTitles() throws Exception {
        saveThreeTestHouses();

        assertTrue(houseMongoAdapter.findHouses().stream().map(House::getTitle).toList().containsAll(List.of("House 1", "House 2", "House 3")));
    }

    @Test
    void shouldReturnHousesWithCorrectAddress() throws Exception {
        saveThreeTestHouses();

        assertTrue(houseMongoAdapter.findHouses().stream().map(House::getAddress).toList().containsAll(List.of("address 1", "address 2", "address 3")));
    }

    @Test
    void shouldReturnHousesWithCorrectAdPrice() throws Exception {
        saveThreeTestHouses();

        assertTrue(houseMongoAdapter.findHouses().stream().map(House::getPrice).toList().containsAll(List.of(500.0, 600.0, 700.0)));
    }

    // *** deleteHouseById *** //

    @Test
    void shouldIdHouseDeletedCorrectly(){
        saveThreeTestHouses();

        int houseCountBeforeTest = houseMongoRepository.findAll().size();

        houseMongoAdapter.deleteHouseById(1L);

        int houseCountAfterTest = houseMongoRepository.findAll().size();

        assertTrue(houseCountBeforeTest == houseCountAfterTest + 1);
    }

    @Test
    void shouldIfHouseDeleteHaveGoodId(){
        saveThreeTestHouses();

        houseMongoAdapter.deleteHouseById(1L);

       assertFalse(houseMongoRepository.findAll().stream().map(House :: getId).toList().contains(1L));
    }

}
