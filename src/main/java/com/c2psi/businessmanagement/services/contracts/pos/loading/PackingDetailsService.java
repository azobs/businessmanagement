package com.c2psi.businessmanagement.services.contracts.pos.loading;

import com.c2psi.businessmanagement.dtos.pos.loading.PackingDetailsDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PackingDetailsService {
    PackingDetailsDto savePackingDetails(PackingDetailsDto pdDto);
    PackingDetailsDto updatePackingDetails(PackingDetailsDto pdDto);
    PackingDetailsDto findPackingDetailsById(Long pdId);
    PackingDetailsDto findPackingDetailsofArticleinLoading(Long packagingId, Long loadingId);
    List<PackingDetailsDto> findAllPackingDetailsinLoading(Long loadingId);
    Page<PackingDetailsDto> findPagePackingDetailsinLoading(Long loadingId, int pagenum, int pagesize);
    Boolean isPackingDetailsUniqueinLoading(Long packagingId, Long loadingId);
    Boolean isPackingDetailsDeleatable(Long pdId);
    Boolean deletePackingDetailsById(Long pdId);
}
