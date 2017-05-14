package com.hostel.service.mapper;

import com.hostel.domain.*;
import com.hostel.service.dto.RoomAllocationDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity RoomAllocation and its DTO RoomAllocationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RoomAllocationMapper {

    RoomAllocationDTO roomAllocationToRoomAllocationDTO(RoomAllocation roomAllocation);

    List<RoomAllocationDTO> roomAllocationsToRoomAllocationDTOs(List<RoomAllocation> roomAllocations);

    RoomAllocation roomAllocationDTOToRoomAllocation(RoomAllocationDTO roomAllocationDTO);

    List<RoomAllocation> roomAllocationDTOsToRoomAllocations(List<RoomAllocationDTO> roomAllocationDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     

}
