package com.hostel.service.impl;

import com.hostel.security.SecurityUtils;
import com.hostel.service.LocationService;
import com.hostel.domain.Location;
import com.hostel.repository.LocationRepository;
import com.hostel.service.UserService;
import com.hostel.service.dto.LocationDTO;
import com.hostel.service.mapper.LocationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Location.
 */
@Service
public class LocationServiceImpl implements LocationService{

    private final Logger log = LoggerFactory.getLogger(LocationServiceImpl.class);

    private final LocationRepository locationRepository;

    private final LocationMapper locationMapper;

    @Autowired
    private UserService userService;

    public LocationServiceImpl(LocationRepository locationRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    /**
     * Save a location.
     *
     * @param locationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LocationDTO save(LocationDTO locationDTO) {
        log.debug("Request to save Location : {}", locationDTO);
        Location location = locationMapper.locationDTOToLocation(locationDTO);
        location = locationRepository.save(location);
        LocationDTO result = locationMapper.locationToLocationDTO(location);
        return result;
    }

    /**
     *  Get all the locations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<LocationDTO> findAll(Pageable pageable, String clientId) {
        log.debug("Request to get all Locations");
        Page<Location> result = locationRepository.findByClient(pageable, clientId);
        return result.map(location -> locationMapper.locationToLocationDTO(location));
    }

    /**
     *  Get one location by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public LocationDTO findOne(String id) {
        log.debug("Request to get Location : {}", id);
        Location location = locationRepository.findOne(id);
        LocationDTO locationDTO = locationMapper.locationToLocationDTO(location);
        return locationDTO;
    }

    @Override
    public  LocationDTO findLocation(String clientId, String locationId) {
        log.debug("Request to get Location : {}", locationId);
        Location location = locationRepository.findByClientAndId(clientId, locationId);
        LocationDTO locationDTO = locationMapper.locationToLocationDTO(location);
        return locationDTO;
    }

    /**
     *  Delete the  location by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Location : {}", id);
        locationRepository.delete(id);
    }
}
