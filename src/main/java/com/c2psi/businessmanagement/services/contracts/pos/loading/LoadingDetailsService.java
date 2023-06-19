package com.c2psi.businessmanagement.services.contracts.pos.loading;

import com.c2psi.businessmanagement.dtos.pos.loading.LoadingDetailsDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LoadingDetailsService {
    LoadingDetailsDto saveLoadingDetails(LoadingDetailsDto ldDto);
    LoadingDetailsDto updateLoadingDetails(LoadingDetailsDto ldDto);
    LoadingDetailsDto findLoadingDetailsById(Long ldId);
    LoadingDetailsDto findLoadingDetailsofArticleinLoading(Long artId, Long loadingId);
    List<LoadingDetailsDto> findAllLoadingDetailsinLoading(Long loadingId);
    Page<LoadingDetailsDto> findPageLoadingDetailsinLoading(Long loadingId, int pagenum, int pagesize);
    Boolean isLoadingDetailsUniqueinLoading(Long artId, Long loadingId);
    Boolean isLoadingDetailsDeleatable(Long ldId);
    Boolean deleteLoadingDetailsById(Long ldId);
}
