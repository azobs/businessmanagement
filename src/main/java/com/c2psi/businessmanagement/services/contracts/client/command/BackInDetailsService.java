package com.c2psi.businessmanagement.services.contracts.client.command;

import com.c2psi.businessmanagement.dtos.client.command.BackInDetailsDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BackInDetailsService {
    BackInDetailsDto saveBackInDetails(BackInDetailsDto backInDetailsDto);

    BackInDetailsDto updateBackInDetails(BackInDetailsDto backInDetailsDto);
    BackInDetailsDto findBackInDetailsById(Long backInDetailsId);
    Boolean isBackInDetailsUnique(Long artId, Long backInId);
    BackInDetailsDto findBackInDetailsofArticleinBackIn(Long artId, Long backInId);
    List<BackInDetailsDto> findAllBackInDetailsofBackIn(Long backInId);
    Page<BackInDetailsDto> findPageBackInDetailsofBackIn(Long backInId, int pagenum, int pagesize);
    Boolean isBackInDetailsDeleteable(Long backInDetailsId);
    Boolean deleteBackInDetailsById(Long backInDetailsId);
}
