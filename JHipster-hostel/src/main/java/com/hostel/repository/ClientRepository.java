package com.hostel.repository;

import com.hostel.domain.Client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Client entity.
 */
@SuppressWarnings("unused")
public interface ClientRepository extends MongoRepository<Client,String> {

    Page<Client> findById(Pageable page, String clientId);
}
