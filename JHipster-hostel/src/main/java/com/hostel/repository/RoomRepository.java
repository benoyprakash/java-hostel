package com.hostel.repository;

import com.hostel.domain.Room;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Page;

/**
 * Spring Data MongoDB repository for the Room entity.
 */
@SuppressWarnings("unused")
public interface RoomRepository extends MongoRepository<Room,String> {

    Page<Room> findByBuilding(Pageable pageable, String buildingId);

}
