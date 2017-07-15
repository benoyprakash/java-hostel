package com.hostel.repository;

import com.hostel.domain.Building;
import com.hostel.domain.RoomAllocation;

import com.hostel.domain.enumeration.RoomStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the RoomAllocation entity.
 */
@SuppressWarnings("unused")
public interface RoomAllocationRepository extends MongoRepository<RoomAllocation,String> {

    Page<RoomAllocation> findByBuildingId(Pageable pageable, String buildingId);

    Page<RoomAllocation> findByBuildingIdAndCurrStatus(Pageable pageable, String buildingId, RoomStatus currStatus);

}
