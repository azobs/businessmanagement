package com.c2psi.businessmanagement.controllers.apiImpl.pos.loading;

import com.c2psi.businessmanagement.controllers.api.pos.loading.LoadingApi;
import com.c2psi.businessmanagement.dtos.pos.loading.LoadingDetailsDto;
import com.c2psi.businessmanagement.dtos.pos.loading.LoadingDto;
import com.c2psi.businessmanagement.dtos.pos.loading.PackingDetailsDto;
import com.c2psi.businessmanagement.services.contracts.pos.loading.LoadingDetailsService;
import com.c2psi.businessmanagement.services.contracts.pos.loading.LoadingService;
import com.c2psi.businessmanagement.services.contracts.pos.loading.PackingDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
public class LoadingApiImpl implements LoadingApi {
    private LoadingService loadingService;
    private LoadingDetailsService loadingDetailsService;
    private PackingDetailsService packingDetailsService;

    public LoadingApiImpl(LoadingService loadingService, LoadingDetailsService loadingDetailsService,
                          PackingDetailsService packingDetailsService) {
        this.loadingService = loadingService;
        this.loadingDetailsService = loadingDetailsService;
        this.packingDetailsService = packingDetailsService;
    }

    @Override
    public ResponseEntity saveLoading(LoadingDto loadingDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", loadingDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        LoadingDto loadingDtoSaved = loadingService.saveLoading(loadingDto);
        log.info("The method saveLoading is being executed");

        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Loading in pos created successfully ");
        map.put("data", loadingDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateLoading(LoadingDto loadingDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", loadingDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        LoadingDto loadingDtoUpdated = loadingService.updateLoading(loadingDto);
        log.info("The method updateLoading is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Loading in pos updated successfully ");
        map.put("data", loadingDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findLoadingById(Long loadingId) {
        Map<String, Object> map = new LinkedHashMap<>();
        LoadingDto loadingDtoFound = loadingService.findLoadingById(loadingId);
        log.info("The method findLoadingById is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Loading in pos found successfully ");
        map.put("data", loadingDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findLoadingByCodeinPos(String loadingCode, Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        LoadingDto loadingDtoFound = loadingService.findLoadingByCodeinPos(loadingCode, posId);
        log.info("The method findLoadingByCodeinPos is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Loading in pos found successfully ");
        map.put("data", loadingDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllLoadinginPosBetween(Long posId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<LoadingDto> loadingDtoList = loadingService.findAllLoadinginPosBetween(posId, startDate, endDate);
        log.info("The method findAllLoadinginPosBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Loading list between in pos found successfully ");
        map.put("data", loadingDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageLoadinginPosBetween(Long posId, Instant startDate, Instant endDate,
                                                      Optional<Integer> optpagenum, Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();

        Page<LoadingDto> loadingDtoPage = loadingService.findPageLoadinginPosBetween(posId, startDate, endDate,
                pagenum, pagesize);
        log.info("The method findPageLoadinginPosBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Loading page between in pos found successfully ");
        map.put("data", loadingDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllLoadingofUserbmManagerinPosBetween(Long posId, Long userbmId, Instant startDate,
                                                                    Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<LoadingDto> loadingDtoList = loadingService.findAllLoadingofUserbmManagerinPosBetween(posId, userbmId,
                startDate, endDate);
        log.info("The method findAllLoadingofUserbmManagerinPosBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Loading list between in pos found successfully ");
        map.put("data", loadingDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageLoadingofUserbmManagerinPosBetween(Long posId, Long userbmId, Instant startDate,
                                                                     Instant endDate, Optional<Integer> optpagenum,
                                                                     Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();

        Page<LoadingDto> loadingDtoPage = loadingService.findPageLoadingofUserbmManagerinPosBetween(posId, userbmId,
                startDate, endDate,
                pagenum, pagesize);
        log.info("The method findPageLoadingofUserbmManagerinPosBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Loading page between in pos found successfully ");
        map.put("data", loadingDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllLoadingofUserbmSalerinPosBetween(Long posId, Long userbmId, Instant startDate,
                                                                  Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<LoadingDto> loadingDtoList = loadingService.findAllLoadingofUserbmSalerinPosBetween(posId, userbmId,
                startDate, endDate);
        log.info("The method findAllLoadingofUserbmSalerinPosBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Loading list between in pos found successfully ");
        map.put("data", loadingDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageLoadingofUserbmSalerinPosBetween(Long posId, Long userbmId, Instant startDate,
                                                                   Instant endDate, Optional<Integer> optpagenum,
                                                                   Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();

        Page<LoadingDto> loadingDtoPage = loadingService.findPageLoadingofUserbmSalerinPosBetween(posId, userbmId,
                startDate, endDate,
                pagenum, pagesize);
        log.info("The method findPageLoadingofUserbmSalerinPosBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Loading page between in pos found successfully ");
        map.put("data", loadingDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllLoadingofUserbmManagerandSalerinPosBetween(Long posId, Long userbmId_m,
                                                                            Long userbmId_s, Instant startDate,
                                                                            Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<LoadingDto> loadingDtoList = loadingService.findAllLoadingofUserbmManagerandSalerinPosBetween(
                posId, userbmId_m, userbmId_s,
                startDate, endDate);
        log.info("The method findAllLoadingofUserbmManagerandSalerinPosBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Loading list between in pos found successfully ");
        map.put("data", loadingDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageLoadingofUserbmManagerandSalerinPosBetween(Long posId, Long userbmId_m,
                                                                             Long userbmId_s, Instant startDate,
                                                                             Instant endDate,
                                                                             Optional<Integer> optpagenum,
                                                                             Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();

        Page<LoadingDto> loadingDtoPage = loadingService.findPageLoadingofUserbmManagerandSalerinPosBetween(
                posId, userbmId_m, userbmId_s,
                startDate, endDate,
                pagenum, pagesize);
        log.info("The method findPageLoadingofUserbmManagerandSalerinPosBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Loading page between in pos found successfully ");
        map.put("data", loadingDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteLoadingById(Long loadingId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = loadingService.deleteLoadingById(loadingId);
        log.info("The method deleteLoadingById is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Loading page between in pos deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity saveLoadingDetails(LoadingDetailsDto ldDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", ldDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        LoadingDetailsDto loadingDetailsDtoSaved = loadingDetailsService.saveLoadingDetails(ldDto);
        log.info("The method saveLoadingDetails is being executed");

        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "LoadingDetails in pos created successfully ");
        map.put("data", loadingDetailsDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateLoadingDetails(LoadingDetailsDto ldDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", ldDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        LoadingDetailsDto loadingDetailsDtoUpdated = loadingDetailsService.updateLoadingDetails(ldDto);
        log.info("The method updateLoadingDetails is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "LoadingDetails in loading updated successfully ");
        map.put("data", loadingDetailsDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findLoadingDetailsById(Long ldId) {
        Map<String, Object> map = new LinkedHashMap<>();
        LoadingDetailsDto loadingDetailsDtoFound = loadingDetailsService.findLoadingDetailsById(ldId);
        log.info("The method findLoadingDetailsById is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "LoadingDetails in loading found successfully ");
        map.put("data", loadingDetailsDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findLoadingDetailsofArticleinLoading(Long artId, Long loadingId) {
        Map<String, Object> map = new LinkedHashMap<>();
        LoadingDetailsDto loadingDetailsDtoFound = loadingDetailsService.findLoadingDetailsofArticleinLoading(artId, loadingId);
        log.info("The method findLoadingDetailsofArticleinLoading is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "LoadingDetails in loading found successfully ");
        map.put("data", loadingDetailsDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllLoadingDetailsinLoading(Long loadingId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<LoadingDetailsDto> loadingDetailsDtoList = loadingDetailsService.findAllLoadingDetailsinLoading(loadingId);
        log.info("The method findAllLoadingDetailsinLoading is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "LoadingDetails list in loading found successfully ");
        map.put("data", loadingDetailsDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageLoadingDetailsinLoading(Long loadingId, Optional<Integer> optpagenum,
                                                          Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<LoadingDetailsDto> loadingDetailsDtoPage = loadingDetailsService.findPageLoadingDetailsinLoading(
                loadingId, pagenum, pagesize);
        log.info("The method findPageLoadingDetailsinLoading is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "LoadingDetails page in loading found successfully ");
        map.put("data", loadingDetailsDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteLoadingDetailsById(Long ldId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = loadingDetailsService.deleteLoadingDetailsById(ldId);
        log.info("The method deleteLoadingDetailsById is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "LoadingDetails in loading deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity savePackingDetails(PackingDetailsDto pdDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", pdDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        PackingDetailsDto packingDetailsDtoSaved = packingDetailsService.savePackingDetails(pdDto);
        log.info("The method savePackingDetails is being executed");

        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "PackingDetails in pos created successfully ");
        map.put("data", packingDetailsDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updatePackingDetails(PackingDetailsDto pdDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", pdDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        PackingDetailsDto packingDetailsDtoUpdated = packingDetailsService.updatePackingDetails(pdDto);
        log.info("The method updatePackingDetails is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "PackingDetails in loading updated successfully ");
        map.put("data", packingDetailsDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPackingDetailsById(Long pdId) {
        Map<String, Object> map = new LinkedHashMap<>();
        PackingDetailsDto packingDetailsDtoFound= packingDetailsService.findPackingDetailsById(pdId);
        log.info("The method updatePackingDetails is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "PackingDetails in loading found successfully ");
        map.put("data", packingDetailsDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPackingDetailsofArticleinLoading(Long packagingId, Long loadingId) {
        Map<String, Object> map = new LinkedHashMap<>();
        PackingDetailsDto packingDetailsDtoFound= packingDetailsService.findPackingDetailsofArticleinLoading(
                packagingId, loadingId);
        log.info("The method findPackingDetailsofArticleinLoading is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "PackingDetails in loading found successfully ");
        map.put("data", packingDetailsDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllPackingDetailsinLoading(Long loadingId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<PackingDetailsDto> packingDetailsDtoList = packingDetailsService.findAllPackingDetailsinLoading(loadingId);
        log.info("The method findAllPackingDetailsinLoading is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "PackingDetails list in loading found successfully ");
        map.put("data", packingDetailsDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPagePackingDetailsinLoading(Long loadingId, Optional<Integer> optpagenum,
                                                          Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();

        Page<PackingDetailsDto> packingDetailsDtoPage = packingDetailsService.findPagePackingDetailsinLoading(
                loadingId, pagenum, pagesize);
        log.info("The method findPagePackingDetailsinLoading is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "PackingDetails page in loading found successfully ");
        map.put("data", packingDetailsDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deletePackingDetailsById(Long pdId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = packingDetailsService.deletePackingDetailsById(pdId);
        log.info("The method deletePackingDetailsById is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "PackingDetails page in loading deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
