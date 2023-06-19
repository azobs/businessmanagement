package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface PackagingService {
    PackagingDto savePackaging(PackagingDto packDto);
    PackagingDto updatePackaging(PackagingDto packDto);
    PackagingDto findPackagingById(Long packId);
    PackagingDto findPackagingByAttributes(String pack_label, String pack_firstcolor, Long providerId, Long posId);
    Boolean isPackagingUniqueinPos(String packLabel, String packFirstcolor, Long providerId, Long posId);
    List<PackagingDto> findAllPackagingofPos(Long posId);
    Page<PackagingDto> findPagePackagingofPos(Long posId, int pagenum, int pagesize);
    List<PackagingDto> findAllPackagingofProviderinPos(Long providerId, Long posId);
    Page<PackagingDto> findPagePackagingofProviderinPos(Long providerId, Long posId, int pagenum, int pagesize);
    Boolean isPackagingDeleteable(Long packId);
    Boolean deletePackagingById(Long packId);

    /**************************************************************
     * Deconsignation de Packaging: il s'agit ici de convertir un
     * certains nombre d'emballage en argent. cette deconsignation
     * peut aller direct dans le compte cash d'un client ou alors
     * directement etre considerer comme de l'argent verse
     ***************************************************************/
    BigDecimal convertPackagingToCash(Long packagingId, BigDecimal nberofPackaging);

    /**************************************************************
     *Consignation de Packaging: Il s'agit ici de convertir un
     * montant d'argent en nombre d'emballage.
     */
    BigDecimal convertCashToPackaging(Long packagingId, BigDecimal amount);
}
