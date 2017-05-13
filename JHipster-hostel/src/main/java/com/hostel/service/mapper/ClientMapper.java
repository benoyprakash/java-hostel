package com.hostel.service.mapper;

import com.hostel.domain.*;
import com.hostel.service.dto.ClientDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Client and its DTO ClientDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClientMapper {

    ClientDTO clientToClientDTO(Client client);

    List<ClientDTO> clientsToClientDTOs(List<Client> clients);

    Client clientDTOToClient(ClientDTO clientDTO);

    List<Client> clientDTOsToClients(List<ClientDTO> clientDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     

}
