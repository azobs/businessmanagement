package com.c2psi.businessmanagement.services.contracts.stock.product;

import com.c2psi.businessmanagement.dtos.stock.product.CapsuleArrivalDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface CapsuleArrivalService {
    CapsuleArrivalDto saveCapsuleArrival(CapsuleArrivalDto capsaDto);
    CapsuleArrivalDto updateCapsuleArrival(CapsuleArrivalDto capsaDto);
    Boolean isCapsuleArrivalUnique(Long articleId, Long sicapsId);
    Boolean isCapsuleArrivalDeleteable(Long capsaId);
    Boolean deleteCapsuleArrivalById(Long capsaId);
    CapsuleArrivalDto findCapsuleArrivalById(Long capsaId);
    CapsuleArrivalDto findCapsuleArrivalofArticleinSicaps(Long articleId, Long sicapsId);
    List<CapsuleArrivalDto> findAllCapsuleArrivalinSicaps(Long sicapsId);
    Page<CapsuleArrivalDto> findPageCapsuleArrivalinSicaps(Long sicapsId, int pagenum, int pagesize);
    List<CapsuleArrivalDto> findAllCapsuleArrivalinSicapsBetween(Long sicapsId, Instant startDate, Instant endDate);
    Page<CapsuleArrivalDto> findPageCapsuleArrivalinSicapsBetween(Long sicapsId, Instant startDate, Instant endDate,
                                                                  int pagenum, int pagesize);
    List<CapsuleArrivalDto> findAllCapsuleArrivalinPosBetween(Long posId, Instant startDate, Instant endDate);
    Page<CapsuleArrivalDto> findPageCapsuleArrivalinPosBetween(Long posId, Instant startDate, Instant endDate,
                                                                  int pagenum, int pagesize);
}
