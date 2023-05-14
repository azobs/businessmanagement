package com.c2psi.businessmanagement.services.contracts.stock.product;


import com.c2psi.businessmanagement.dtos.stock.product.DamageArrivalDto;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface DamageArrivalService {
    DamageArrivalDto saveDamageArrival(DamageArrivalDto damaDto);
    DamageArrivalDto updateDamageArrival(DamageArrivalDto damaDto);
    Boolean isDamageArrivalUnique(Long articleId, Long sidamId);
    Boolean isDamageArrivalDeleteable(Long damaId);
    Boolean deleteDamageArrivalById(Long damaId);
    DamageArrivalDto findDamageArrivalById(Long damaId);
    DamageArrivalDto findDamageArrivalofArticleinSidam(Long articleId, Long sidamId);
    List<DamageArrivalDto> findAllDamageArrivalinSidam(Long sidamId);
    Page<DamageArrivalDto> findPageDamageArrivalinSidam(Long sidamId, int pagenum, int pagesize);
    List<DamageArrivalDto> findAllDamageArrivalinSidamBetween(Long sidamId, Instant startDate, Instant endDate);
    Page<DamageArrivalDto> findPageDamageArrivalinSidamBetween(Long sidamId, Instant startDate, Instant endDate,
                                                                  int pagenum, int pagesize);
}
