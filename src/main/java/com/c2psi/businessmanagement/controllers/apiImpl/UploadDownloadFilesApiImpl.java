package com.c2psi.businessmanagement.controllers.apiImpl;

import com.c2psi.businessmanagement.controllers.api.UploadDownloadFilesApi;
import com.c2psi.businessmanagement.services.contracts.UploadDownloadFilesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@Slf4j
public class UploadDownloadFilesApiImpl implements UploadDownloadFilesApi {
    private UploadDownloadFilesService uploadDownloadFilesService;

    @Autowired
    public UploadDownloadFilesApiImpl(UploadDownloadFilesService uploadDownloadFilesService) {
        this.uploadDownloadFilesService = uploadDownloadFilesService;
    }

    @Override
    public ResponseEntity personsUploadFile(MultipartFile file) {
        log.info("controller personsUploadFile in execution");
        Map<String, Object> map = new LinkedHashMap<>();
        String message = "";
        try{
            String msg = uploadDownloadFilesService.saveImageofPerson(file);
            message = "Person image uploaded successfully: " + file.getOriginalFilename();

            map.clear();
            map.put("status", HttpStatus.OK);
            map.put("message", "The person image save method has been called sucessfully");
            map.put("data", msg);
            map.put("cause", "nothing happens");
            //return ResponseEntity.ok(map);
            return new ResponseEntity(map, HttpStatus.OK);
        }
        catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();

            map.clear();
            map.put("status", HttpStatus.EXPECTATION_FAILED);
            map.put("message", "Problem during uploading process");
            map.put("data", message);
            map.put("cause", e.getMessage());
            //return ResponseEntity.ok(map);
            return new ResponseEntity(map, HttpStatus.EXPECTATION_FAILED);
        }
        //return null;
    }

    @Override
    public ResponseEntity articlesUploadFile(MultipartFile file) {
        log.info("controller articlesUploadFile in execution");
        Map<String, Object> map = new LinkedHashMap<>();
        String message = "";
        try{
            String msg = uploadDownloadFilesService.saveImageofArticle(file);
            message = "Article image uploaded the file successfully: " + file.getOriginalFilename();

            map.clear();
            map.put("status", HttpStatus.OK);
            map.put("message", "The article image save method has been called sucessfully");
            map.put("data", msg);
            map.put("cause", "nothing happens");
            //return ResponseEntity.ok(map);
            return new ResponseEntity(map, HttpStatus.OK);
        }
        catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();

            map.clear();
            map.put("status", HttpStatus.EXPECTATION_FAILED);
            map.put("message", "Problem during uploading process");
            map.put("data", message);
            map.put("cause", e.getMessage());
            //return ResponseEntity.ok(map);
            return new ResponseEntity(map, HttpStatus.EXPECTATION_FAILED);
        }
        //return null;
    }

    @Override
    public ResponseEntity test() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "Nothing");
        map.put("data", "Just a test ");
        map.put("cause", "Nothing");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
