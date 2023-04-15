package com.c2psi.businessmanagement.services.contracts.stock.price;

import com.c2psi.businessmanagement.dtos.stock.price.SpecialPriceDto;

import java.util.List;

public interface SpecialPriceService {
    SpecialPriceDto saveSpecialPrice(SpecialPriceDto specialPriceDto);
    SpecialPriceDto updateSpecialPrice(SpecialPriceDto specialPriceDto);
    SpecialPriceDto findSpecialPriceById(Long specialPriceId);
    List<SpecialPriceDto> findListofSpecialPriceOf(Long basePriceId);
    Boolean deleteSpecialPriceById(Long specialPriceId);
    Boolean isSpecialPriceDeleteable(Long specialPriceId);
}
