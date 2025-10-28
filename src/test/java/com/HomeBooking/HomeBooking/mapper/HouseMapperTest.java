package com.HomeBooking.HomeBooking.mapper;

import com.HomeBooking.HomeBooking.BO.HouseBO;
import com.HomeBooking.HomeBooking.DTO.HouseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Profile("test")
@ActiveProfiles("test")
public class HouseMapperTest {

    @Test
    void toDTO_shouldMapAllFieldsCorrectly() {
        HouseBO bo = new HouseBO();
        bo.setId("1");
        bo.setTitle("Château de la Cité");
        bo.setAddress("Rue des Acacias, 75010 Paris");
        bo.setPrice(249000000.0);

        HouseDTO dto = HouseDTOMapper.toDTO(bo);

        assertNotNull(dto, "Le DTO ne doit pas être null");
        assertEquals("1", dto.getId());
        assertEquals("Château de la Cité", dto.getTitle());
        assertEquals("Rue des Acacias, 75010 Paris", dto.getAddress());
        assertEquals(249000000.0, dto.getPrice());
    }

    @Test
    void toDTO_shouldReturnNullWhenInputIsNull() {
        assertNull(HouseDTOMapper.toDTO(null), "Le mapper doit retourner null si l'entrée est null");
    }
}
