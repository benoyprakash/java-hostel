package com.hostel.web.rest;

import com.hostel.HostelApp;

import com.hostel.domain.Room;
import com.hostel.repository.RoomRepository;
import com.hostel.service.RoomService;
import com.hostel.service.dto.RoomDTO;
import com.hostel.service.mapper.RoomMapper;
import com.hostel.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RoomResource REST controller.
 *
 * @see RoomResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HostelApp.class)
public class RoomResourceIntTest {

    private static final String DEFAULT_ROOM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ROOM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FLOOR = "AAAAAAAAAA";
    private static final String UPDATED_FLOOR = "BBBBBBBBBB";

    private static final String DEFAULT_BUILDING = "AAAAAAAAAA";
    private static final String UPDATED_BUILDING = "BBBBBBBBBB";

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private RoomService roomService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restRoomMockMvc;

    private Room room;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RoomResource roomResource = new RoomResource(roomService);
        this.restRoomMockMvc = MockMvcBuilders.standaloneSetup(roomResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Room createEntity() {
        Room room = new Room()
            .roomName(DEFAULT_ROOM_NAME)
            .floor(DEFAULT_FLOOR)
            .building(DEFAULT_BUILDING);
        return room;
    }

    @Before
    public void initTest() {
        roomRepository.deleteAll();
        room = createEntity();
    }

    @Test
    public void createRoom() throws Exception {
        int databaseSizeBeforeCreate = roomRepository.findAll().size();

        // Create the Room
        RoomDTO roomDTO = roomMapper.roomToRoomDTO(room);
        restRoomMockMvc.perform(post("/api/rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomDTO)))
            .andExpect(status().isCreated());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeCreate + 1);
        Room testRoom = roomList.get(roomList.size() - 1);
        assertThat(testRoom.getRoomName()).isEqualTo(DEFAULT_ROOM_NAME);
        assertThat(testRoom.getFloor()).isEqualTo(DEFAULT_FLOOR);
        assertThat(testRoom.getBuilding()).isEqualTo(DEFAULT_BUILDING);
    }

    @Test
    public void createRoomWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = roomRepository.findAll().size();

        // Create the Room with an existing ID
        room.setId("existing_id");
        RoomDTO roomDTO = roomMapper.roomToRoomDTO(room);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoomMockMvc.perform(post("/api/rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllRooms() throws Exception {
        // Initialize the database
        roomRepository.save(room);

        // Get all the roomList
        restRoomMockMvc.perform(get("/api/rooms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(room.getId())))
            .andExpect(jsonPath("$.[*].roomName").value(hasItem(DEFAULT_ROOM_NAME.toString())))
            .andExpect(jsonPath("$.[*].floor").value(hasItem(DEFAULT_FLOOR.toString())))
            .andExpect(jsonPath("$.[*].building").value(hasItem(DEFAULT_BUILDING.toString())));
    }

    @Test
    public void getRoom() throws Exception {
        // Initialize the database
        roomRepository.save(room);

        // Get the room
        restRoomMockMvc.perform(get("/api/rooms/{id}", room.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(room.getId()))
            .andExpect(jsonPath("$.roomName").value(DEFAULT_ROOM_NAME.toString()))
            .andExpect(jsonPath("$.floor").value(DEFAULT_FLOOR.toString()))
            .andExpect(jsonPath("$.building").value(DEFAULT_BUILDING.toString()));
    }

    @Test
    public void getNonExistingRoom() throws Exception {
        // Get the room
        restRoomMockMvc.perform(get("/api/rooms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateRoom() throws Exception {
        // Initialize the database
        roomRepository.save(room);
        int databaseSizeBeforeUpdate = roomRepository.findAll().size();

        // Update the room
        Room updatedRoom = roomRepository.findOne(room.getId());
        updatedRoom
            .roomName(UPDATED_ROOM_NAME)
            .floor(UPDATED_FLOOR)
            .building(UPDATED_BUILDING);
        RoomDTO roomDTO = roomMapper.roomToRoomDTO(updatedRoom);

        restRoomMockMvc.perform(put("/api/rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomDTO)))
            .andExpect(status().isOk());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate);
        Room testRoom = roomList.get(roomList.size() - 1);
        assertThat(testRoom.getRoomName()).isEqualTo(UPDATED_ROOM_NAME);
        assertThat(testRoom.getFloor()).isEqualTo(UPDATED_FLOOR);
        assertThat(testRoom.getBuilding()).isEqualTo(UPDATED_BUILDING);
    }

    @Test
    public void updateNonExistingRoom() throws Exception {
        int databaseSizeBeforeUpdate = roomRepository.findAll().size();

        // Create the Room
        RoomDTO roomDTO = roomMapper.roomToRoomDTO(room);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRoomMockMvc.perform(put("/api/rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomDTO)))
            .andExpect(status().isCreated());

        // Validate the Room in the database
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteRoom() throws Exception {
        // Initialize the database
        roomRepository.save(room);
        int databaseSizeBeforeDelete = roomRepository.findAll().size();

        // Get the room
        restRoomMockMvc.perform(delete("/api/rooms/{id}", room.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Room> roomList = roomRepository.findAll();
        assertThat(roomList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Room.class);
    }
}
