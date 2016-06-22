package com.hostel.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hostel.entity.Room;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

	public Room findByRoomNum(String email);

	public Room findById(Long id);

	public Room findByRoomTitle(Integer userStatus, String userName);

	public List<Room> findByStatus(Integer roomStatus);

}
