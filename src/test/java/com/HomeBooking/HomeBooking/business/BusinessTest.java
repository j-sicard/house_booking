package com.HomeBooking.HomeBooking.business;

import com.HomeBooking.HomeBooking.BO.HouseBO;
import com.HomeBooking.HomeBooking.business.impl.HouseBusinessImpl;
import com.HomeBooking.HomeBooking.exceptions.HouseNotFoundException;
import com.HomeBooking.HomeBooking.exceptions.InvalidHouseException;
import com.HomeBooking.HomeBooking.mapper.HouseMongoMapper;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Profile("test")
@ActiveProfiles("test")
public class BusinessTest {

    @Autowired
    HouseBusinessImpl houseBusiness;

    @Autowired
    HouseMongoRepository houseMongoRepository;

    private HouseMO houseMoForTest(){
        HouseMO house = new HouseMO();
        house.setTitle("HouseTest");
        house.setAddress("house address for test");
        house.setPrice(500.0);
        return house;
    }

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


    private HouseBO houseBOForTest(){
        HouseBO house = new HouseBO();
        house.setTitle("HouseTest");
        house.setAddress("house address for test");
        house.setPrice(500.0);
        return house;
    }

    private HouseMO houseMOForTest(){
        HouseMO house = new HouseMO();
        house.setTitle("HouseTest");
        house.setAddress("house address for test");
        house.setPrice(500.0);
        return house;
    }

    // *** createHouse *** //

    @Test
    void shouldIncreaseHouseCountWhenHouseIsCreated(){
        Long countHouseBeforeHouseSave = houseMongoRepository.count();
        houseBusiness.createHouse(houseBOForTest());

        assertEquals(countHouseBeforeHouseSave +1 , houseMongoRepository.count());
    }

    @Test
    void shouldThrowInvalidHouseExceptionWhenCreatingHouseWithMissingPrice() {
        HouseBO houseWithMissingPrice = new HouseBO();
        houseWithMissingPrice.setTitle("title house");
        houseWithMissingPrice.setAddress("house address");

        InvalidHouseException thrown = assertThrows(
                InvalidHouseException.class,
                () -> houseBusiness.createHouse(houseWithMissingPrice)
        );

        assertTrue(thrown.getMessage().contains("Price must be > 0."));
    }

    @Test
    void shouldThrowInvalidHouseExceptionWhenCreatingHouseWithMissingAddress() {
        HouseBO houseWithMissingAddress = new HouseBO();
        houseWithMissingAddress.setTitle("title house");
        houseWithMissingAddress.setPrice(500.0);

        InvalidHouseException thrown = assertThrows(
                InvalidHouseException.class,
                () -> houseBusiness.createHouse(houseWithMissingAddress)
        );

        assertTrue(thrown.getMessage().contains("Address is required."));
    }

    @Test
    void shouldThrowInvalidHouseExceptionWhenCreatingHouseWithMissingTitle() {
        HouseBO houseWithMissingTitle = new HouseBO();
        houseWithMissingTitle.setAddress("address house");
        houseWithMissingTitle.setPrice(500.0);

        InvalidHouseException thrown = assertThrows(
                InvalidHouseException.class,
                () -> houseBusiness.createHouse(houseWithMissingTitle)
        );

        assertTrue(thrown.getMessage().contains("the Title is required."));
    }

    @Test
    void shouldThrowInvalidHouseExceptionWhenCreatingEmptyHouse() {
        InvalidHouseException thrown = assertThrows(
                InvalidHouseException.class,
                () -> houseBusiness.createHouse(null)
        );

        assertTrue(thrown.getMessage().contains("The house cannot be zero."));
    }

   // *** findHouses *** //

    @Test
    void shouldIncreaseHouseCountWhenFindHouses(){
        Long houseCountBeforeTest = houseMongoRepository.count();
        saveThreeHouses();

        assertEquals(houseCountBeforeTest +3, houseBusiness.findHouses().size());
    }

    // *** deleteHouseById *** //

    @Test
    void shouldDecreaseHouseCountByOneAfterDeletion(){
        saveThreeHouses();
        Long housesCountBeforeTest = houseMongoRepository.count();

        houseBusiness.deleteHouseById(listHouseMOForTest().get(1).getId());

        assertEquals(housesCountBeforeTest - 1 , houseMongoRepository.count());
    }

    @Test
    void shouldNotContainDeletedHouseInRepository() {
        saveThreeHouses();
        String houseIdToDelete = listHouseMOForTest().get(1).getId();

        houseBusiness.deleteHouseById(houseIdToDelete);

        List<String> remainingIds = houseMongoRepository.findAll()
                .stream()
                .map(HouseMO::getId)
                .toList();

        assertFalse(remainingIds.contains(houseIdToDelete));
    }

    @Test
    void shouldThrowHouseNotFoundExceptionWhenIdDoesNotExist() {
        saveThreeHouses();

        HouseNotFoundException thrown = assertThrows(
                HouseNotFoundException.class,
                () -> houseBusiness.deleteHouseById("4")
        );

        assertTrue(thrown.getMessage().contains("House not found: 4"));
    }

    // *** finById *** //

    @Test
    void shouldFindByIdReturnObjectNotNullSuccessful(){
        saveThreeHouses();

        assertNotNull(houseBusiness.findHouseById(listHouseMOForTest().get(1).getId()));
    }

    @Test
    void shouldThrowExceptionWhenFindingHouseWithInvalidId(){
        saveThreeHouses();

        HouseNotFoundException thrown = assertThrows(
                HouseNotFoundException.class,
                () -> houseBusiness.findHouseById("99")
        );

        assertTrue(thrown.getMessage().contains("House not found: 99"));
    }

    @Test
    void shouldReturnCorrectTitleWhenFindingHouseById(){
        saveThreeHouses();

        assertEquals("House 1", houseBusiness.findHouseById("1").getTitle());
    }

    @Test
    void shouldReturnCorrectAddressWhenFindingHouseById(){
        saveThreeHouses();

        assertEquals("address 1", houseBusiness.findHouseById("1").getAddress());
    }

    @Test
    void shouldReturnCorrectPriceWhenFindingHouseById(){
        saveThreeHouses();

        assertEquals(500.0, houseBusiness.findHouseById("1").getPrice());
    }


    // *** updateHouse ** //

    @Test
    void updateHouseTest(){
        HouseMO house = houseMoForTest();

        houseMongoRepository.save(house);

        house.setTitle("House updated");

        houseBusiness.updateHouse(HouseMongoMapper.toDomain(house));

        assertEquals(houseMongoRepository.findById(house.getId()).get().getTitle(), "House updated");
    }
}
