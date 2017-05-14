package com.hostel.repository;

import com.hostel.domain.RoomAllocation;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the RoomAllocation entity.
 */
@SuppressWarnings("unused")
public interface RoomAllocationRepository extends MongoRepository<RoomAllocation,String> {

}
