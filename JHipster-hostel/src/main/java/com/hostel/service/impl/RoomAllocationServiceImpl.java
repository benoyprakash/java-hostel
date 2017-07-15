package com.hostel.service.impl;

import com.hostel.domain.Room;
import com.hostel.domain.User;
import com.hostel.domain.enumeration.RoomStatus;
import com.hostel.service.RoomAllocationService;
import com.hostel.domain.RoomAllocation;
import com.hostel.repository.RoomAllocationRepository;
import com.hostel.service.RoomService;
import com.hostel.service.UserService;
import com.hostel.service.dto.RoomAllocationDTO;
import com.hostel.service.mapper.RoomAllocationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing RoomAllocation.
 */
@Service
public class RoomAllocationServiceImpl implements RoomAllocationService{

    private final Logger log = LoggerFactory.getLogger(RoomAllocationServiceImpl.class);

    private final RoomAllocationRepository roomAllocationRepository;

    private final RoomAllocationMapper roomAllocationMapper;

    private final UserService userService;

    private final RoomService roomService;

    public RoomAllocationServiceImpl(RoomAllocationRepository roomAllocationRepository, RoomAllocationMapper roomAllocationMapper, UserService userService, RoomService roomService) {
        this.roomAllocationRepository = roomAllocationRepository;
        this.roomAllocationMapper = roomAllocationMapper;
        this.userService = userService;
        this.roomService = roomService;
    }

    /**
     * Save a roomAllocation.
     *
     * @param roomAllocationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RoomAllocationDTO save(RoomAllocationDTO roomAllocationDTO) {
        log.debug("Request to save RoomAllocation : {}", roomAllocationDTO);
        RoomAllocation roomAllocation = roomAllocationMapper.roomAllocationDTOToRoomAllocation(roomAllocationDTO);
        roomAllocation.setBuildingId(roomAllocationDTO.getBuildingId());
        roomAllocation = roomAllocationRepository.save(roomAllocation);
        return roomAllocationMapper.roomAllocationToRoomAllocationDTO(roomAllocation);
    }

    /**
     *  Get all the roomAllocations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<RoomAllocationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RoomAllocations");
        Page<RoomAllocation> result = roomAllocationRepository.findAll(pageable);
        return result.map(roomAllocation -> roomAllocationMapper.roomAllocationToRoomAllocationDTO(roomAllocation));
    }

    /**
     *  Get one roomAllocation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public RoomAllocationDTO findOne(String id) {
        log.debug("Request to get RoomAllocation : {}", id);
        RoomAllocation roomAllocation = roomAllocationRepository.findOne(id);
        RoomAllocationDTO roomAllocationDTO = roomAllocationMapper.roomAllocationToRoomAllocationDTO(roomAllocation);

        User user = userService.getUserById(roomAllocationDTO.getUserId());
        roomAllocationDTO.setUserName(user.getLastName());
        Room room = roomService.findRoom(roomAllocationDTO.getRoomId());
        roomAllocationDTO.setRoomName(room.getRoomName());

        return roomAllocationDTO;
    }

    /**
     *  Delete the  roomAllocation by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete RoomAllocation : {}", id);
        roomAllocationRepository.delete(id);
    }

    @Override
    public Page<RoomAllocationDTO> findRoomAllocationsByBuilding(Pageable pageable, String buildingId) {
        log.debug("Request to get all RoomAllocations of ");
        Page<RoomAllocation> result = roomAllocationRepository.findByBuildingId(pageable, buildingId);
        Page<RoomAllocationDTO> results = result.map(roomAllocation -> roomAllocationMapper.roomAllocationToRoomAllocationDTO(roomAllocation));

        for(RoomAllocationDTO data : results.getContent()){
            User user = userService.getUserById(data.getUserId());
            data.setUserName(user.getLastName());
            Room room = roomService.findRoom(data.getRoomId());
            data.setRoomName(room.getRoomName());
        }

        return results;
    }

    @Override
    public Page<RoomAllocationDTO> findRoomAllocationsByBuildingAndStatus(Pageable pageable, String buildingId, RoomStatus currStatus) {
        log.debug("Request to get all RoomAllocations of ");
        Page<RoomAllocation> result = roomAllocationRepository.findByBuildingIdAndCurrStatus(pageable, buildingId, currStatus);
        Page<RoomAllocationDTO> results = result.map(roomAllocation -> roomAllocationMapper.roomAllocationToRoomAllocationDTO(roomAllocation));

        for(RoomAllocationDTO data : results.getContent()){
            User user = userService.getUserById(data.getUserId());
            data.setUserName(user.getLastName());
            Room room = roomService.findRoom(data.getRoomId());
            data.setRoomName(room.getRoomName());
        }

        return results;
    }
}
