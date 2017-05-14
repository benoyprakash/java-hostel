package com.hostel.web.rest;

import com.hostel.HostelApp;

import com.hostel.domain.RoomAllocation;
import com.hostel.repository.RoomAllocationRepository;
import com.hostel.service.RoomAllocationService;
import com.hostel.service.dto.RoomAllocationDTO;
import com.hostel.service.mapper.RoomAllocationMapper;
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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hostel.domain.enumeration.RoomStatus;
/**
 * Test class for the RoomAllocationResource REST controller.
 *
 * @see RoomAllocationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HostelApp.class)
public class RoomAllocationResourceIntTest {

    private static final String DEFAULT_ROOM_ID = "AAAAAAAAAA";
    private static final String UPDATED_ROOM_ID = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final RoomStatus DEFAULT_CURR_STATUS = RoomStatus.ACTIVE;
    private static final RoomStatus UPDATED_CURR_STATUS = RoomStatus.INACTIVE;

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_DATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_DATE_TIME = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private RoomAllocationRepository roomAllocationRepository;

    @Autowired
    private RoomAllocationMapper roomAllocationMapper;

    @Autowired
    private RoomAllocationService roomAllocationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restRoomAllocationMockMvc;

    private RoomAllocation roomAllocation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RoomAllocationResource roomAllocationResource = new RoomAllocationResource(roomAllocationService);
        this.restRoomAllocationMockMvc = MockMvcBuilders.standaloneSetup(roomAllocationResource)
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
    public static RoomAllocation createEntity() {
        RoomAllocation roomAllocation = new RoomAllocation()
            .roomId(DEFAULT_ROOM_ID)
            .userId(DEFAULT_USER_ID)
            .fromDate(DEFAULT_FROM_DATE)
            .toDate(DEFAULT_TO_DATE)
            .currStatus(DEFAULT_CURR_STATUS)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedDateTime(DEFAULT_UPDATED_DATE_TIME);
        return roomAllocation;
    }

    @Before
    public void initTest() {
        roomAllocationRepository.deleteAll();
        roomAllocation = createEntity();
    }

    @Test
    public void createRoomAllocation() throws Exception {
        int databaseSizeBeforeCreate = roomAllocationRepository.findAll().size();

        // Create the RoomAllocation
        RoomAllocationDTO roomAllocationDTO = roomAllocationMapper.roomAllocationToRoomAllocationDTO(roomAllocation);
        restRoomAllocationMockMvc.perform(post("/api/room-allocations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomAllocationDTO)))
            .andExpect(status().isCreated());

        // Validate the RoomAllocation in the database
        List<RoomAllocation> roomAllocationList = roomAllocationRepository.findAll();
        assertThat(roomAllocationList).hasSize(databaseSizeBeforeCreate + 1);
        RoomAllocation testRoomAllocation = roomAllocationList.get(roomAllocationList.size() - 1);
        assertThat(testRoomAllocation.getRoomId()).isEqualTo(DEFAULT_ROOM_ID);
        assertThat(testRoomAllocation.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testRoomAllocation.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testRoomAllocation.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testRoomAllocation.getCurrStatus()).isEqualTo(DEFAULT_CURR_STATUS);
        assertThat(testRoomAllocation.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testRoomAllocation.getUpdatedDateTime()).isEqualTo(DEFAULT_UPDATED_DATE_TIME);
    }

    @Test
    public void createRoomAllocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = roomAllocationRepository.findAll().size();

        // Create the RoomAllocation with an existing ID
        roomAllocation.setId("existing_id");
        RoomAllocationDTO roomAllocationDTO = roomAllocationMapper.roomAllocationToRoomAllocationDTO(roomAllocation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoomAllocationMockMvc.perform(post("/api/room-allocations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomAllocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RoomAllocation> roomAllocationList = roomAllocationRepository.findAll();
        assertThat(roomAllocationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkRoomIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomAllocationRepository.findAll().size();
        // set the field null
        roomAllocation.setRoomId(null);

        // Create the RoomAllocation, which fails.
        RoomAllocationDTO roomAllocationDTO = roomAllocationMapper.roomAllocationToRoomAllocationDTO(roomAllocation);

        restRoomAllocationMockMvc.perform(post("/api/room-allocations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomAllocationDTO)))
            .andExpect(status().isBadRequest());

        List<RoomAllocation> roomAllocationList = roomAllocationRepository.findAll();
        assertThat(roomAllocationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkFromDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomAllocationRepository.findAll().size();
        // set the field null
        roomAllocation.setFromDate(null);

        // Create the RoomAllocation, which fails.
        RoomAllocationDTO roomAllocationDTO = roomAllocationMapper.roomAllocationToRoomAllocationDTO(roomAllocation);

        restRoomAllocationMockMvc.perform(post("/api/room-allocations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomAllocationDTO)))
            .andExpect(status().isBadRequest());

        List<RoomAllocation> roomAllocationList = roomAllocationRepository.findAll();
        assertThat(roomAllocationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkToDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomAllocationRepository.findAll().size();
        // set the field null
        roomAllocation.setToDate(null);

        // Create the RoomAllocation, which fails.
        RoomAllocationDTO roomAllocationDTO = roomAllocationMapper.roomAllocationToRoomAllocationDTO(roomAllocation);

        restRoomAllocationMockMvc.perform(post("/api/room-allocations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomAllocationDTO)))
            .andExpect(status().isBadRequest());

        List<RoomAllocation> roomAllocationList = roomAllocationRepository.findAll();
        assertThat(roomAllocationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCurrStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomAllocationRepository.findAll().size();
        // set the field null
        roomAllocation.setCurrStatus(null);

        // Create the RoomAllocation, which fails.
        RoomAllocationDTO roomAllocationDTO = roomAllocationMapper.roomAllocationToRoomAllocationDTO(roomAllocation);

        restRoomAllocationMockMvc.perform(post("/api/room-allocations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomAllocationDTO)))
            .andExpect(status().isBadRequest());

        List<RoomAllocation> roomAllocationList = roomAllocationRepository.findAll();
        assertThat(roomAllocationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllRoomAllocations() throws Exception {
        // Initialize the database
        roomAllocationRepository.save(roomAllocation);

        // Get all the roomAllocationList
        restRoomAllocationMockMvc.perform(get("/api/room-allocations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roomAllocation.getId())))
            .andExpect(jsonPath("$.[*].roomId").value(hasItem(DEFAULT_ROOM_ID.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].currStatus").value(hasItem(DEFAULT_CURR_STATUS.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updatedDateTime").value(hasItem(DEFAULT_UPDATED_DATE_TIME.toString())));
    }

    @Test
    public void getRoomAllocation() throws Exception {
        // Initialize the database
        roomAllocationRepository.save(roomAllocation);

        // Get the roomAllocation
        restRoomAllocationMockMvc.perform(get("/api/room-allocations/{id}", roomAllocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(roomAllocation.getId()))
            .andExpect(jsonPath("$.roomId").value(DEFAULT_ROOM_ID.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.currStatus").value(DEFAULT_CURR_STATUS.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updatedDateTime").value(DEFAULT_UPDATED_DATE_TIME.toString()));
    }

    @Test
    public void getNonExistingRoomAllocation() throws Exception {
        // Get the roomAllocation
        restRoomAllocationMockMvc.perform(get("/api/room-allocations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateRoomAllocation() throws Exception {
        // Initialize the database
        roomAllocationRepository.save(roomAllocation);
        int databaseSizeBeforeUpdate = roomAllocationRepository.findAll().size();

        // Update the roomAllocation
        RoomAllocation updatedRoomAllocation = roomAllocationRepository.findOne(roomAllocation.getId());
        updatedRoomAllocation
            .roomId(UPDATED_ROOM_ID)
            .userId(UPDATED_USER_ID)
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .currStatus(UPDATED_CURR_STATUS)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedDateTime(UPDATED_UPDATED_DATE_TIME);
        RoomAllocationDTO roomAllocationDTO = roomAllocationMapper.roomAllocationToRoomAllocationDTO(updatedRoomAllocation);

        restRoomAllocationMockMvc.perform(put("/api/room-allocations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomAllocationDTO)))
            .andExpect(status().isOk());

        // Validate the RoomAllocation in the database
        List<RoomAllocation> roomAllocationList = roomAllocationRepository.findAll();
        assertThat(roomAllocationList).hasSize(databaseSizeBeforeUpdate);
        RoomAllocation testRoomAllocation = roomAllocationList.get(roomAllocationList.size() - 1);
        assertThat(testRoomAllocation.getRoomId()).isEqualTo(UPDATED_ROOM_ID);
        assertThat(testRoomAllocation.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testRoomAllocation.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testRoomAllocation.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testRoomAllocation.getCurrStatus()).isEqualTo(UPDATED_CURR_STATUS);
        assertThat(testRoomAllocation.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testRoomAllocation.getUpdatedDateTime()).isEqualTo(UPDATED_UPDATED_DATE_TIME);
    }

    @Test
    public void updateNonExistingRoomAllocation() throws Exception {
        int databaseSizeBeforeUpdate = roomAllocationRepository.findAll().size();

        // Create the RoomAllocation
        RoomAllocationDTO roomAllocationDTO = roomAllocationMapper.roomAllocationToRoomAllocationDTO(roomAllocation);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRoomAllocationMockMvc.perform(put("/api/room-allocations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomAllocationDTO)))
            .andExpect(status().isCreated());

        // Validate the RoomAllocation in the database
        List<RoomAllocation> roomAllocationList = roomAllocationRepository.findAll();
        assertThat(roomAllocationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteRoomAllocation() throws Exception {
        // Initialize the database
        roomAllocationRepository.save(roomAllocation);
        int databaseSizeBeforeDelete = roomAllocationRepository.findAll().size();

        // Get the roomAllocation
        restRoomAllocationMockMvc.perform(delete("/api/room-allocations/{id}", roomAllocation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RoomAllocation> roomAllocationList = roomAllocationRepository.findAll();
        assertThat(roomAllocationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoomAllocation.class);
    }
}
