package com.hostel.repository;

import com.hostel.domain.Building;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Building entity.
 */
@SuppressWarnings("unused")
public interface BuildingRepository extends MongoRepository<Building,String> {

    Page<Building> findByLocation(Pageable pageable, String locationId);

}
