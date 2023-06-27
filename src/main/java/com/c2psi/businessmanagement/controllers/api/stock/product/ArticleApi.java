package com.c2psi.businessmanagement.controllers.api.stock.product;

import com.c2psi.businessmanagement.dtos.stock.price.BasePriceDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Optional;

import static com.c2psi.businessmanagement.utils.Constants.APP_ROOT;

@Validated
@Api(APP_ROOT+"/article")
public interface ArticleApi {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/article/all/pos/{posId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllArticleofPos", notes = "Search all articles in a pointofsale",
            responseContainer = "List<ArticleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The list of article of pointofsale found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllArticleofPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/article/page/pos/{posId}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageArticleofPos", notes = "Search all articles in a pointofsale page by page",
            responseContainer = "Page<ArticleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The page of article of pointofsale found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageArticleofPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/article/all/cat/{catId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllArticleofCat", notes = "Search all articles in a category",
            responseContainer = "List<ArticleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The list of article of category found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllArticleofCat(
            @ApiParam(name = "catId", type = "Long", required = true,
                    value="Id of the concerned Category", example = "1")
            @NotNull @PathVariable("catId") Long catId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/article/page/cat/{catId}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageArticleofCat", notes = "Search all articles in a category page by page",
            responseContainer = "Page<ArticleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The page of article of category found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageArticleofCat(
            @ApiParam(name = "catId", type = "Long", required = true,
                    value="Id of the concerned category", example = "1")
            @NotNull @PathVariable("catId") Long catId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT+"/article/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveArticle",
            notes = "This method is used to save an article in the DB",
            response = ArticleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Article added successfully"),
            @ApiResponse(code=400, message="Object Article is not valid during the saving process")
    })
    ResponseEntity saveArticle(
            @ApiParam(name = "artDto", type = "ArticleDto", required = true,
                    value="The JSON object that represent the Article to save")
            @Valid @RequestBody ArticleDto artDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = APP_ROOT+"/article/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateArticle",
            notes = "This method is used to update an article in the DB",
            response = ArticleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Article updated successfully"),
            @ApiResponse(code=400, message="Object Article is not valid during the updating process")
    })
    ResponseEntity updateArticle(
            @ApiParam(name = "artDto", type = "ArticleDto", required = true,
                    value="The JSON object that represent the Article to update")
            @Valid @RequestBody ArticleDto artDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = APP_ROOT+"/article/update/stock",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "fixQuantityofArticle",
            notes = "This method is used to fix the quantity in stock of an article in the DB",
            response = ArticleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Article(stock) updated successfully"),
            @ApiResponse(code=400, message="Object Article(stock) is not valid during the updating process")
    })
    ResponseEntity fixQuantityofArticle(
            @ApiParam(name = "artDto", type = "ArticleDto", required = true,
                    value="The JSON object that represent the Article to update")
            @Valid @RequestBody ArticleDto artDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = APP_ROOT+"/article/update/unit",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateUnitofArticle",
            notes = "This method is used to update the unit of an article in the DB",
            response = ArticleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Article(Unit) updated successfully"),
            @ApiResponse(code=400, message="Object Article(Unit) is not valid during the updating process")
    })
    ResponseEntity updateUnitofArticle(
            @ApiParam(name = "artDto", type = "ArticleDto", required = true,
                    value="The JSON object that represent the Article to update")
            @Valid @RequestBody ArticleDto artDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = APP_ROOT+"/article/update/bprice",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateBasePriceofArticle",
            notes = "This method is used to update the base price of an article in the DB",
            response = ArticleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Article(BasePrice) updated successfully"),
            @ApiResponse(code=400, message="Object Article(BasePrice) is not valid during the updating process")
    })
    ResponseEntity updateBasePriceofArticle(
            @ApiParam(name = "artDto", type = "ArticleDto", required = true,
                    value="The JSON object that represent the Article to update")
            @Valid @RequestBody ArticleDto artDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/article/id/{artId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findArticleById", notes = "Search in the DB an article by its Id",
            response = ArticleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The article with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findArticleById(
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="Id of the concerned Article", example = "1")
            @NotNull @PathVariable("artId") Long artId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/article/code/{artCode}/{posId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findArticleByCodeInPos", notes = "Search in the DB an article by its Code and PosId",
            response = ArticleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The article with the id precised found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findArticleByCodeInPos(
            @ApiParam(name = "artCode", type = "String", required = true,
                    value="Code of the concerned Article", example = "1")
            @NotNull @PathVariable("artCode") String artCode,
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/article/all/pos/provider/{posId}/{providerId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllArticleofProviderInPos", notes = "Search in the DB all articles of a provider in Pos",
            responseContainer = "List<ArticleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The article list of a provider in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findAllArticleofProviderInPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "providerId", type = "Long", required = true,
                    value="Id of the concerned Provider", example = "1")
            @NotNull @PathVariable("providerId") Long providerId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/article/page/pos/provider/{posId}/{providerId}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageofArticleofProviderInPos", notes = "Search in the DB all articles of a provider in Pos page by page",
            responseContainer = "Page<ArticleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The article page of a provider in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageofArticleofProviderInPos(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "providerId", type = "Long", required = true,
                    value="Id of the concerned Provider", example = "1")
            @NotNull @PathVariable("providerId") Long providerId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/article/name/page/{posId}/{sample}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageofArticleofProviderInPos", notes = "Search in the DB all articles of a provider in Pos page by page",
            responseContainer = "Page<ArticleDto>")
    @ApiResponses(value={
            @ApiResponse(code=200, message="The article page of a provider in Pos found successfully"),
            @ApiResponse(code=404, message="Error faced during the finding process")
    })
    ResponseEntity findPageArticleofPointofsaleContaining(
            @ApiParam(name = "posId", type = "Long", required = true,
                    value="Id of the concerned Pointofsale", example = "1")
            @NotNull @PathVariable("posId") Long posId,
            @ApiParam(name = "sample", type = "String", value="Character contain in the article name")
            @PathVariable("sample") String sample,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*@GetMapping(value = APP_ROOT+"/article/stock/add/{artId}/{quantity}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "addQuantityofArticle", notes = "Add a quantity of article in stock",
            response = ArticleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The quantity of article with the id precised is added successfully"),
            @ApiResponse(code=404, message="Error faced during the adding quantity process")
    })
    ResponseEntity addQuantityofArticle(
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="If of the concerned Article", example = "1")
            @NotNull @PathVariable("artId") Long artId,
            @ApiParam(name = "quantity", type = "BigDecimal", required = true,
                    value="Quantity to add", example = "0")
            @NotNull @PathVariable("quantity") BigDecimal quantity);*/

    @PutMapping(value = APP_ROOT+"/article/update/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "addQuantityofArticle",
            notes = "This method is used to update the quantity of an article in the DB by adding it",
            response = ArticleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Article(quantity in stock) added successfully"),
            @ApiResponse(code=400, message="Object Article(quantity in stock) is not valid during the updating process")
    })
    ResponseEntity addQuantityofArticle(
            @ApiParam(name = "artDto", type = "ArticleDto", required = true,
                    value="The JSON object that represent the Article to update")
            @Valid @RequestBody ArticleDto artDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*@GetMapping(value = APP_ROOT+"/article/stock/reduce/{artId}/{quantity}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "reduceQuantityofArticle", notes = "Reduce a quantity of article in stock",
            response = ArticleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The quantity of article with the id precised is reduced successfully"),
            @ApiResponse(code=404, message="Error faced during the reducing quantity process")
    })
    ResponseEntity reduceQuantityofArticle(
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="If of the concerned Article", example = "1")
            @NotNull @PathVariable("artId") Long artId,
            @ApiParam(name = "quantity", type = "BigDecimal", required = true,
                    value="Quantity to add", example = "0")
            @NotNull @PathVariable("quantity") BigDecimal quantity);*/

    @PutMapping(value = APP_ROOT+"/article/update/reduce",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "reduceQuantityofArticle",
            notes = "This method is used to update the quantity of an article in the DB by reducing it",
            response = ArticleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object Article(quantity in stock) reduced successfully"),
            @ApiResponse(code=400, message="Object Article(quantity in stock) is not valid during the updating process")
    })
    ResponseEntity reduceQuantityofArticle(
            @ApiParam(name = "artDto", type = "ArticleDto", required = true,
                    value="The JSON object that represent the Article to update")
            @Valid @RequestBody ArticleDto artDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*@GetMapping(value = APP_ROOT+"/article/damage/add/{artId}/{quantity}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "addDamageArticleof", notes = "Add a damage quantity of article in stock",
            response = ArticleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The damage quantity of article with the id precised is added successfully"),
            @ApiResponse(code=404, message="Error faced during the adding damage quantity process")
    })
    ResponseEntity addDamageArticleof(
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="If of the concerned Article", example = "1")
            @NotNull @PathVariable("artId") Long artId,
            @ApiParam(name = "quantity", type = "BigDecimal", required = true,
                    value="Quantity to add", example = "0")
            @NotNull @PathVariable("quantity") BigDecimal quantity);*/

    @PutMapping(value = APP_ROOT+"/article/damage/update/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "addDamageArticleof",
            notes = "Add a damage quantity of article in stock",
            response = ArticleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The damage quantity of article with the id precised is added successfully"),
            @ApiResponse(code=400, message="Error faced during the adding damage quantity process")
    })
    ResponseEntity addDamageArticleof(
            @ApiParam(name = "artDto", type = "ArticleDto", required = true,
                    value="The JSON object that represent the Article to update")
            @Valid @RequestBody ArticleDto artDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*@GetMapping(value = APP_ROOT+"/article/damage/reduce/{artId}/{quantity}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "reduceDamageArticle", notes = "Reduce a damage quantity of article in stock",
            response = ArticleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The damage quantity of article with the id precised is reduced successfully"),
            @ApiResponse(code=404, message="Error faced during the reducing damage quantity process")
    })
    ResponseEntity reduceDamageArticle(
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="If of the concerned Article", example = "1")
            @NotNull @PathVariable("artId") Long artId,
            @ApiParam(name = "quantity", type = "BigDecimal", required = true,
                    value="Quantity to add", example = "0")
            @NotNull @PathVariable("quantity") BigDecimal quantity);*/

    @PutMapping(value = APP_ROOT+"/article/damage/update/reduce",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "reduceDamageArticle",
            notes = "Reduce a damage quantity of article in stock",
            response = ArticleDto.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The damage quantity of article with the id precised is reduced successfully"),
            @ApiResponse(code=400, message="Error faced during the adding damage quantity process")
    })
    ResponseEntity reduceDamageArticle(
            @ApiParam(name = "artDto", type = "ArticleDto", required = true,
                    value="The JSON object that represent the Article to update")
            @Valid @RequestBody ArticleDto artDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT+"/article/price/{artId}/{quantity}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "getCommonEffectivePriceToApplied", notes = "Get the effective price to applied according to the quantity command",
            response = BigDecimal.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="The effectve price of the article is computed successfully"),
            @ApiResponse(code=404, message="Error faced during the computing process")
    })
    ResponseEntity getCommonEffectivePriceToApplied(
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="If of the concerned Article", example = "1")
            @NotNull @PathVariable("artId") Long artId,
            @ApiParam(name = "quantity", type = "BigDecimal", required = true,
                    value="Quantity to add", example = "0")
            @NotNull @PathVariable("quantity") BigDecimal quantity);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT+"/article/delete/id/{artId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteArticleById",
            notes = "This method is used to delete an article saved in the DB", response = Boolean.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object article deleted successfully")
    })
    ResponseEntity deleteArticleById(
            @ApiParam(name = "artId", type = "Long", required = true,
                    value="Id of the article to delete", example = "1")
            @NotNull @PathVariable("artId") Long artId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////







































































}
