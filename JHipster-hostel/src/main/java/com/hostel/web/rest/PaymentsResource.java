package com.hostel.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hostel.service.PaymentsService;
import com.hostel.web.rest.util.HeaderUtil;
import com.hostel.web.rest.util.PaginationUtil;
import com.hostel.service.dto.PaymentsDTO;
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

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Payments.
 */
@RestController
@RequestMapping("/api")
public class PaymentsResource {

    private final Logger log = LoggerFactory.getLogger(PaymentsResource.class);

    private static final String ENTITY_NAME = "payments";

    private final PaymentsService paymentsService;

    public PaymentsResource(PaymentsService paymentsService) {
        this.paymentsService = paymentsService;
    }

    /**
     * POST  /payments : Create a new payments.
     *
     * @param paymentsDTO the paymentsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paymentsDTO, or with status 400 (Bad Request) if the payments has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payments")
    @Timed
    public ResponseEntity<PaymentsDTO> createPayments(@Valid @RequestBody PaymentsDTO paymentsDTO) throws URISyntaxException {
        log.debug("REST request to save Payments : {}", paymentsDTO);
        if (paymentsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new payments cannot already have an ID")).body(null);
        }
        PaymentsDTO result = paymentsService.save(paymentsDTO);
        return ResponseEntity.created(new URI("/api/payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payments : Updates an existing payments.
     *
     * @param paymentsDTO the paymentsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paymentsDTO,
     * or with status 400 (Bad Request) if the paymentsDTO is not valid,
     * or with status 500 (Internal Server Error) if the paymentsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payments")
    @Timed
    public ResponseEntity<PaymentsDTO> updatePayments(@Valid @RequestBody PaymentsDTO paymentsDTO) throws URISyntaxException {
        log.debug("REST request to update Payments : {}", paymentsDTO);
        if (paymentsDTO.getId() == null) {
            return createPayments(paymentsDTO);
        }
        PaymentsDTO result = paymentsService.save(paymentsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paymentsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payments : get all the payments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of payments in body
     */
    @GetMapping("/payments")
    @Timed
    public ResponseEntity<List<PaymentsDTO>> getAllPayments(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Payments");
        Page<PaymentsDTO> page = paymentsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/payments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/payments/building/{buildingId}")
    @Timed
    public ResponseEntity<List<PaymentsDTO>> getAllPaymentsByBuilding(@ApiParam Pageable pageable, @PathVariable("buildingId") String buildingId) {
        log.debug("REST request to get a page of Payments");
        Page<PaymentsDTO> page = paymentsService.findAllByBuilding(pageable, buildingId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/payments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /payments/:id : get the "id" payments.
     *
     * @param id the id of the paymentsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paymentsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/payments/{id}")
    @Timed
    public ResponseEntity<PaymentsDTO> getPayments(@PathVariable String id) {
        log.debug("REST request to get Payments : {}", id);
        PaymentsDTO paymentsDTO = paymentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(paymentsDTO));
    }

    /**
     * DELETE  /payments/:id : delete the "id" payments.
     *
     * @param id the id of the paymentsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payments/{id}")
    @Timed
    public ResponseEntity<Void> deletePayments(@PathVariable String id) {
        log.debug("REST request to delete Payments : {}", id);
        paymentsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
