package com.c2psi.businessmanagement.controllers.apiImpl.stock.product;

import com.c2psi.businessmanagement.controllers.api.stock.product.FormatApi;
import com.c2psi.businessmanagement.dtos.stock.product.FormatDto;
import com.c2psi.businessmanagement.services.contracts.stock.product.FormatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class FormatApiImpl implements FormatApi {
    FormatService formatService;

    @Autowired
    public FormatApiImpl(FormatService formatService) {
        this.formatService = formatService;
    }

    @Override
    public ResponseEntity saveFormat(FormatDto formatDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", formatDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }
        FormatDto formatDtoSaved = formatService.saveFormat(formatDto);
        log.info("The formay of product saved successfully");
        return new ResponseEntity(formatDtoSaved, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateFormat(FormatDto formatDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", formatDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }

        FormatDto formatDtoUpdated = formatService.updateFormat(formatDto);
        log.info("The formay of product updated successfully");
        return new ResponseEntity(formatDtoUpdated, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findFormatById(Long formatId) {
        FormatDto formatDtoFound = formatService.findFormatById(formatId);
        log.info("Format found with the id {} is {}", formatId, formatDtoFound);
        return ResponseEntity.ok(formatDtoFound);
    }

    @Override
    public ResponseEntity findFormatInPointofsaleByFullcharacteristic(Long posId, String formatName,
                                                                      BigDecimal formatCapacity) {
        FormatDto formatDtoFound = formatService.findFormatInPointofsaleByFullcharacteristic(formatName, formatCapacity,
                posId);
        log.info("Format found with characteristic");
        return ResponseEntity.ok(formatDtoFound);
    }

    @Override
    public ResponseEntity findAllFormatInPos(Long posId) {
        List<FormatDto> formatDtoList = formatService.findAllFormatInPos(posId);
        log.info("Format list of pos with id {} is found {}", posId, formatDtoList);
        return ResponseEntity.ok(formatDtoList);
    }

    @Override
    public ResponseEntity findPageFormatInPos(Long posId, Optional<Integer> optpagenum,
                                              Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<FormatDto> formatDtoPage = formatService.findPageFormatInPos(posId, pagenum, pagesize);
        log.info("Page of format is successfully found");
        return ResponseEntity.ok(formatDtoPage);
    }
}
