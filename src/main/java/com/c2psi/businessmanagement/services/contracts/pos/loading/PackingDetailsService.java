package com.c2psi.businessmanagement.services.contracts.pos.loading;

import com.c2psi.businessmanagement.dtos.pos.loading.LoadingDto;
import com.c2psi.businessmanagement.dtos.pos.loading.PackingDetailsDto;
import com.c2psi.businessmanagement.dtos.stock.product.PackagingDto;

import java.util.List;

public interface PackingDetailsService {
    PackingDetailsDto savePackingDetails(PackingDetailsDto pdDto);
    PackingDetailsDto findPackingDetailsById(Long id);
    List<PackingDetailsDto> findAllPackingDetails(LoadingDto loadingDto);
    PackingDetailsDto findPackingDetailsForPackaging(LoadingDto loadingDto,
                                                  PackagingDto packagingDto);
    Boolean isPackingDetailsUniqueForPackaging(LoadingDto loadingDto,
                                                        PackagingDto packagingDto);
    Boolean deletePackingDetailsById(Long id);
}
