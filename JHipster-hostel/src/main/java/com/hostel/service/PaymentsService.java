package com.hostel.service;

import com.hostel.service.dto.PaymentsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Payments.
 */
public interface PaymentsService {

    /**
     * Save a payments.
     *
     * @param paymentsDTO the entity to save
     * @return the persisted entity
     */
    PaymentsDTO save(PaymentsDTO paymentsDTO);

    /**
     *  Get all the payments.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PaymentsDTO> findAll(Pageable pageable);

    Page<PaymentsDTO> findAllByBuilding(Pageable pageable, String buildingId);

    /**
     *  Get the "id" payments.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PaymentsDTO findOne(String id);

    /**
     *  Delete the "id" payments.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
