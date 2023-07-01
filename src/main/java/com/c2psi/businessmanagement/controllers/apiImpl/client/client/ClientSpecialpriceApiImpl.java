package com.c2psi.businessmanagement.controllers.apiImpl.client.client;

import com.c2psi.businessmanagement.controllers.api.client.client.ClientSpecialpriceApi;
import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientSpecialpriceDto;
import com.c2psi.businessmanagement.services.contracts.client.client.ClientSpecialpriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
public class ClientSpecialpriceApiImpl implements ClientSpecialpriceApi {
    private ClientSpecialpriceService clientSpecialpriceService;

    @Autowired
    public ClientSpecialpriceApiImpl(ClientSpecialpriceService clientSpecialpriceService) {
        this.clientSpecialpriceService = clientSpecialpriceService;
    }

    @Override
    public ResponseEntity saveClientSpecialprice(ClientSpecialpriceDto cltspepriceDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", cltspepriceDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        ClientSpecialpriceDto cltspepriceDtoSaved = clientSpecialpriceService.saveClientSpecialprice(cltspepriceDto);
        log.info("The method saveClientSpecialprice is being executed");
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "ClientSpecialprice created successfully");
        map.put("data", cltspepriceDtoSaved);
        map.put("cause", "Le prix special du client a ete cree avec success");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateClientSpecialprice(ClientSpecialpriceDto cltspepriceDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", cltspepriceDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        ClientSpecialpriceDto cltspepriceDtoUpdated = clientSpecialpriceService.updateClientSpecialprice(cltspepriceDto);
        log.info("The method updateClientSpecialprice is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "ClientSpecialprice updated successfully");
        map.put("data", cltspepriceDtoUpdated);
        map.put("cause", "Le prix special du client a ete mis a jour avec success");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findClientSpecialpriceById(Long cltspepriceId) {
        Map<String, Object> map = new LinkedHashMap<>();
        ClientSpecialpriceDto cltspepriceDtoFound= clientSpecialpriceService.findClientSpecialpriceById(cltspepriceId);
        log.info("The method findClientSpecialpriceById is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "ClientSpecialprice found successfully");
        map.put("data", cltspepriceDtoFound);
        map.put("cause", "Le prix special du client a ete retrouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findClientSpecialpriceofArticleforClient(Long articleId, Long clientId) {
        Map<String, Object> map = new LinkedHashMap<>();
        ClientSpecialpriceDto cltspepriceDtoFound= clientSpecialpriceService.
                findClientSpecialpriceofArticleforClient(articleId, clientId);
        log.info("The method findClientSpecialpriceofArticleforClient is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "ClientSpecialprice found successfully");
        map.put("data", cltspepriceDtoFound);
        map.put("cause", "Le prix special du client a ete retrouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSpecialpriceofArticle(Long articleId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ClientSpecialpriceDto> clientspecialPriceDtoList = clientSpecialpriceService.findAllSpecialpriceofArticle(articleId);
        log.info("The method findAllSpecialpriceofArticle is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "ClientSpecialprice list found successfully");
        map.put("data", clientspecialPriceDtoList);
        map.put("cause", "La liste des prix special du client a ete retrouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSpecialpriceofArticle(Long articleId, Optional<Integer> optpagenum,
                                                        Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Page<ClientSpecialpriceDto> clientspecialPriceDtoPage = clientSpecialpriceService.
                findPageSpecialpriceofArticle(articleId, pagenum, pagesize);
        log.info("The method findPageSpecialpriceofArticle is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "ClientSpecialprice page found successfully");
        map.put("data", clientspecialPriceDtoPage);
        map.put("cause", "La liste des prix speciaux du client a ete retrouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllSpecialpriceofClient(Long clientId) {
        Map<String, Object> map = new LinkedHashMap<>();

        List<ClientSpecialpriceDto> clientspecialPriceDtoList = clientSpecialpriceService.
                findAllSpecialpriceofClient(clientId);
        log.info("The method findAllSpecialpriceofClient is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "ClientSpecialprice list found successfully");
        map.put("data", clientspecialPriceDtoList);
        map.put("cause", "La liste des prix speciaux du client a ete retrouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageSpecialpriceofClient(Long clientId, Optional<Integer> optpagenum,
                                                       Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Page<ClientSpecialpriceDto> clientspecialPriceDtoPage = clientSpecialpriceService.
                findPageSpecialpriceofClient(clientId, pagenum, pagesize);
        log.info("The method findPageSpecialpriceofClient is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "ClientSpecialprice page found successfully");
        map.put("data", clientspecialPriceDtoPage);
        map.put("cause", "La page des prix speciaux du client a ete retrouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllClientWithSpecialpriceonArticle(Long articleId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ClientDto> clientDtowithspecialpriceList = clientSpecialpriceService.
                findAllClientWithSpecialpriceonArticle(articleId);
        log.info("The method findAllClientWithSpecialpriceonArticle is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Client list with special price found successfully");
        map.put("data", clientDtowithspecialpriceList);
        map.put("cause", "La liste des prix speciaux du client a ete retrouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageClientWithSpecialpriceonArticle(Long articleId, Optional<Integer> optpagenum,
                                                                  Optional<Integer> optpagesize) {
        Map<String, Object> map = new LinkedHashMap<>();
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Page<ClientDto> clientDtowithspecialpricePage = clientSpecialpriceService.
                findPageClientWithSpecialpriceonArticle(articleId, pagenum, pagesize);
        log.info("The method findPageClientWithSpecialpriceonArticle is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Client page with special price found successfully");
        map.put("data", clientDtowithspecialpricePage);
        map.put("cause", "La page des prix speciaux du client a ete retrouve avec success");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getEffectiveSpecialPriceToApplied(Long clientId, Long articleId, BigDecimal qteCommand) {
        Map<String, Object> map = new LinkedHashMap<>();
        BigDecimal effectivePrice = clientSpecialpriceService.getEffectiveSpecialPriceToApplied(qteCommand, articleId, clientId);
        log.info("The method getEffectiveSpecialPriceToApplied is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "The effective price to apply is computed successfully");
        map.put("data", effectivePrice);
        map.put("cause", "Le prix effectif a applique a ete calcule avec success");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getCommonEffectivePriceToApplied(Long articleId, BigDecimal qteCommand) {
        Map<String, Object> map = new LinkedHashMap<>();
        BigDecimal effectivePrice = clientSpecialpriceService.getCommonEffectivePriceToApplied(qteCommand, articleId);
        log.info("The method getCommonEffectivePriceToApplied is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "The effective price to apply is computed successfully");
        map.put("data", effectivePrice);
        map.put("cause", "Le prix effectif a applique a ete calcule avec success");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteClientSpecialprice(Long cltspepriceId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete  =  clientSpecialpriceService.deleteClientSpecialprice(cltspepriceId);
        log.info("The method deleteClientSpecialprice is being executed");
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "The client special price deleted successfully");
        map.put("data", delete);
        map.put("cause", "Le prix special du client a ete supprime avec success");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
