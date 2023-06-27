package com.c2psi.businessmanagement.services.contracts.client.client;

import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClientService {
    ClientDto saveClient(ClientDto clientDto);
    ClientDto updateClient(ClientDto clientDto);
    ClientDto findClientByNameofPos(String clientName, String clientOthername, Long posId);
    Boolean isClientUniqueForPos(String clientName, String clientOthername, String clientCni, Long posId);
    public Boolean isClientUniqueWithEmail(String clientEmail);
    ClientDto findClientByCniofPos(String clientCni, Long posId);
    ClientDto findClientById(Long clientId);
    ClientDto findClientByEmail(String clientEmail);
    List<ClientDto> findAllClientofPos(Long posId);
    Page<ClientDto> findPageClientofPos(Long posId, int pagenum, int pagesize);
    Boolean isClientDeleteable(Long clientId);
    Boolean deleteClientById(Long clientId);
}
