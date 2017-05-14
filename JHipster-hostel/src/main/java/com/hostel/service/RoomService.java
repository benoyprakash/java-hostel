package com.hostel.service;

import com.hostel.service.dto.RoomDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Room.
 */
public interface RoomService {

    /**
     * Save a room.
     *
     * @param roomDTO the entity to save
     * @return the persisted entity
     */
    RoomDTO save(RoomDTO roomDTO);

    /**
     *  Get all the rooms.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RoomDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" room.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RoomDTO findOne(String id);

    /**
     *  Delete the "id" room.
     *
     *  @param id the id of the entity
     */
    void delete(String id);

    Page<RoomDTO> findByBuilding(Pageable pageable, String buildingId);
}
