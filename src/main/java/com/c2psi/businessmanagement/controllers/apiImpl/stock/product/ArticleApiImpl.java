package com.c2psi.businessmanagement.controllers.apiImpl.stock.product;

import com.c2psi.businessmanagement.controllers.api.stock.product.ArticleApi;
import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.services.contracts.stock.price.BasePriceService;
import com.c2psi.businessmanagement.services.contracts.stock.product.ArticleService;
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
public class ArticleApiImpl implements ArticleApi {
    private ArticleService articleService;

    @Autowired
    public ArticleApiImpl(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Override
    public ResponseEntity findAllArticleofPos(Long posId) {
        List<ArticleDto> articleDtoList = articleService.findAllArticleofPos(posId);
        log.info("The method findAllArticleofPos is being executed");
        return ResponseEntity.ok(articleDtoList);
    }

    @Override
    public ResponseEntity findPageArticleofPos(Long posId, Optional<Integer> optpagenum,
                                               Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<ArticleDto> articleDtoPage = articleService.findPageArticleofPos(posId, pagenum, pagesize);
        log.info("The method findPageArticleofPos is being executed");
        return ResponseEntity.ok(articleDtoPage);
    }

    @Override
    public ResponseEntity findAllArticleofCat(Long catId) {
        List<ArticleDto> articleDtoList = articleService.findAllArticleofCat(catId);
        log.info("The method findAllArticleofCat is being executed");
        return ResponseEntity.ok(articleDtoList);
    }

    @Override
    public ResponseEntity findPageArticleofCat(Long catId, Optional<Integer> optpagenum,
                                               Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<ArticleDto> articleDtoPage = articleService.findPageArticleofCat(catId, pagenum, pagesize);
        log.info("The method findPageArticleofCat is being executed");
        return ResponseEntity.ok(articleDtoPage);
    }

    @Override
    public ResponseEntity saveArticle(ArticleDto artDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", artDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }
        ArticleDto articleDtoSaved = articleService.saveArticle(artDto);
        log.info("The method saveArticle is being executed");
        return new ResponseEntity(articleDtoSaved, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateArticle(ArticleDto artDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", artDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }
        ArticleDto articleDtoUpdated = articleService.updateArticle(artDto);
        log.info("The method updateArticle is being executed");
        return new ResponseEntity(articleDtoUpdated, HttpStatus.OK);
    }

    @Override
    public ResponseEntity fixQuantityofArticle(ArticleDto artDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", artDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }
        ArticleDto articleDtoUpdated = articleService.fixQuantityofArticle(artDto);
        log.info("The method fixQuantityofArticle is being executed");
        return new ResponseEntity(articleDtoUpdated, HttpStatus.OK);
    }

    @Override
    public ResponseEntity updateUnitofArticle(ArticleDto artDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", artDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }
        ArticleDto articleDtoUpdated = articleService.updateUnitofArticle(artDto);
        log.info("The method updateUnitofArticle is being executed");
        return new ResponseEntity(articleDtoUpdated, HttpStatus.OK);
    }

    @Override
    public ResponseEntity updateBasePriceofArticle(ArticleDto artDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in argument {} " +
                    "and the report errors are {}", artDto, bindingResult);
            return ResponseEntity.badRequest().body(bindingResult.toString());
        }
        ArticleDto articleDtoUpdated = articleService.updateBasePriceofArticle(artDto);
        log.info("The method updateBasePriceofArticle is being executed");
        return new ResponseEntity(articleDtoUpdated, HttpStatus.OK);
    }

    @Override
    public ResponseEntity findArticleById(Long artId) {
        ArticleDto articleDtoFound = articleService.findArticleById(artId);
        log.info("The method findArticleById is being executed");
        return ResponseEntity.ok(articleDtoFound);
    }

    @Override
    public ResponseEntity findArticleByCodeInPos(String artCode, Long posId) {
        ArticleDto articleDtoFound = articleService.findArticleByCodeInPos(artCode, posId);
        log.info("The method findArticleByCodeInPos is being executed");
        return ResponseEntity.ok(articleDtoFound);
    }

    @Override
    public ResponseEntity findAllArticleofProviderInPos(Long posId, Long providerId) {
        List<ArticleDto> articleDtoList = articleService.findAllArticleofProviderInPos(posId, providerId);
        log.info("The method findAllArticleofProviderInPos is being executed");
        return ResponseEntity.ok(articleDtoList);
    }

    @Override
    public ResponseEntity findPageofArticleofProviderInPos(Long posId, Long providerId, Optional<Integer> optpagenum,
                                                           Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<ArticleDto> articleDtoPage = articleService.findPageArticleofProviderInPos(posId, providerId, pagenum,
                pagesize);
        log.info("The method findPageofArticleofProviderInPos is being executed");
        return ResponseEntity.ok(articleDtoPage);
    }

    @Override
    public ResponseEntity findPageArticleofPointofsaleContaining(Long posId, String sample, Optional<Integer> optpagenum,
                                                                 Optional<Integer> optpagesize) {
        int pagenum = optpagenum.isPresent()?optpagenum.get():0;
        int pagesize = optpagesize.isPresent()?optpagesize.get():1;
        Page<ArticleDto> articleDtoPage = articleService.findPageArticleofPointofsaleContaining(posId, sample, pagenum,
                pagesize);
        log.info("The method findPageArticleofPointofsaleContaining is being executed");
        return ResponseEntity.ok(articleDtoPage);
    }

    @Override
    public ResponseEntity addQuantityofArticle(Long artId, BigDecimal quantity) {
        ArticleDto articleDtoUpdated = articleService.addQuantityofArticle(artId, quantity);
        log.info("The method addQuantityofArticle is being executed");
        return ResponseEntity.ok(articleDtoUpdated);
    }

    @Override
    public ResponseEntity reduceQuantityofArticle(Long artId, BigDecimal quantity) {
        ArticleDto articleDtoUpdated = articleService.reduceQuantityofArticle(artId, quantity);
        log.info("The method reduceQuantityofArticle is being executed");
        return ResponseEntity.ok(articleDtoUpdated);
    }

    @Override
    public ResponseEntity addDamageArticleof(Long artId, BigDecimal quantity) {
        ArticleDto articleDtoUpdated = articleService.addDamageArticleof(artId, quantity);
        log.info("The method reduceQuantityofArticle is being executed");
        return ResponseEntity.ok(articleDtoUpdated);
    }

    @Override
    public ResponseEntity reduceDamageArticle(Long artId, BigDecimal quantity) {
        ArticleDto articleDtoUpdated = articleService.reduceDamageArticle(artId, quantity);
        log.info("The method reduceQuantityofArticle is being executed");
        return ResponseEntity.ok(articleDtoUpdated);
    }

    @Override
    public ResponseEntity getCommonEffectivePriceToApplied(Long artId, BigDecimal quantity) {
        BigDecimal effectivePrice = articleService.getCommonEffectivePriceToApplied(artId, quantity);
        log.info("The method getCommonEffectivePriceToApplied is being executed");
        return ResponseEntity.ok(effectivePrice);
    }

    @Override
    public ResponseEntity deleteArticleById(Long artId) {
        Boolean delete = articleService.deleteArticleById(artId);
        log.info("The method deleteArticleById is being executed");
        return ResponseEntity.ok(delete);
    }
}
