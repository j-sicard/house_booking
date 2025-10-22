package com.HomeBooking.HomeBooking.Mapper;

import com.HomeBooking.HomeBooking.BO.HouseBO;
import com.HomeBooking.HomeBooking.exceptions.InvalidHouseException;
import org.springframework.stereotype.Component;

@Component
public class HouseValidator {

    private HouseValidator() {
    }
    public static void validate(HouseBO house) {
        if (house == null) {
            throw new InvalidHouseException("The house cannot be zero.");
        }
        if (house.getTitle() == null || house.getTitle().isBlank()) {
            throw new InvalidHouseException("the Title is required.");
        }
        if (house.getAddress() == null || house.getAddress().isBlank()) {
            throw new InvalidHouseException("Address is required.");
        }
        if (house.getPrice() == null || house.getPrice() <= 0) {
            throw new InvalidHouseException("Price must be > 0.");
        }
    }
}
