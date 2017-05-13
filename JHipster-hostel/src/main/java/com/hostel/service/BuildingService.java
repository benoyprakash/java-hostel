package com.hostel.service;

import com.hostel.service.dto.BuildingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Building.
 */
public interface BuildingService {

    /**
     * Save a building.
     *
     * @param buildingDTO the entity to save
     * @return the persisted entity
     */
    BuildingDTO save(BuildingDTO buildingDTO);

    /**
     *  Get all the buildings.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<BuildingDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" building.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BuildingDTO findOne(String id);

    /**
     *  Delete the "id" building.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
