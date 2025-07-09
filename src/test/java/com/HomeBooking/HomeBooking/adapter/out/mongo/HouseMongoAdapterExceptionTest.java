package com.HomeBooking.HomeBooking.adapter.out.mongo;

import com.HomeBooking.HomeBooking.domain.exception.TechnicalDatabaseException;
import com.mongodb.MongoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HouseMongoAdapterExceptionTest {


    @Mock
    private HouseMongoRepository houseMongoRepository_with_mock;

    @InjectMocks
    private HouseMongoAdapter houseMongoAdapter_with_mock;

    // *** findHouses ** //

    @Test
    void shouldReturnExceptionWhenUseFindHouseWhenDataBaseDisconnected(){
        when(houseMongoRepository_with_mock.findAll()).thenThrow(new MongoException("Mongo DB down"));


        TechnicalDatabaseException thrown = assertThrows(
                TechnicalDatabaseException.class,
                () -> houseMongoAdapter_with_mock.findHouses()
        );

        assertTrue(thrown.getMessage().contains("Technical error while retrieving houses"));
        assertTrue(thrown.getCause() instanceof  MongoException);
    }

    // *** deleteHouseById *** //

    @Test
    void shouldReturnExceptionWhenUseDeleteHouseByIdWhenDataBaseDisconnected() {
        doThrow(new MongoException("Mongo DB down"))
                .when(houseMongoRepository_with_mock)
                .deleteById(1L);

        TechnicalDatabaseException thrown = assertThrows(
                TechnicalDatabaseException.class,
                () -> houseMongoAdapter_with_mock.deleteHouseById(1L)
        );

        assertTrue(thrown.getMessage().contains("Technical error while deleting house"));
        assertTrue(thrown.getCause() instanceof MongoException);
    }

    // *** findHouseById *** //

    @Test
    void shouldReturnExceptionWhenUseFindHouseByIdWhenDataBaseDisconnected(){
        when(houseMongoRepository_with_mock.findById(1L)).thenThrow(new MongoException("Mongo DB down"));


        TechnicalDatabaseException thrown = assertThrows(
                TechnicalDatabaseException.class,
                () -> houseMongoAdapter_with_mock.findHouseById(1L)
        );

        assertTrue(thrown.getMessage().contains("Technical error while retrieving house with id : 1"));
        assertTrue(thrown.getCause() instanceof  MongoException);
    }


}
