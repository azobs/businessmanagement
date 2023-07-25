package com.c2psi.businessmanagement.controllers.apiImpl.stock.product;

import com.c2psi.businessmanagement.controllers.api.stock.product.ArticleApi;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.services.contracts.stock.product.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
public class ArticleApiImpl implements ArticleApi {
    private ArticleService articleService;
    //private Map<String, Object> map;

    @Autowired
    public ArticleApiImpl(ArticleService articleService) {
        this.articleService = articleService;
        //map = new LinkedHashMap<>();
    }

    @Override
    public ResponseEntity findAllArticleofPos(Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();

        List<ArticleDto> ArticleDtoList = articleService.findAllArticleofPos(posId);
        log.info("The method findAllArticleofPos is being executed");
        //return ResponseEntity.ok(articleDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "article list in pos found successfully ");
        map.put("data", ArticleDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllArticleofPosOrderByCreationDate(Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();

        List<ArticleDto> ArticleDtoList = articleService.findAllArticleofPosOrderByCreationDate(posId);
        log.info("The method findAllArticleofPos is being executed");
        //return ResponseEntity.ok(articleDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "article list in pos found successfully ");
        map.put("data", ArticleDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageArticleofPos(Long posId, Optional<Integer> optpagenum,
                                               Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<ArticleDto> articleDtoPage = articleService.findPageArticleofPos(posId, pagenum, pagesize);
        log.info("The method findPageArticleofPos is being executed");
        //return ResponseEntity.ok(articleDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "article page in pos found successfully ");
        map.put("data", articleDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageArticleofPosOrderByCreationDate(Long posId, Optional<Integer> optpagenum,
                                                                  Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;

        Map<String, Object> map = new LinkedHashMap<>();

        Page<ArticleDto> articleDtoPage = articleService.findPageArticleofPosOrderByCreationDate(posId, pagenum, pagesize);
        log.info("The method findPageArticleofPos is being executed");
        //return ResponseEntity.ok(articleDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "article page in pos found successfully ");
        map.put("data", articleDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllArticleofCat(Long catId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ArticleDto> ArticleDtoList = articleService.findAllArticleofCat(catId);
        log.info("The method findAllArticleofCat is being executed");
        //return ResponseEntity.ok(articleDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "article list in category found successfully ");
        map.put("data", ArticleDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageArticleofCat(Long catId, Optional<Integer> optpagenum,
                                               Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<ArticleDto> articleDtoPage = articleService.findPageArticleofCat(catId, pagenum, pagesize);
        log.info("The method findPageArticleofCat is being executed");
        //return ResponseEntity.ok(articleDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "article page of category found successfully ");
        map.put("data", articleDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity saveArticle(ArticleDto artDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", artDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        ArticleDto ArticleDtoSaved = articleService.saveArticle(artDto);
        log.info("The method saveArticle is being executed");
        //return new ResponseEntity(articleDtoSaved, HttpStatus.CREATED);
        map.clear();
        map.put("status", HttpStatus.CREATED);
        map.put("message", "article created successfully ");
        map.put("data", ArticleDtoSaved);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateArticle(ArticleDto artDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", artDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        ArticleDto ArticleDtoUpdated = articleService.updateArticle(artDto);
        log.info("The method updateArticle is being executed");
        //return new ResponseEntity(articleDtoUpdated, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "article updated successfully ");
        map.put("data", ArticleDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity fixQuantityofArticle(ArticleDto artDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", artDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        ArticleDto ArticleDtoUpdated = articleService.fixQuantityofArticle(artDto);
        log.info("The method fixQuantityofArticle is being executed");
        //return new ResponseEntity(articleDtoUpdated, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "article quantity fixed successfully ");
        map.put("data", ArticleDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity updateUnitofArticle(ArticleDto artDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", artDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        ArticleDto ArticleDtoUpdated = articleService.updateUnitofArticle(artDto);
        log.info("The method updateUnitofArticle is being executed");
        //return new ResponseEntity(articleDtoUpdated, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "article unit updated successfully ");
        map.put("data", ArticleDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity updateBasePriceofArticle(ArticleDto artDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", artDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        ArticleDto ArticleDtoUpdated = articleService.updateBasePriceofArticle(artDto);
        log.info("The method updateBasePriceofArticle is being executed");
        //return new ResponseEntity(articleDtoUpdated, HttpStatus.OK);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "article baseprice updated successfully ");
        map.put("data", ArticleDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findArticleById(Long artId) {
        Map<String, Object> map = new LinkedHashMap<>();
        ArticleDto ArticleDtoFound = articleService.findArticleById(artId);
        log.info("The method findArticleById is being executed");
        //return ResponseEntity.ok(articleDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "article found successfully ");
        map.put("data", ArticleDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findArticleByCodeInPos(String artCode, Long posId) {
        Map<String, Object> map = new LinkedHashMap<>();
        ArticleDto ArticleDtoFound = articleService.findArticleByCodeInPos(artCode, posId);
        log.info("The method findArticleByCodeInPos is being executed");
        //return ResponseEntity.ok(articleDtoFound);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "article found successfully ");
        map.put("data", ArticleDtoFound);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findAllArticleofProviderInPos(Long posId, Long providerId) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<ArticleDto> ArticleDtoList = articleService.findAllArticleofProviderInPos(posId, providerId);
        log.info("The method findAllArticleofProviderInPos is being executed");
        //return ResponseEntity.ok(articleDtoList);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "article list of provider found successfully ");
        map.put("data", ArticleDtoList);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageofArticleofProviderInPos(Long posId, Long providerId, Optional<Integer> optpagenum,
                                                           Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<ArticleDto> articleDtoPage = articleService.findPageArticleofProviderInPos(posId, providerId, pagenum,
                pagesize);
        log.info("The method findPageofArticleofProviderInPos is being executed");
        //return ResponseEntity.ok(articleDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "article page of provider found successfully ");
        map.put("data", articleDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findPageArticleofPointofsaleContaining(Long posId, String sample, Optional<Integer> optpagenum,
                                                                 Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Map<String, Object> map = new LinkedHashMap<>();
        Page<ArticleDto> articleDtoPage = articleService.findPageArticleofPointofsaleContaining(posId, sample, pagenum,
                pagesize);
        log.info("The method findPageArticleofPointofsaleContaining is being executed");
        //return ResponseEntity.ok(articleDtoPage);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "article page that name contain key characters found successfully ");
        map.put("data", articleDtoPage);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity addQuantityofArticle(ArticleDto artDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", artDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }

        /****
         * The quantity in stock of the articleDto sent represent the value to add
         */
//        ArticleDto articleDtoUpdated = articleService.addQuantityofArticle(artDto.getId(),
//                artDto.getArtQuantityinstock());
        ArticleDto ArticleDtoUpdated = articleService.addQuantityofArticle(artDto.getId(),
                new BigDecimal(artDto.getArtQuantityinstock(), new MathContext(2)));
        log.info("The method addQuantityofArticle is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "article quantity in stock added successfully ");
        map.put("data", ArticleDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity reduceQuantityofArticle(ArticleDto artDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", artDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        /****
         * The quantity in stock of the articleDto sent represent the value to reduce
         */
//        ArticleDto articleDtoUpdated = articleService.reduceQuantityofArticle(artDto.getId(),
//                artDto.getArtQuantityinstock());
        ArticleDto ArticleDtoUpdated = articleService.reduceQuantityofArticle(artDto.getId(),
                new BigDecimal(artDto.getArtQuantityinstock(), new MathContext(2)));
        log.info("The method reduceQuantityofArticle is being executed");

        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "article quantity in stock reduced successfully ");
        map.put("data", ArticleDtoUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity addDamageArticleof(ArticleDto artDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", artDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        /****
         * The quantity in stock of the articleDto sent represent the value of damage article to add
         */

//        ArticleDto articleDamageAccountUpdated = articleService.addDamageArticleof(artDto.getId(),
//                artDto.getArtQuantityinstock());
        ArticleDto articleDamageAccountUpdated = articleService.addDamageArticleof(artDto.getId(),
                new BigDecimal(artDto.getArtQuantityinstock(), new MathContext(2)));
        log.info("The method reduceQuantityofArticle is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "damage article quantity in stock added successfully ");
        map.put("data", articleDamageAccountUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity reduceDamageArticle(ArticleDto artDto, BindingResult bindingResult) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", artDto, bindingResult);
            //return ResponseEntity.badRequest().body(bindingResult.toString());
            map.clear();
            map.put("status", HttpStatus.BAD_REQUEST);
            map.put("message", "Some data are not validated");
            map.put("data", bindingResult);
            map.put("cause", "Erreur de validation des donnees dans la requete envoyee");
            //return ResponseEntity.ok(map);
            return ResponseEntity.badRequest().body(map);
        }
        /****
         * The quantity in stock of the articleDto sent represent the value of damage article to reduce
         */
//        ArticleDto articleDamageAccountUpdated = articleService.reduceDamageArticle(artDto.getId(),
//                artDto.getArtQuantityinstock());
        ArticleDto articleDamageAccountUpdated = articleService.reduceDamageArticle(artDto.getId(),
                new BigDecimal(artDto.getArtQuantityinstock(), new MathContext(2)));
        log.info("The method reduceQuantityofArticle is being executed");
        //return null;
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "damage article quantity in stock reduced successfully ");
        map.put("data", articleDamageAccountUpdated);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getCommonEffectivePriceToApplied(Long artId, BigDecimal quantity) {
        Map<String, Object> map = new LinkedHashMap<>();
        BigDecimal effectivePrice = articleService.getCommonEffectivePriceToApplied(artId, quantity);
        log.info("The method getCommonEffectivePriceToApplied is being executed");
        //return ResponseEntity.ok(effectivePrice);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "article price to applied successfully computed in function of the quantity");
        map.put("data", effectivePrice);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteArticleById(Long artId) {
        Map<String, Object> map = new LinkedHashMap<>();
        Boolean delete = articleService.deleteArticleById(artId);
        log.info("The method deleteArticleById is being executed");
        //return ResponseEntity.ok(delete);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "article successfully deleted");
        map.put("data", delete);
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
