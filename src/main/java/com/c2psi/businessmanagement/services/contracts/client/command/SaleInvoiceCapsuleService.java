package com.c2psi.businessmanagement.services.contracts.client.command;

import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCapsuleDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface SaleInvoiceCapsuleService {
    SaleInvoiceCapsuleDto saveSaleInvoiceCapsule(SaleInvoiceCapsuleDto saleiCapsuleDto);
    SaleInvoiceCapsuleDto updateSaleInvoiceCapsule(SaleInvoiceCapsuleDto saleiCapsuleDto);
    Boolean isSaleInvoiceCapsuleUniqueinPos(String saleiCapsuleCode, Long posId);
    Boolean isSaleInvoiceCapsuleDeleteable(Long saleiCapsuleId);
    Boolean deleteSaleInvoiceCapsuleById(Long saleiCapsuleId);
    SaleInvoiceCapsuleDto findSaleInvoiceCapsuleById(Long saleiCapsuleId);
    SaleInvoiceCapsuleDto findSaleInvoiceCapsuleByCode(String saleiCapsuleCode, Long posId);

    //Faire la liste des SaleInvoiceCapsule dans un intervalle de temps puis page par page
    List<SaleInvoiceCapsuleDto> findAllSaleiCapsuleBetween(Instant startDate, Instant endDate);
    Page<SaleInvoiceCapsuleDto> findPageSaleiCapsuleBetween(Instant startDate, Instant endDate, int pagenum, int pagesize);


    //Faire la liste des SaleInvoiceCapsule d'un client dans un intervalle de temps puis page par page
    List<SaleInvoiceCapsuleDto> findAllSaleiCapsuleofClientBetween(Long clientId, Instant startDate, Instant endDate);
    Page<SaleInvoiceCapsuleDto> findPageSaleiCapsuleofClientBetween(Long clientId, Instant startDate, Instant endDate,
                                                                    int pagenum, int pagesize);


    //Faire la liste des SaleInvoiceCapsule d'un userBM dans un intervalle de temps puis page par page
    List<SaleInvoiceCapsuleDto> findAllSaleiCapsuleofUserbmBetween(Long userbmId, Instant startDate, Instant endDate);
    Page<SaleInvoiceCapsuleDto> findPageSaleiCapsuleofUserbmBetween(Long userbmId, Instant startDate, Instant endDate,
                                                                    int pagenum, int pagesize);


    //Faire la liste des SaleInvoiceCapsule dans un Pos dans un intervalle de temps puis page par page
    List<SaleInvoiceCapsuleDto> findAllSaleiCapsuleinPosBetween(Long posId, Instant startDate, Instant endDate);
    Page<SaleInvoiceCapsuleDto> findPageSaleiCapsuleinPosBetween(Long posId, Instant startDate, Instant endDate,
                                                                int pagenum, int pagesize);
    //Faire la liste des SaleInvoiceCapsule d'un userBM dans un Pos dans un intervalle de temps puis page par page
    List<SaleInvoiceCapsuleDto> findAllSaleiCapsuleofUserbminPosBetween(Long userbmId, Long posId, Instant startDate,
                                                                        Instant endDate);
    Page<SaleInvoiceCapsuleDto> findPageSaleiCapsuleofUserbminPosBetween(Long userbmId, Long posId, Instant startDate,
                                                                        Instant endDate, int pagenum, int pagesize);

    //Faire la liste des SaleInvoiceCapsule d'un client dans un Pos dans un intervalle de temps puis page par page
    List<SaleInvoiceCapsuleDto> findAllSaleiCapsuleofClientinPosBetween(Long clientId, Long posId, Instant startDate,
                                                                        Instant endDate);
    Page<SaleInvoiceCapsuleDto> findPageSaleiCapsuleofClientinPosBetween(Long clientId, Long posId, Instant startDate,
                                                                         Instant endDate, int pagenum, int pagesize);
    //Faire la liste des SaleInvoiceCapsule d'un client enregistre par un userBM dans un intervalle de temps puis page par page
    List<SaleInvoiceCapsuleDto> findAllSaleiCapsuleofClientforUserbmBetween(Long clientId, Long userbmId, Instant startDate,
                                                                            Instant endDate);
    Page<SaleInvoiceCapsuleDto> findPageSaleiCapsuleofClientforUserbmBetween(Long clientId, Long userbmId, Instant startDate,
                                                                             Instant endDate, int pagenum, int pagesize);
    //Faire la liste des SaleInvoiceCapsule d'un client enregistre par un userBM dans un intervalle de temps puis page par page
    List<SaleInvoiceCapsuleDto> findAllSaleiCapsuleofClientforUserbminPosBetween(Long clientId, Long userbmId, Long posId,
                                                                                 Instant startDate, Instant endDate);
    Page<SaleInvoiceCapsuleDto> findPageSaleiCapsuleofClientforUserbminPosBetween(Long clientId, Long userbmId, Long posId,
                                                                                  Instant startDate, Instant endDate,
                                                                                  int pagenum, int pagesize);
}
