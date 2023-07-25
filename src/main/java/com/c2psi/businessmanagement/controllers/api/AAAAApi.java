package com.c2psi.businessmanagement.controllers.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;

import static com.c2psi.businessmanagement.utils.Constants.*;

@Validated
@Api(ANOTHER_TEST_ENDPOINT)
public interface AAAAApi {
    @GetMapping(value = ANOTHER_TEST_ENDPOINT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "test", notes = "test", response = BigDecimal.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Test executed successfully"),
            @ApiResponse(code=404, message="Error faced during the test process")
    })
    ResponseEntity test();
}
