package com.hostel.service.mapper;

import com.hostel.domain.*;
import com.hostel.service.dto.LocationDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Location and its DTO LocationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LocationMapper {

    LocationDTO locationToLocationDTO(Location location);

    List<LocationDTO> locationsToLocationDTOs(List<Location> locations);

    Location locationDTOToLocation(LocationDTO locationDTO);

    List<Location> locationDTOsToLocations(List<LocationDTO> locationDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     

}
