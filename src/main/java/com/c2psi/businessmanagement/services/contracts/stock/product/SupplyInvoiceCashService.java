package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.Enumerations.CashArrivalType;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.product.SupplyInvoiceCashDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;

import java.util.Date;
import java.util.List;

public interface SupplyInvoiceCashService {
    SupplyInvoiceCashDto saveSupplyInvoiceCash(SupplyInvoiceCashDto sicashDto);
    Boolean deleteSupplyInvoiceCashById(Long sicashId);
    SupplyInvoiceCashDto findSupplyInvoiceCashById(Long sicashId);
    SupplyInvoiceCashDto findSupplyInvoiceCashByCode(String sicashCode, PointofsaleDto posDto);
    List<SupplyInvoiceCashDto> findAllSupplyInvoiceCashBetween(PointofsaleDto posDto,
                                                    Date startDate, Date endDate);
    List<SupplyInvoiceCashDto> findAllSupplyInvoiceCashBetween(
            PointofsaleDto posDto, CashArrivalType cashaType, Date startDate,
            Date endDate);
    List<SupplyInvoiceCashDto> findAllSupplyInvoiceCashBetween(
            PointofsaleDto posDto, UserBMDto userBMDto, Date startDate,
            Date endDate);
    List<SupplyInvoiceCashDto> findAllSupplyInvoiceCashBetween(
            PointofsaleDto posDto, CashArrivalType cashaType,
            UserBMDto userBMDto, Date startDate, Date endDate);
    List<SupplyInvoiceCashDto> findAllSupplyInvoiceCashBetween(
            PointofsaleDto posDto, ProviderDto providerDto, Date startDate,
            Date endDate);
    List<SupplyInvoiceCashDto> findAllSupplyInvoiceCashBetween(
            PointofsaleDto posDto, ProviderDto providerDto, UserBMDto userBMDto,
            Date startDate, Date endDate);
    Boolean isSupplyInvoiceCashUnique(String sicashCode, PointofsaleDto posDto);
}
