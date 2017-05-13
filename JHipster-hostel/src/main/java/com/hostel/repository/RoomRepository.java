package com.hostel.repository;

import com.hostel.domain.Room;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Room entity.
 */
@SuppressWarnings("unused")
public interface RoomRepository extends MongoRepository<Room,String> {

}
