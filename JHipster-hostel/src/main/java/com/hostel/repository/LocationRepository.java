package com.hostel.repository;

import com.hostel.domain.Location;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the Location entity.
 */
@SuppressWarnings("unused")
public interface LocationRepository extends MongoRepository<Location,String> {

    public Page<Location> findByClient(Pageable pageable, String clientId);

    public Location findByClientAndId(String clientId, String locationId);
}
