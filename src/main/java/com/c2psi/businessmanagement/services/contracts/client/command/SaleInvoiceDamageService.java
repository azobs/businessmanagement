package com.c2psi.businessmanagement.services.contracts.client.command;

import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceDamageDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface SaleInvoiceDamageService {
    SaleInvoiceDamageDto saveSaleInvoiceDamage(SaleInvoiceDamageDto saleiDamageDto);
    SaleInvoiceDamageDto updateSaleInvoiceDamage(SaleInvoiceDamageDto saleiDamageDto);
    Boolean isSaleInvoiceDamageUniqueinPos(String saleiDamageCode, Long posId);
    Boolean isSaleInvoiceDamageDeleteable(Long saleiDamageId);
    Boolean deleteSaleInvoiceDamageById(Long saleiDamageId);
    SaleInvoiceDamageDto findSaleInvoiceDamageById(Long saleiDamageId);
    SaleInvoiceDamageDto findSaleInvoiceDamageByCode(String saleiDamageCode, Long posId);

    //Faire la liste des SaleInvoiceDamage dans un intervalle de temps puis page par page
    List<SaleInvoiceDamageDto> findAllSaleiDamageBetween(Instant startDate, Instant endDate);
    Page<SaleInvoiceDamageDto> findPageSaleiDamageBetween(Instant startDate, Instant endDate, int pagenum, int pagesize);


    //Faire la liste des SaleInvoiceDamage d'un client dans un intervalle de temps puis page par page
    List<SaleInvoiceDamageDto> findAllSaleiDamageofClientBetween(Long clientId, Instant startDate, Instant endDate);
    Page<SaleInvoiceDamageDto> findPageSaleiDamageofClientBetween(Long clientId, Instant startDate, Instant endDate,
                                                                    int pagenum, int pagesize);


    //Faire la liste des SaleInvoiceDamage d'un userBM dans un intervalle de temps puis page par page
    List<SaleInvoiceDamageDto> findAllSaleiDamageofUserbmBetween(Long userbmId, Instant startDate, Instant endDate);
    Page<SaleInvoiceDamageDto> findPageSaleiDamageofUserbmBetween(Long userbmId, Instant startDate, Instant endDate,
                                                                    int pagenum, int pagesize);


    //Faire la liste des SaleInvoiceDamage dans un Pos dans un intervalle de temps puis page par page
    List<SaleInvoiceDamageDto> findAllSaleiDamageinPosBetween(Long posId, Instant startDate, Instant endDate);
    Page<SaleInvoiceDamageDto> findPageSaleiDamageinPosBetween(Long posId, Instant startDate, Instant endDate,
                                                                int pagenum, int pagesize);
    //Faire la liste des SaleInvoiceDamage d'un userBM dans un Pos dans un intervalle de temps puis page par page
    List<SaleInvoiceDamageDto> findAllSaleiDamageofUserbminPosBetween(Long userbmId, Long posId, Instant startDate,
                                                                        Instant endDate);
    Page<SaleInvoiceDamageDto> findPageSaleiDamageofUserbminPosBetween(Long userbmId, Long posId, Instant startDate,
                                                                        Instant endDate, int pagenum, int pagesize);

    //Faire la liste des SaleInvoiceDamage d'un client dans un Pos dans un intervalle de temps puis page par page
    List<SaleInvoiceDamageDto> findAllSaleiDamageofClientinPosBetween(Long clientId, Long posId, Instant startDate,
                                                                        Instant endDate);
    Page<SaleInvoiceDamageDto> findPageSaleiDamageofClientinPosBetween(Long clientId, Long posId, Instant startDate,
                                                                         Instant endDate, int pagenum, int pagesize);
    //Faire la liste des SaleInvoiceDamage d'un client enregistre par un userBM dans un intervalle de temps puis page par page
    List<SaleInvoiceDamageDto> findAllSaleiDamageofClientforUserbmBetween(Long clientId, Long userbmId, Instant startDate,
                                                                            Instant endDate);
    Page<SaleInvoiceDamageDto> findPageSaleiDamageofClientforUserbmBetween(Long clientId, Long userbmId, Instant startDate,
                                                                             Instant endDate, int pagenum, int pagesize);
    //Faire la liste des SaleInvoiceDamage d'un client enregistre par un userBM dans un intervalle de temps puis page par page
    List<SaleInvoiceDamageDto> findAllSaleiDamageofClientforUserbminPosBetween(Long clientId, Long userbmId, Long posId,
                                                                                 Instant startDate, Instant endDate);
    Page<SaleInvoiceDamageDto> findPageSaleiDamageofClientforUserbminPosBetween(Long clientId, Long userbmId, Long posId,
                                                                                  Instant startDate, Instant endDate,
                                                                                  int pagenum, int pagesize);
}
