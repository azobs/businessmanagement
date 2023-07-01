package com.c2psi.businessmanagement.controllers.api.client.client;

import com.c2psi.businessmanagement.dtos.client.client.ClientSpecialpriceDto;
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
@Api(APP_ROOT+"/clientspecialprice")
public interface ClientSpecialpriceApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = APP_ROOT + "/clientspecialprice/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "saveClientSpecialprice",
            notes = "This method is used to save special price for a client on an article in a pointofsale in the DB",
            response = ClientSpecialpriceDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Object ClientSpecialprice added successfully"),
            @ApiResponse(code = 400, message = "Object ClientSpecialprice is not valid during the saving process")
    })
    ResponseEntity saveClientSpecialprice(
            @ApiParam(name = "cltspepriceDto", type = "ClientSpecialpriceDto", required = true,
                    value = "The JSON object that represent the ClientSpecialpriceDto to save")
            @Valid @RequestBody ClientSpecialpriceDto cltspepriceDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = APP_ROOT + "/clientspecialprice/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "updateClientSpecialprice",
            notes = "This method is used to update special price for a client on an article in a pointofsale in the DB",
            response = ClientSpecialpriceDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Object ClientSpecialprice updated successfully"),
            @ApiResponse(code = 400, message = "Object ClientSpecialprice is not valid during the saving process")
    })
    ResponseEntity updateClientSpecialprice(
            @ApiParam(name = "cltspepriceDto", type = "ClientSpecialpriceDto", required = true,
                    value = "The JSON object that represent the ClientSpecialpriceDto to update")
            @Valid @RequestBody ClientSpecialpriceDto cltspepriceDto, BindingResult bindingResult);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT + "/clientspecialprice/id/{cltspepriceId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findClientPackagingAccountById", notes = "Search in the DB a clientspecialprice by its Id",
            response = ClientSpecialpriceDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The clientspecialprice with the id precised found successfully"),
            @ApiResponse(code = 404, message = "Error faced during the finding process")
    })
    ResponseEntity findClientSpecialpriceById(
            @ApiParam(name = "cltspepriceId", type = "Long", required = true,
                    value = "Id of the concerned ClientSpecialPriceDto", example = "1")
            @NotNull @PathVariable("cltspepriceId") Long cltspepriceId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT + "/clientspecialprice/of/{articleId}/{clientId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findClientSpecialpriceofArticleforClient", notes = "Search in the DB a clientspecialprice " +
            "of a client for an article",
            response = ClientSpecialpriceDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The clientspecialprice with the id precised found successfully"),
            @ApiResponse(code = 404, message = "Error faced during the finding process")
    })
    ResponseEntity findClientSpecialpriceofArticleforClient(
            @ApiParam(name = "articleId", type = "Long", required = true,
                    value = "Id of the concerned ArticleDto", example = "1")
            @NotNull @PathVariable("articleId") Long articleId,
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value = "Id of the concerned ClientDto", example = "1")
            @NotNull @PathVariable("clientId") Long clientId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT + "/clientspecialprice/article/all/{articleId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSpecialpriceofArticle", notes = "find all special price of an article",
            responseContainer = "List<ClientSpecialpriceDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The special price list of an article found successfully"),
            @ApiResponse(code = 404, message = "Error faced during the finding process")
    })
    ResponseEntity findAllSpecialpriceofArticle(
            @ApiParam(name = "articleId", type = "Long", required = true,
                    value = "Id of the concerned ArticleDto", example = "1")
            @NotNull @PathVariable("articleId") Long articleId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT + "/clientspecialprice/article/page/{articleId}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSpecialpriceofArticle", notes = "find all special price of an article page by page",
            responseContainer = "Page<ClientSpecialpriceDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The special price page of an article found successfully"),
            @ApiResponse(code = 404, message = "Error faced during the finding process")
    })
    ResponseEntity findPageSpecialpriceofArticle(
            @ApiParam(name = "articleId", type = "Long", required = true,
                    value = "Id of the concerned ArticleDto", example = "1")
            @NotNull @PathVariable("articleId") Long articleId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT + "/clientspecialprice/client/all/{clientId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllSpecialpriceofClient", notes = "find all special price of a client",
            responseContainer = "List<ClientSpecialpriceDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The special price list of a client found successfully"),
            @ApiResponse(code = 404, message = "Error faced during the finding process")
    })
    ResponseEntity findAllSpecialpriceofClient(
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value = "Id of the concerned ClientDto", example = "1")
            @NotNull @PathVariable("clientId") Long clientId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT + "/clientspecialprice/client/page/{clientId}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findPageSpecialpriceofClient", notes = "find all special price of a client page by page",
            responseContainer = "Page<ClientSpecialpriceDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The special price page of a client found successfully"),
            @ApiResponse(code = 404, message = "Error faced during the finding process")
    })
    ResponseEntity findPageSpecialpriceofClient(
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value = "Id of the concerned ClientDto", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT + "/clientspecialprice/client/article/all/{articleId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllClientWithSpecialpriceonArticle", notes = "find all client with special price on an article",
            responseContainer = "List<ClientDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The client list with special price on an article found successfully"),
            @ApiResponse(code = 404, message = "Error faced during the finding process")
    })
    ResponseEntity findAllClientWithSpecialpriceonArticle(
            @ApiParam(name = "articleId", type = "Long", required = true,
                    value = "Id of the concerned ArticleDto", example = "1")
            @NotNull @PathVariable("articleId") Long articleId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT + "/clientspecialprice/client/article/page/{articleId}/{pagenum}/{pagesize}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "findAllClientWithSpecialpriceonArticle", notes = "find all client with special price on an " +
            "article page by page",
            responseContainer = "Page<ClientDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The client list with special price on an article found successfully page by page"),
            @ApiResponse(code = 404, message = "Error faced during the finding process")
    })
    ResponseEntity findPageClientWithSpecialpriceonArticle(
            @ApiParam(name = "articleId", type = "Long", required = true,
                    value = "Id of the concerned ArticleDto", example = "1")
            @NotNull @PathVariable("articleId") Long articleId,
            @PathVariable(name = "pagenum", required = false) Optional<Integer> optpagenum,
            @PathVariable(name = "pagesize", required = false) Optional<Integer> optpagesize);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT + "/clientspecialprice/client/article/price/{clientId}/{articleId}/{qteCommand}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "getEffectiveSpecialPriceToApplied", notes = "get effective special price to apply",
            responseContainer = "BigDecimal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The special price to apply to a client on an article due to the quantity " +
                    "of article command"),
            @ApiResponse(code = 404, message = "Error faced during the finding process")
    })
    ResponseEntity getEffectiveSpecialPriceToApplied(
            @ApiParam(name = "clientId", type = "Long", required = true,
                    value = "Id of the concerned ClientDto", example = "1")
            @NotNull @PathVariable("clientId") Long clientId,
            @ApiParam(name = "articleId", type = "Long", required = true,
                    value = "Id of the concerned ArticleDto", example = "1")
            @NotNull @PathVariable("articleId") Long articleId,
            @ApiParam(name = "qteCommand", type = "BigDecimal", required = true,
                    value = "Quantity command", example = "0")
            @NotNull @PathVariable("qteCommand") BigDecimal qteCommand);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping(value = APP_ROOT + "/clientspecialprice/article/price/{articleId}/{qteCommand}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "getCommonEffectivePriceToApplied", notes = "get effective price to apply",
            responseContainer = "BigDecimal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The price to apply for an article due to the quantity " +
                    "of article command"),
            @ApiResponse(code = 404, message = "Error faced during the finding process")
    })
    ResponseEntity getCommonEffectivePriceToApplied(
            @ApiParam(name = "articleId", type = "Long", required = true,
                    value = "Id of the concerned ArticleDto", example = "1")
            @NotNull @PathVariable("articleId") Long articleId,
            @ApiParam(name = "qteCommand", type = "BigDecimal", required = true,
                    value = "Quantity command", example = "0")
            @NotNull @PathVariable("qteCommand") BigDecimal qteCommand);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping(value = APP_ROOT + "/clientspecialprice/delete/{cltspepriceId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "deleteClientSpecialprice",
            notes = "This method is used to delete a ClientSpecialPrice saved in the DB", response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Object ClientSpecialPrice deleted successfully")
    })
    ResponseEntity deleteClientSpecialprice(
            @ApiParam(name = "cltspepriceId", type = "Long", required = true,
                    value = "Id of the ClientSpecialPrice to delete", example = "1")
            @NotNull @PathVariable("cltspepriceId") Long cltspepriceId);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}