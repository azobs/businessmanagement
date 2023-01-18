package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.stock.product.FormatDto;

import java.math.BigDecimal;
import java.util.List;

public interface FormatService {
    FormatDto saveFormat(FormatDto formatDto);
    FormatDto findFormat(Long formatId);
    FormatDto findFormat(String format_name, BigDecimal formatCapacity,
                         PointofsaleDto posDto);
    Boolean isFormatUnique(String format_name, BigDecimal formatCapacity,
                           PointofsaleDto posDto);
    Boolean deleteFormatById(Long formatId);
    Boolean isFormatUsed(FormatDto formatDto);
    List<FormatDto> findAllFormat(PointofsaleDto posDto);
}
