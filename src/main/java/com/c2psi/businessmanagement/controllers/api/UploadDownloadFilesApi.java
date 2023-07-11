package com.c2psi.businessmanagement.controllers.api;

import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

import static com.c2psi.businessmanagement.utils.Constants.*;

@Validated
@Api(IMAGE_UPLOAD_ENDPOINT)
public interface UploadDownloadFilesApi {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping(value = PERSONS_IMAGE_UPLOAD_ENDPOINT,
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "uploadFile",
            notes = "This method is used to save a base price for articles in the DB",
            response = String.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="Object BasePrice added successfully"),
            @ApiResponse(code=400, message="Object BasePrice is not valid during the saving process")
    })
    ResponseEntity personsUploadFile(
            @ApiParam(name = "file", type = "MultipartFile ", required = true,
                    value="The JSON object that represent the file to upload")
            @RequestPart("file") @NotNull MultipartFile file);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
