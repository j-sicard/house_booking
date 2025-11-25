package com.HomeBooking.HomeBooking.business;

import com.HomeBooking.HomeBooking.BO.HouseBO;
import com.HomeBooking.HomeBooking.business.impl.HouseBusinessImpl;
import com.HomeBooking.HomeBooking.exceptions.TechnicalDatabaseException;
import com.HomeBooking.HomeBooking.service.impl.HouseMongoServiceImpl;
import com.mongodb.MongoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
public class HousebusinessExceptionTest {

    @Mock
    private HouseMongoServiceImpl houseMongoService_with_mock;

    @InjectMocks
    private HouseBusinessImpl houseBusiness_with_mock;

    // *** createHouse *** //

    @Test
    void shouldReturnExceptionWhenUseCreateHouseWhenDataBaseDisconnected() {
        HouseBO house = new HouseBO("1", "Titre", "Adresse", 100.0);

        when(houseMongoService_with_mock.saveHouse(any(HouseBO.class)))
                .thenThrow(new MongoException("MongoDB down"));

        TechnicalDatabaseException thrown = assertThrows(
                TechnicalDatabaseException.class,
                () -> houseBusiness_with_mock.createHouse(house)
        );

        assertTrue(thrown.getMessage().contains("Technical error while registering the house"));
        assertTrue(thrown.getCause() instanceof MongoException);
    }


    // *** findHouses ** //

    @Test
    void shouldReturnExceptionWhenUseFindHouseWhenDataBaseDisconnected(){
        when(houseMongoService_with_mock.findHouses()).thenThrow(new MongoException("Mongo DB down"));


        TechnicalDatabaseException thrown = assertThrows(
                TechnicalDatabaseException.class,
                () -> houseBusiness_with_mock.findHouses()
        );

        assertTrue(thrown.getMessage().contains("Technical error while retrieving houses"));
        assertTrue(thrown.getCause() instanceof  MongoException);
    }

    // *** deleteHouseById *** //

    @Test
    void shouldReturnExceptionWhenUseDeleteHouseByIdWhenDataBaseDisconnected() {
        when(houseMongoService_with_mock.houseExistedById("1")).thenReturn(true);

        doThrow(new MongoException("Mongo DB down"))
                .when(houseMongoService_with_mock)
                .deleteHouseById("1");

        TechnicalDatabaseException thrown = assertThrows(
                TechnicalDatabaseException.class,
                () -> houseBusiness_with_mock.deleteHouseById("1")
        );

        assertTrue(thrown.getMessage().contains("MongoDB Technical Error"));
        assertTrue(thrown.getCause() instanceof MongoException);
    }

    // *** findHouseById *** //

    @Test
    void shouldReturnExceptionWhenUseFindHouseByIdWhenDataBaseDisconnected() {
        when(houseMongoService_with_mock.findHouseById("1"))
                .thenThrow(new MongoException("Mongo DB down"));

        TechnicalDatabaseException thrown = assertThrows(
                TechnicalDatabaseException.class,
                () -> houseBusiness_with_mock.findHouseById("1")
        );

        assertTrue(thrown.getMessage().contains("Technical error while retrieving house with id: 1"));
        assertTrue(thrown.getCause() instanceof MongoException);
    }


    // *** updateHouse *** //

    @Test
    void shouldThrowTechnicalDatabaseExceptionWhenMongoExceptionOccursDuringUpdate() {
        // GIVEN
        HouseBO house = new HouseBO("1", "Titre", "Adresse", 100.0);
        when(houseMongoService_with_mock.findHouseById(house.getId()))
                .thenReturn(Optional.of(house));

        // Simuler l'erreur MongoDB
        doThrow(new MongoException("Mongo error"))
                .when(houseMongoService_with_mock)
                .saveHouse(any(HouseBO.class));

        // WHEN + THEN
        TechnicalDatabaseException thrown = assertThrows(
                TechnicalDatabaseException.class,
                () -> houseBusiness_with_mock.updateHouse(house)
        );

        assertTrue(thrown.getMessage().contains("MongoDB Technical Error"));
    }


}
