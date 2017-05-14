package com.hostel.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hostel.service.RoomAllocationService;
import com.hostel.web.rest.util.HeaderUtil;
import com.hostel.web.rest.util.PaginationUtil;
import com.hostel.service.dto.RoomAllocationDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing RoomAllocation.
 */
@RestController
@RequestMapping("/api")
public class RoomAllocationResource {

    private final Logger log = LoggerFactory.getLogger(RoomAllocationResource.class);

    private static final String ENTITY_NAME = "roomAllocation";
        
    private final RoomAllocationService roomAllocationService;

    public RoomAllocationResource(RoomAllocationService roomAllocationService) {
        this.roomAllocationService = roomAllocationService;
    }

    /**
     * POST  /room-allocations : Create a new roomAllocation.
     *
     * @param roomAllocationDTO the roomAllocationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new roomAllocationDTO, or with status 400 (Bad Request) if the roomAllocation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/room-allocations")
    @Timed
    public ResponseEntity<RoomAllocationDTO> createRoomAllocation(@Valid @RequestBody RoomAllocationDTO roomAllocationDTO) throws URISyntaxException {
        log.debug("REST request to save RoomAllocation : {}", roomAllocationDTO);
        if (roomAllocationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new roomAllocation cannot already have an ID")).body(null);
        }
        RoomAllocationDTO result = roomAllocationService.save(roomAllocationDTO);
        return ResponseEntity.created(new URI("/api/room-allocations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /room-allocations : Updates an existing roomAllocation.
     *
     * @param roomAllocationDTO the roomAllocationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated roomAllocationDTO,
     * or with status 400 (Bad Request) if the roomAllocationDTO is not valid,
     * or with status 500 (Internal Server Error) if the roomAllocationDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/room-allocations")
    @Timed
    public ResponseEntity<RoomAllocationDTO> updateRoomAllocation(@Valid @RequestBody RoomAllocationDTO roomAllocationDTO) throws URISyntaxException {
        log.debug("REST request to update RoomAllocation : {}", roomAllocationDTO);
        if (roomAllocationDTO.getId() == null) {
            return createRoomAllocation(roomAllocationDTO);
        }
        RoomAllocationDTO result = roomAllocationService.save(roomAllocationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, roomAllocationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /room-allocations : get all the roomAllocations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of roomAllocations in body
     */
    @GetMapping("/room-allocations")
    @Timed
    public ResponseEntity<List<RoomAllocationDTO>> getAllRoomAllocations(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of RoomAllocations");
        Page<RoomAllocationDTO> page = roomAllocationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/room-allocations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /room-allocations/:id : get the "id" roomAllocation.
     *
     * @param id the id of the roomAllocationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the roomAllocationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/room-allocations/{id}")
    @Timed
    public ResponseEntity<RoomAllocationDTO> getRoomAllocation(@PathVariable String id) {
        log.debug("REST request to get RoomAllocation : {}", id);
        RoomAllocationDTO roomAllocationDTO = roomAllocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(roomAllocationDTO));
    }

    /**
     * DELETE  /room-allocations/:id : delete the "id" roomAllocation.
     *
     * @param id the id of the roomAllocationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/room-allocations/{id}")
    @Timed
    public ResponseEntity<Void> deleteRoomAllocation(@PathVariable String id) {
        log.debug("REST request to delete RoomAllocation : {}", id);
        roomAllocationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
