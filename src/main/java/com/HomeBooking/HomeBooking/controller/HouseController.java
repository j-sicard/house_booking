package com.HomeBooking.HomeBooking.controller;

import com.HomeBooking.HomeBooking.FO.HouseFO;
import com.HomeBooking.HomeBooking.business.HouseBusiness;
import com.HomeBooking.HomeBooking.mapper.HouseDTOMapper;
import com.HomeBooking.HomeBooking.mapper.HouseFormMapper;
import com.HomeBooking.HomeBooking.mapper.ResponseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return ResponseManager.created(HouseDTOMapper.toDTO(houseBusiness.createHouse(HouseFormMapper.toBusiness(house))));

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
    @GetMapping("/find/all")
    public ResponseEntity<?> findHouses() {
        try {
            return ResponseManager.success(houseBusiness.findHouses().stream().map(HouseDTOMapper::toDTO).toList());

        }catch (Exception e) {
            logger.error("Error retrieving houses", e);
            // Return an error response
            return ResponseManager.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving houses : " + e.getMessage());
        }
    }

    /**
     * This method is used to find a house by its id. It retrieves the business object, maps it to a DTO and returns a success response with the DTO.
     * If an error occurs during this process, it logs the error and returns an error response.
     *
     * @param id The id of the house to be found.
     * @return A ResponseEntity containing either a success or error response.
     */
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findHouseById(@PathVariable String id) {
        try {
            return  ResponseManager.success(HouseDTOMapper.toDTO(houseBusiness.findHouseById(id)));

        }catch (Exception e) {
            logger.error("Error retrieving house", e);
            // Return an error response
            return ResponseManager.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving house : " + e.getMessage());
        }
    }


    /**
     * Endpoint to delete a house by its id
     * @param id the id of the house to delete
     * @return a success message if the house was deleted successfully
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHouse(@PathVariable String id) {
        try  {
            houseBusiness.deleteHouseById(id);

            return ResponseManager.success("House deleted successfully");
        } catch (Exception e) {
            logger.error("Error deleting house", e);

            return ResponseManager.error(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting house : " + e.getMessage());
        }
    }





}
