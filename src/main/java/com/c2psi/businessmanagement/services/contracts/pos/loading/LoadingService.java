package com.c2psi.businessmanagement.services.contracts.pos.loading;

import com.c2psi.businessmanagement.dtos.pos.loading.LoadingDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;

import java.util.Date;
import java.util.List;

public interface LoadingService {
    LoadingDto saveLoading(LoadingDto loadingDto);
    LoadingDto findLoadingById(Long loading_id);
    LoadingDto findLoadingByCode(String loadingCode, PointofsaleDto posDto);
    List<LoadingDto> findAllLoading(PointofsaleDto posDto, Date startDate,
                                    Date endDate);
    List<LoadingDto> findAllLoading(PointofsaleDto posDto, UserBMDto user_manager,
                                    UserBMDto userSaler, Date startDate, Date endDate);
    Boolean deleteLoadingById(Long loading_id);
}
