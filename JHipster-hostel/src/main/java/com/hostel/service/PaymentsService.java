package com.hostel.service;

import com.hostel.domain.Payments;
import com.hostel.domain.enumeration.PaymentAgainstType;
import com.hostel.domain.enumeration.PaymentStatus;
import com.hostel.service.dto.PaymentsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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

    Page<PaymentsDTO> findAllByBuildingAndDateFilter(Pageable pageable, String buildingId, LocalDate searchFromDate, LocalDate searchToDate);

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

    Payments savePayment(Payments payments);

    List<Payments> findUserRoomPaymentWithStatus(String userId, String roomId, PaymentAgainstType type, List<PaymentStatus> status);

    Payments findUserRoomLastPaymentWithTypeAndStatus(String userId, String roomId, PaymentAgainstType type, List<PaymentStatus> status);

}
