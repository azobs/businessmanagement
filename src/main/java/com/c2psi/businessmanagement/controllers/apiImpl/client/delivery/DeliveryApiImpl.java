package com.c2psi.businessmanagement.controllers.apiImpl.client.delivery;

import com.c2psi.businessmanagement.Enumerations.DeliveryState;
import com.c2psi.businessmanagement.controllers.api.client.delivery.DeliveryApi;
import com.c2psi.businessmanagement.dtos.client.delivery.DeliveryDetailsDto;
import com.c2psi.businessmanagement.dtos.client.delivery.DeliveryDto;
import com.c2psi.businessmanagement.services.contracts.client.delivery.DeliveryDetailsService;
import com.c2psi.businessmanagement.services.contracts.client.delivery.DeliveryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class DeliveryApiImpl implements DeliveryApi {
    private DeliveryService deliveryService;
    private DeliveryDetailsService deliveryDetailsService;

    @Autowired
    public DeliveryApiImpl(DeliveryService deliveryService, DeliveryDetailsService deliveryDetailsService) {
        this.deliveryService = deliveryService;
        this.deliveryDetailsService = deliveryDetailsService;
    }

    @Override
    public ResponseEntity saveDelivery(DeliveryDto deliveryDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", deliveryDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        DeliveryDto deliveryDtoSaved = deliveryService.saveDelivery(deliveryDto);
        log.info("The method saveDelivery is being executed");

        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "Delivery created successfully ");
        map.put("data", deliveryDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateDelivery(DeliveryDto deliveryDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", deliveryDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        DeliveryDto deliveryDtoUpdated = deliveryService.updateDelivery(deliveryDto);
        log.info("The method updateDelivery is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Delivery updated successfully ");
        map.put("data", deliveryDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findDeliveryById(Long deliveryId) {
        Map<String, Object> map = new LinkedHashMap<>();

        DeliveryDto deliveryDtoFound = deliveryService.findDeliveryById(deliveryId);
        log.info("The method findDeliveryById is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Delivery found successfully ");
        map.put("data", deliveryDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findDeliveryByCodeinPos(String deliveryCode, Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();

        DeliveryDto deliveryDtoFound = deliveryService.findDeliveryByCodeinPos(deliveryCode, posId);
        log.info("The method findDeliveryByCodeinPos is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Delivery found successfully ");
        map.put("data", deliveryDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteDeliveryById(Long deliveryId) {
        Map<String, Object> map = new LinkedHashMap<>();

        Boolean delete = deliveryService.deleteDeliveryById(deliveryId);
        log.info("The method deleteDeliveryById is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Delivery found successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllDeliveryinPosBetween(Long posId, Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();

        List<DeliveryDto> deliveryDtoList = deliveryService.findAllDeliveryinPosBetween(posId, startDate, endDate);
        log.info("The method findAllDeliveryinPosBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Delivery list found successfully ");
        map.put("data", deliveryDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageDeliveryinPosBetween(Long posId, Instant startDate, Instant endDate,
                                                       Optional<Integer> optpagenum,
                                                       Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();

        Page<DeliveryDto> deliveryDtoPage = deliveryService.findPageDeliveryinPosBetween(posId, startDate, endDate,
                pagenum, pagesize);
        log.info("The method findPageDeliveryinPosBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Delivery page found successfully ");
        map.put("data", deliveryDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllDeliveryinPoswithStateBetween(Long posId, DeliveryState deliveryState, Instant startDate,
                                                               Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();

        List<DeliveryDto> deliveryDtoList = deliveryService.findAllDeliveryinPoswithStateBetween(posId, deliveryState,
                startDate, endDate);
        log.info("The method findAllDeliveryinPoswithStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Delivery list found successfully ");
        map.put("data", deliveryDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageDeliveryinPoswithStateBetween(Long posId, DeliveryState deliveryState,
                                                                Instant startDate, Instant endDate,
                                                                Optional<Integer> optpagenum,
                                                                Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();

        Page<DeliveryDto> deliveryDtoPage = deliveryService.findPageDeliveryinPoswithStateBetween(posId, deliveryState,
                startDate, endDate, pagenum, pagesize);
        log.info("The method findPageDeliveryinPoswithStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Delivery page found successfully ");
        map.put("data", deliveryDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllDeliveryinPosforUserbmBetween(Long posId, Long userbmId, Instant startDate,
                                                               Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();

        List<DeliveryDto> deliveryDtoList = deliveryService.findAllDeliveryinPosforUserbmBetween(posId, userbmId,
                startDate, endDate);
        log.info("The method findAllDeliveryinPosforUserbmBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Delivery list found successfully ");
        map.put("data", deliveryDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageDeliveryinPosforUserbmBetween(Long posId, Long userbmId, Instant startDate,
                                                                Instant endDate, Optional<Integer> optpagenum,
                                                                Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();

        Page<DeliveryDto> deliveryDtoPage = deliveryService.findPageDeliveryinPosforUserbmBetween(posId, userbmId,
                startDate, endDate, pagenum, pagesize);
        log.info("The method findPageDeliveryinPosforUserbmBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Delivery page found successfully ");
        map.put("data", deliveryDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllDeliveryinPosforUserbmwithStateBetween(Long posId, Long userbmId,
                                                                        DeliveryState deliveryState,
                                                                        Instant startDate, Instant endDate) {
        Map<String, Object> map = new LinkedHashMap<>();

        List<DeliveryDto> deliveryDtoList = deliveryService.findAllDeliveryinPosforUserbmwithStateBetween(posId, userbmId,
                deliveryState, startDate, endDate);
        log.info("The method findAllDeliveryinPosforUserbmwithStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Delivery list found successfully ");
        map.put("data", deliveryDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageDeliveryinPosforUserbmwithStateBetween(Long posId, Long userbmId,
                                                                         DeliveryState deliveryState, Instant startDate,
                                                                         Instant endDate, Optional<Integer> optpagenum,
                                                                         Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();

        Page<DeliveryDto> deliveryDtoPage = deliveryService.findPageDeliveryinPosforUserbmwithStateBetween(posId, userbmId,
                deliveryState, startDate, endDate, pagenum, pagesize);
        log.info("The method findPageDeliveryinPosforUserbmwithStateBetween is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Delivery page found successfully ");
        map.put("data", deliveryDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity saveDeliveryDetails(DeliveryDetailsDto ddDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", ddDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        DeliveryDetailsDto deliveryDetailsDtoSaved = deliveryDetailsService.saveDeliveryDetails(ddDto);
        log.info("The method saveDeliveryDetails is being executed");

        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "DeliveryDetails created successfully ");
        map.put("data", deliveryDetailsDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateDeliveryDetails(DeliveryDetailsDto ddDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", ddDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        DeliveryDetailsDto deliveryDetailsDtoUpdated = deliveryDetailsService.updateDeliveryDetails(ddDto);
        log.info("The method updateDeliveryDetails is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "DeliveryDetails updated successfully ");
        map.put("data", deliveryDetailsDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findDeliveryDetailsById(Long ddId) {
        Map<String, Object> map = new LinkedHashMap<>();

        DeliveryDetailsDto deliveryDetailsDtoFound = deliveryDetailsService.findDeliveryDetailsById(ddId);
        log.info("The method findDeliveryDetailsById is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "DeliveryDetails found successfully ");
        map.put("data", deliveryDetailsDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findDeliveryDetailsinDeliverywithPackaging(Long packagingId, Long deliveryId) {
        Map<String, Object> map = new LinkedHashMap<>();

        DeliveryDetailsDto deliveryDetailsDtoFound = deliveryDetailsService.
                findDeliveryDetailsinDeliverywithPackaging(packagingId, deliveryId);
        log.info("The method findDeliveryDetailsinDeliverywithPackaging is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "DeliveryDetails found successfully ");
        map.put("data", deliveryDetailsDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteDeliveryDetailsById(Long ddId) {
        Map<String, Object> map = new LinkedHashMap<>();

        Boolean delete = deliveryDetailsService.deleteDeliveryDetailsById(ddId);
        log.info("The method deleteDeliveryDetailsById is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "DeliveryDetails deleted successfully ");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllDeliveryDetailsinDelivery(Long deliveryId) {
        Map<String, Object> map = new LinkedHashMap<>();

        List<DeliveryDetailsDto> deliveryDetailsDtoList = deliveryDetailsService.
                findAllDeliveryDetailsinDelivery(deliveryId);
        log.info("The method findAllDeliveryDetailsinDelivery is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "DeliveryDetails list found successfully ");
        map.put("data", deliveryDetailsDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageDeliveryDetailsinDelivery(Long deliveryId, Optional<Integer> optpagenum,
                                                            Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();

        Page<DeliveryDetailsDto> deliveryDetailsDtoPage = deliveryDetailsService.
                findPageDeliveryDetailsinDelivery(deliveryId, pagenum, pagesize);
        log.info("The method findPageDeliveryDetailsinDelivery is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "DeliveryDetails list found successfully ");
        map.put("data", deliveryDetailsDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
