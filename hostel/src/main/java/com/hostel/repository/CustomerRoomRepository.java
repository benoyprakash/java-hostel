package com.hostel.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hostel.entity.CustomerRoom;
import com.hostel.entity.Room;

@Repository
public interface CustomerRoomRepository extends CrudRepository<CustomerRoom, Long> {

	public Room findByCustomerId(Long customerId);

	public Room findById(Long id);

	public Room findByRoomId(Long roomId);

	public List<Room> findByStatus(Integer roomStatus);

}
