package com.hostel.service.impl;

import com.hostel.service.BuildingService;
import com.hostel.domain.Building;
import com.hostel.repository.BuildingRepository;
import com.hostel.service.dto.BuildingDTO;
import com.hostel.service.mapper.BuildingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Building.
 */
@Service
public class BuildingServiceImpl implements BuildingService{

    private final Logger log = LoggerFactory.getLogger(BuildingServiceImpl.class);
    
    private final BuildingRepository buildingRepository;

    private final BuildingMapper buildingMapper;

    public BuildingServiceImpl(BuildingRepository buildingRepository, BuildingMapper buildingMapper) {
        this.buildingRepository = buildingRepository;
        this.buildingMapper = buildingMapper;
    }

    /**
     * Save a building.
     *
     * @param buildingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BuildingDTO save(BuildingDTO buildingDTO) {
        log.debug("Request to save Building : {}", buildingDTO);
        Building building = buildingMapper.buildingDTOToBuilding(buildingDTO);
        building = buildingRepository.save(building);
        BuildingDTO result = buildingMapper.buildingToBuildingDTO(building);
        return result;
    }

    /**
     *  Get all the buildings.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<BuildingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Buildings");
        Page<Building> result = buildingRepository.findAll(pageable);
        return result.map(building -> buildingMapper.buildingToBuildingDTO(building));
    }

    /**
     *  Get one building by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public BuildingDTO findOne(String id) {
        log.debug("Request to get Building : {}", id);
        Building building = buildingRepository.findOne(id);
        BuildingDTO buildingDTO = buildingMapper.buildingToBuildingDTO(building);
        return buildingDTO;
    }

    /**
     *  Delete the  building by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Building : {}", id);
        buildingRepository.delete(id);
    }
}
