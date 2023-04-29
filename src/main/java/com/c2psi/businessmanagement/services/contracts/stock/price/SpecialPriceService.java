package com.c2psi.businessmanagement.services.contracts.stock.price;

import com.c2psi.businessmanagement.dtos.stock.price.SpecialPriceDto;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface SpecialPriceService {
    SpecialPriceDto saveSpecialPrice(SpecialPriceDto specialPriceDto);
    SpecialPriceDto updateSpecialPrice(SpecialPriceDto specialPriceDto);
    SpecialPriceDto findSpecialPriceById(Long specialPriceId);
    List<SpecialPriceDto> findListofSpecialPriceOf(Long basePriceId);
    Page<SpecialPriceDto> findPageofSpecialPriceOf(Long basePriceId, int pagenum, int pagesize);
    Boolean deleteSpecialPriceById(Long specialPriceId);
    Boolean isSpecialPriceDeleteable(Long specialPriceId);

}
