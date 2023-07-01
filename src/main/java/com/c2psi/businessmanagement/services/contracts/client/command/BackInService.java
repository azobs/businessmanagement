package com.c2psi.businessmanagement.services.contracts.client.command;

import com.c2psi.businessmanagement.dtos.client.command.BackInDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface BackInService {
    BackInDto saveBackIn(BackInDto backInDto);
    Boolean isBackInUniqueforCommand(Long cmdId);
    BackInDto updateBackIn(BackInDto backInDto);
    BackInDto findBackInById(Long backInId);
    BackInDto findBackInofCommand(Long cmdId);
    List<BackInDto> findAllBackIninPosBetween(Long posId, Instant startDate, Instant endDate);
    Page<BackInDto> findPageBackIninPosBetween(Long posId, Instant startDate, Instant endDate, int pagenum, int pagesize);
    List<BackInDto> findAllBackIninPosforUserBMBetween(Long posId, Long userbmId, Instant startDate, Instant endDate);
    Page<BackInDto> findPageBackIninPosforUserBMBetween(Long posId, Long userbmId, Instant startDate, Instant endDate,
                                                        int pagenum, int pagesize);
    Boolean isBackInByDeleteable(Long backInId);
    Boolean deleteBackInById(Long backInId);
}
