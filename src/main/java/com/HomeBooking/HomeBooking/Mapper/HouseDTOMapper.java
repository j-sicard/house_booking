package com.HomeBooking.HomeBooking.Mapper;

import com.HomeBooking.HomeBooking.BO.HouseBO;
import com.HomeBooking.HomeBooking.DTO.HouseDTO;

public class HouseDTOMapper {

    // BO -> DTO
    public static HouseDTO toDTO(HouseBO bo) {
        if (bo == null) return null;

        HouseDTO dto = new HouseDTO();
        dto.setId(bo.getId());
        dto.setTitle(bo.getTitle());
        dto.setAddress(bo.getAddress());
        dto.setPrice(bo.getPrice());

        return dto;
    }

    // DTO -> BO
    public static HouseBO toBO(HouseDTO dto) {
        if (dto == null) return null;

        HouseBO bo = new HouseBO();
        bo.setId(dto.getId());
        bo.setTitle(dto.getTitle());
        bo.setAddress(dto.getAddress());
        bo.setPrice(dto.getPrice());

        return bo;
    }
}
