package com.hostel.repository;

import com.hostel.domain.Payments;
import com.hostel.domain.enumeration.PaymentAgainstType;
import com.hostel.domain.enumeration.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


/**
 * Spring Data MongoDB repository for the Payments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentsRepository extends MongoRepository<Payments,String> {

    public Page<Payments> findByBuildingAndPaymentFromGreaterThanEqualAndPaymentToLessThanEqual(Pageable page, String buildingId, LocalDate searchFromDate, LocalDate searchToDate);

    public List<Payments> findByCustomerAndRoomAndPayentAgainstAndPaymentStatusIn(String customer, String room, PaymentAgainstType type, Collection<PaymentStatus> statusValues);

    public Payments findOneByCustomerAndRoomAndPayentAgainstAndPaymentStatusInOrderByPaymentToDesc(String customer, String room, PaymentAgainstType type, Collection<PaymentStatus> statusValues);

}
