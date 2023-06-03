package com.c2psi.businessmanagement.services.contracts.client.client;

import com.c2psi.businessmanagement.dtos.client.client.DiversDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DiversService {
    DiversDto saveDivers(DiversDto diversDto);
    DiversDto updateDivers(DiversDto diversDto);
    DiversDto findDiversByEmail(String diversEmail);
    DiversDto findDiversById(Long diversId);
    Boolean isDiversUnique(String diversEmail);
    List<DiversDto> findAllDiversofPos(Long posId);
    Page<DiversDto> findPageDiversofPos(Long posId, int pagenum, int pagesize);
    List<DiversDto> findAllDiversByNameinPos(String diversName, Long posId);
    Page<DiversDto> findPageDiversByNameinPos(String diversName, Long posId, int pagenum, int pagesize);
    Boolean isDiversDeleteable(Long diversId);
    Boolean deleteDiversById(Long diversId);
}
