package com.hostel.repository;

import com.hostel.domain.Payments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * Spring Data MongoDB repository for the Payments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentsRepository extends MongoRepository<Payments,String> {

    public Page<Payments> findByBuilding(Pageable page, String buildingId);
}
