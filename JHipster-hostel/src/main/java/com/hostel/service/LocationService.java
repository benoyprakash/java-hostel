package com.hostel.service;

import com.hostel.domain.Location;
import com.hostel.service.dto.LocationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Location.
 */
public interface LocationService {

    /**
     * Save a location.
     *
     * @param locationDTO the entity to save
     * @return the persisted entity
     */
    LocationDTO save(LocationDTO locationDTO);

    /**
     *  Get all the locations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LocationDTO> findAll(Pageable pageable, String clientId);

    /**
     *  Get the "id" location.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LocationDTO findOne(String id);

    /**
     *  Get the "id" location.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LocationDTO findLocation(String clientId, String locationId);

    /**
     *  Delete the "id" location.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
