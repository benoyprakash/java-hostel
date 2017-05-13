package com.hostel.service.mapper;

import com.hostel.domain.*;
import com.hostel.service.dto.RoomDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Room and its DTO RoomDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RoomMapper {

    RoomDTO roomToRoomDTO(Room room);

    List<RoomDTO> roomsToRoomDTOs(List<Room> rooms);

    Room roomDTOToRoom(RoomDTO roomDTO);

    List<Room> roomDTOsToRooms(List<RoomDTO> roomDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     

}
