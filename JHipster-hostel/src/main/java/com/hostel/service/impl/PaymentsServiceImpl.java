package com.hostel.service.impl;

import com.hostel.domain.Room;
import com.hostel.domain.User;
import com.hostel.domain.enumeration.PaymentAgainstType;
import com.hostel.domain.enumeration.PaymentStatus;
import com.hostel.repository.RoomRepository;
import com.hostel.repository.UserRepository;
import com.hostel.service.PaymentsService;
import com.hostel.domain.Payments;
import com.hostel.repository.PaymentsRepository;
import com.hostel.service.dto.PaymentsDTO;
import com.hostel.service.mapper.PaymentsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


/**
 * Service Implementation for managing Payments.
 */
@Service
public class PaymentsServiceImpl implements PaymentsService{

    private final Logger log = LoggerFactory.getLogger(PaymentsServiceImpl.class);

    private final PaymentsRepository paymentsRepository;

    private final UserRepository userRepository;

    private final RoomRepository roomRepository;

    private final PaymentsMapper paymentsMapper;

    public PaymentsServiceImpl(PaymentsRepository paymentsRepository, PaymentsMapper paymentsMapper,
                            UserRepository userRepository, RoomRepository roomRepository) {
        this.paymentsRepository = paymentsRepository;
        this.paymentsMapper = paymentsMapper;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    /**
     * Save a payments.
     *
     * @param paymentsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PaymentsDTO save(PaymentsDTO paymentsDTO) {
        log.debug("Request to save Payments : {}", paymentsDTO);
        Payments payments = paymentsMapper.toEntity(paymentsDTO);
        payments = paymentsRepository.save(payments);
        return paymentsMapper.toDto(payments);
    }

    @Override
    public Payments savePayment(Payments payments) {
        log.debug("Request to save Payments : {}", payments);
        payments = paymentsRepository.save(payments);
        return payments;
    }

    /**
     *  Get all the payments.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<PaymentsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Payments");

        Page<PaymentsDTO> payments = paymentsRepository.findAll(pageable)
            .map(paymentsMapper::toDto);

        for(PaymentsDTO dto : payments.getContent()){

            User customer = userRepository.findOne(dto.getCustomer());
            Room room = roomRepository.findOne(dto.getRoom());

            if(customer != null){
                dto.setCustomerName(customer.getLastName() + ", "+ customer.getFirstName());
            }
            if(room != null){
                dto.setRoomName(room.getRoomName());
            }
        }
        return payments;
    }

    @Override
    public Page<PaymentsDTO> findAllByBuildingAndDateFilter(Pageable pageable, String buildingId, LocalDate searchFromDate,
                                                            LocalDate searchToDate) {
        log.debug("Request to get all Payments");
        if(searchFromDate == null || searchToDate == null){
            searchFromDate = LocalDate.now().minusDays(90);
            searchToDate = LocalDate.now().plusDays(90);
        }
        Page<PaymentsDTO> payments = paymentsRepository.findByBuildingAndPaymentFromGreaterThanEqualAndPaymentToLessThanEqual(pageable, buildingId, searchFromDate, searchToDate)
            .map(paymentsMapper::toDto);

        for(PaymentsDTO dto : payments.getContent()){

            User customer = userRepository.findOne(dto.getCustomer());
            Room room = roomRepository.findOne(dto.getRoom());

            if(customer != null){
                dto.setCustomerName(customer.getLastName() + ", "+ customer.getFirstName());
            }
            if(room != null){
                dto.setRoomName(room.getRoomName());
            }
        }
        return payments;

    }



    /**
     *  Get one payments by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public PaymentsDTO findOne(String id) {
        log.debug("Request to get Payments : {}", id);
        Payments payments = paymentsRepository.findOne(id);
        PaymentsDTO dto = paymentsMapper.toDto(payments);

        User customer = userRepository.findOne(dto.getCustomer());
        Room room = roomRepository.findOne(dto.getRoom());

        if(customer != null){
            dto.setCustomerName(customer.getLastName() + ", "+ customer.getFirstName());
        }
        if(room != null){
            dto.setRoomName(room.getRoomName());
        }
        return dto;
    }

    /**
     *  Delete the  payments by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Payments : {}", id);
        Payments payment = paymentsRepository.findOne(id);
        payment.setPaymentStatus(PaymentStatus.CANCELLED);
        paymentsRepository.save(payment);
    }


    @Override
    public List<Payments> findUserRoomPaymentWithStatus(String userId, String roomId, PaymentAgainstType type, List<PaymentStatus> status){
        return paymentsRepository.findByCustomerAndRoomAndPayentAgainstAndPaymentStatusIn(userId, roomId, type, status);
    }

    @Override
    public Payments findUserRoomLastPaymentWithTypeAndStatus(String userId, String roomId, PaymentAgainstType type, List<PaymentStatus> status){
        return paymentsRepository.findOneByCustomerAndRoomAndPayentAgainstAndPaymentStatusInOrderByPaymentToDesc(userId, roomId, type, status);
    }

}
