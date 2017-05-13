package com.hostel.service.mapper;

import com.hostel.domain.*;
import com.hostel.service.dto.BuildingDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Building and its DTO BuildingDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BuildingMapper {

    BuildingDTO buildingToBuildingDTO(Building building);

    List<BuildingDTO> buildingsToBuildingDTOs(List<Building> buildings);

    Building buildingDTOToBuilding(BuildingDTO buildingDTO);

    List<Building> buildingDTOsToBuildings(List<BuildingDTO> buildingDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     

}
