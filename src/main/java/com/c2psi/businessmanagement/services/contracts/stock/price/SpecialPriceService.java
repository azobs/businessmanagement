package com.c2psi.businessmanagement.services.contracts.stock.price;

import com.c2psi.businessmanagement.dtos.stock.price.SpecialPriceDto;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface SpecialPriceService {
    SpecialPriceDto saveSpecialPrice(SpecialPriceDto specialPriceDto);
    SpecialPriceDto updateSpecialPrice(SpecialPriceDto specialPriceDto);
    SpecialPriceDto findSpecialPriceById(Long specialPriceId);
    List<SpecialPriceDto> findAllofSpecialpriceofBaseprice(Long basePriceId);
    Page<SpecialPriceDto> findPageofSpecialpriceofBaseprice(Long basePriceId, int pagenum, int pagesize);
    List<SpecialPriceDto> findAllSpecialpriceofArticle(Long articleId);
    Page<SpecialPriceDto> findPageSpecialpriceofArticle(Long articleId, int pagenum, int pagesize);
    Boolean deleteSpecialPriceById(Long specialPriceId);
    Boolean isSpecialPriceDeleteable(Long specialPriceId);

}
