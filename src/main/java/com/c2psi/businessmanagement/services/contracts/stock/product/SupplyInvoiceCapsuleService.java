package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceCapsuleDto;
import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceCashDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;

import java.util.Date;
import java.util.List;

public interface SupplyInvoiceCapsuleService {
    SupplyInvoiceCapsuleDto saveSupplyInvoiceCapsule(SupplyInvoiceCapsuleDto sicapsDto);

    Boolean deleteSupplyInvoiceCapsuleById(Long sicapsId);
    SupplyInvoiceCapsuleDto findSupplyInvoiceCapsuleById(Long sicapsId);
    SupplyInvoiceCapsuleDto findSupplyInvoiceCapsuleByCode(String sicapsCode, PointofsaleDto posDto);
    List<SupplyInvoiceCapsuleDto> findAllSupplyInvoiceCapsuleBetween(PointofsaleDto posDto,
                                                    Date startDate, Date endDate);
    List<SupplyInvoiceCapsuleDto> findAllSupplyInvoiceCapsuleBetween(
            PointofsaleDto posDto, UserBMDto userBMDto, Date startDate, Date endDate);
    List<SupplyInvoiceCapsuleDto> findAllSupplyInvoiceCapsuleBetween(
            PointofsaleDto posDto, ProviderDto providerDto, Date startDate,
            Date endDate);
    List<SupplyInvoiceCapsuleDto> findAllSupplyInvoiceCapsuleBetween(
            PointofsaleDto posDto, ProviderDto providerDto, UserBMDto userBMDto,
            Date startDate, Date endDate);
    Boolean isSupplyInvoiceCapsuleUnique(String sicapsCode, PointofsaleDto posDto);
}
