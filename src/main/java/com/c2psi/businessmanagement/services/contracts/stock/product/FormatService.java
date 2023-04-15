package com.c2psi.businessmanagement.services.contracts.stock.product;


import com.c2psi.businessmanagement.dtos.stock.product.FormatDto;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface FormatService {
    FormatDto saveFormat(FormatDto formatDto);
    FormatDto updateFormat(FormatDto formatDto);
    FormatDto findFormatById(Long formatId);
    FormatDto findFormatInPointofsaleByFullcharacteristic(String format_name, BigDecimal formatCapacity,
                         Long posId);
    Boolean isFormatUniqueInPos(String format_name, BigDecimal formatCapacity,
                           Long posId);
    Boolean isFormatExistInPosWith(String format_name, BigDecimal formatCapacity,
                                Long posId);
    Boolean isFormatExistWithId(Long formatId);
    Boolean deleteFormatById(Long formatId);
    Boolean isFormatDeleteable(Long formatId);
    List<FormatDto> findAllFormatInPos(Long posId);
    Page<FormatDto> findPageFormatInPos(Long posId, int pagenum, int pagesize);
}
