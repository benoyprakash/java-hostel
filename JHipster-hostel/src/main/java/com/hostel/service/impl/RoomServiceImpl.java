package com.hostel.service.impl;

import com.hostel.service.RoomService;
import com.hostel.domain.Room;
import com.hostel.repository.RoomRepository;
import com.hostel.service.dto.RoomDTO;
import com.hostel.service.mapper.RoomMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Room.
 */
@Service
public class RoomServiceImpl implements RoomService{

    private final Logger log = LoggerFactory.getLogger(RoomServiceImpl.class);
    
    private final RoomRepository roomRepository;

    private final RoomMapper roomMapper;

    public RoomServiceImpl(RoomRepository roomRepository, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    /**
     * Save a room.
     *
     * @param roomDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RoomDTO save(RoomDTO roomDTO) {
        log.debug("Request to save Room : {}", roomDTO);
        Room room = roomMapper.roomDTOToRoom(roomDTO);
        room = roomRepository.save(room);
        RoomDTO result = roomMapper.roomToRoomDTO(room);
        return result;
    }

    /**
     *  Get all the rooms.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<RoomDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Rooms");
        Page<Room> result = roomRepository.findAll(pageable);
        return result.map(room -> roomMapper.roomToRoomDTO(room));
    }

    /**
     *  Get one room by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public RoomDTO findOne(String id) {
        log.debug("Request to get Room : {}", id);
        Room room = roomRepository.findOne(id);
        RoomDTO roomDTO = roomMapper.roomToRoomDTO(room);
        return roomDTO;
    }

    /**
     *  Delete the  room by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Room : {}", id);
        roomRepository.delete(id);
    }
}
