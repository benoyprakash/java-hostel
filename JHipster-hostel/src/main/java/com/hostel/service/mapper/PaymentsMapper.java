package com.hostel.service.mapper;

import com.hostel.domain.*;
import com.hostel.service.dto.PaymentsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Payments and its DTO PaymentsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaymentsMapper extends EntityMapper <PaymentsDTO, Payments> {
    
    

}
