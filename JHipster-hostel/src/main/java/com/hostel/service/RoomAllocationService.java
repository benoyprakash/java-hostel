package com.hostel.service;

import com.hostel.domain.enumeration.RoomStatus;
import com.hostel.service.dto.RoomAllocationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing RoomAllocation.
 */
public interface RoomAllocationService {

    /**
     * Save a roomAllocation.
     *
     * @param roomAllocationDTO the entity to save
     * @return the persisted entity
     */
    RoomAllocationDTO save(RoomAllocationDTO roomAllocationDTO);

    /**
     *  Get all the roomAllocations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<RoomAllocationDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" roomAllocation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RoomAllocationDTO findOne(String id);

    /**
     *  Delete the "id" roomAllocation.
     *
     *  @param id the id of the entity
     */
    void delete(String id);

    Page<RoomAllocationDTO> findRoomAllocationsByBuilding(Pageable pageable, String buildingId);

    Page<RoomAllocationDTO> findRoomAllocationsByBuildingAndStatus(Pageable pageable, String buildingId, RoomStatus currStatus);
}
