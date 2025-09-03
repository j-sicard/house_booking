package com.HomeBooking.HomeBooking.controller;

import com.HomeBooking.HomeBooking.BO.HouseBO;
import com.HomeBooking.HomeBooking.DTO.HouseDTO;
import com.HomeBooking.HomeBooking.FO.HouseFO;
import com.HomeBooking.HomeBooking.business.HouseBusiness;
import com.HomeBooking.HomeBooking.utils.HouseDTOMapper;
import com.HomeBooking.HomeBooking.utils.HouseFormMapper;
import com.HomeBooking.HomeBooking.utils.ResponseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/House")
public class HouseController {

    private final HouseBusiness houseBusiness;

    public HouseController(HouseBusiness houseBusiness){
        this.houseBusiness = houseBusiness;
    }

    private static final Logger logger = LoggerFactory.getLogger(HouseController.class);

    /**
     * Endpoint to create a house
     * @param house the house object to create
     * @return the created house in the body of the response
     */
    @PostMapping(name = "/create")
    public ResponseEntity<?> createHouse(@RequestBody HouseFO house) {
        try {
            // Step 1: Map the form object (FO) to business object (BO)
            HouseBO houseBO = HouseFormMapper.toBusiness(house);

            // Step 2: Create the house in the business layer
            HouseBO createdHouse = houseBusiness.createHouse(houseBO);

            // Step 3: Map the result to DTO
            HouseDTO houseDTO = HouseDTOMapper.toDTO(createdHouse);

            // Step 4: Return the created response
            return ResponseManager.created(houseDTO);
        } catch (Exception e) {
            logger.error("Error creating house", e);
            return ResponseManager.error(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error creating house : " + e.getMessage()
            );
        }
    }


    /**
     * Endpoint to retrieve all the houses
     * @return the list of houses
     */
    @GetMapping("find/all")
    public ResponseEntity<?> findHouses() {
        try {
            // Step 1: Retrieve business objects
            List<HouseBO> houses = houseBusiness.findHouses();

            // Step 2: Map to DTOs
            List<HouseDTO> houseDTOs = houses.stream().map(HouseDTOMapper::toDTO).toList();

            // Step 3: Return response
            return ResponseManager.success(houseDTOs);
        }catch (Exception e) {
            logger.error("Error retrieving houses", e);
            // Return an error response
            return ResponseManager.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving houses : " + e.getMessage());
        }
    }




}
