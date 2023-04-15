package com.c2psi.businessmanagement.services.contracts.stock.price;

import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;

import java.util.Optional;

public interface BasePriceService {
    BasePriceDto saveBasePrice(BasePriceDto basePriceDto);
    BasePriceDto updateBasePrice(BasePriceDto basePriceDto);
    BasePriceDto findBasePriceById(Long basePriceId);
    Boolean deleteBasePriceById(Long basePriceId);
    Boolean isBasePriceDeleteable(Long basePriceId);
}
