package com.c2psi.businessmanagement.services.contracts.client.command;

import com.c2psi.businessmanagement.dtos.client.command.SaleInvoiceCashDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface SaleInvoiceCashService {
    SaleInvoiceCashDto saveSaleInvoiceCash(SaleInvoiceCashDto saleicashDto);
    SaleInvoiceCashDto updateSaleInvoiceCash(SaleInvoiceCashDto saleicashDto);
    Boolean isSaleInvoiceCashUniqueinPos(String saleicashCode, Long posId);
    Boolean isSaleInvoiceCashDeleteable(Long saleicashId);
    Boolean deleteSaleInvoiceCashById(Long saleicashId);
    SaleInvoiceCashDto findSaleInvoiceCashById(Long saleicashId);
    SaleInvoiceCashDto findSaleInvoiceCashByCode(String saleicashCode, Long posId);

    //Faire la liste des SaleInvoiceCash dans un intervalle de temps puis page par page
    List<SaleInvoiceCashDto> findAllSaleicashBetween(Instant startDate, Instant endDate);
    Page<SaleInvoiceCashDto> findPageSaleicashBetween(Instant startDate, Instant endDate, int pagenum, int pagesize);


    //Faire la liste des SaleInvoiceCash d'un client dans un intervalle de temps puis page par page
    List<SaleInvoiceCashDto> findAllSaleicashofClientBetween(Long clientId, Instant startDate, Instant endDate);
    Page<SaleInvoiceCashDto> findPageSaleicashofClientBetween(Long clientId, Instant startDate, Instant endDate,
                                                              int pagenum, int pagesize);


    //Faire la liste des SaleInvoiceCash d'un userBM dans un intervalle de temps puis page par page
    List<SaleInvoiceCashDto> findAllSaleicashofUserbmBetween(Long userbmId, Instant startDate, Instant endDate);
    Page<SaleInvoiceCashDto> findPageSaleicashofUserbmBetween(Long userbmId, Instant startDate, Instant endDate,
                                                              int pagenum, int pagesize);


    //Faire la liste des SaleInvoiceCash dans un Pos dans un intervalle de temps puis page par page
    List<SaleInvoiceCashDto> findAllSaleicashinPosBetween(Long posId, Instant startDate, Instant endDate);
    Page<SaleInvoiceCashDto> findPageSaleicashinPosBetween(Long posId, Instant startDate, Instant endDate,
                                                          int pagenum, int pagesize);
    //Faire la liste des SaleInvoiceCash d'un userBM dans un Pos dans un intervalle de temps puis page par page
    List<SaleInvoiceCashDto> findAllSaleicashofUserbminPosBetween(Long userbmId, Long posId, Instant startDate,
                                                                  Instant endDate);
    Page<SaleInvoiceCashDto> findPageSaleicashofUserbminPosBetween(Long userbmId, Long posId, Instant startDate,
                                                                  Instant endDate, int pagenum, int pagesize);

    //Faire la liste des SaleInvoiceCash d'un client dans un Pos dans un intervalle de temps puis page par page
    List<SaleInvoiceCashDto> findAllSaleicashofClientinPosBetween(Long clientId, Long posId, Instant startDate,
                                                                  Instant endDate);
    Page<SaleInvoiceCashDto> findPageSaleicashofClientinPosBetween(Long clientId, Long posId, Instant startDate,
                                                                   Instant endDate, int pagenum, int pagesize);
    //Faire la liste des SaleInvoiceCash d'un client enregistre par un userBM dans un intervalle de temps puis page par page
    List<SaleInvoiceCashDto> findAllSaleicashofClientforUserbmBetween(Long clientId, Long userbmId, Instant startDate,
                                                                      Instant endDate);
    Page<SaleInvoiceCashDto> findPageSaleicashofClientforUserbmBetween(Long clientId, Long userbmId, Instant startDate,
                                                                       Instant endDate, int pagenum, int pagesize);
    //Faire la liste des SaleInvoiceCash d'un client enregistre par un userBM dans un intervalle de temps puis page par page
    List<SaleInvoiceCashDto> findAllSaleicashofClientforUserbminPosBetween(Long clientId, Long userbmId, Long posId,
                                                                           Instant startDate, Instant endDate);
    Page<SaleInvoiceCashDto> findPageSaleicashofClientforUserbminPosBetween(Long clientId, Long userbmId, Long posId,
                                                                            Instant startDate, Instant endDate,
                                                                            int pagenum, int pagesize);

}
