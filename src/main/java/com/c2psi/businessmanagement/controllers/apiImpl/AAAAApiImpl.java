package com.c2psi.businessmanagement.controllers.apiImpl;

import com.c2psi.businessmanagement.controllers.api.AAAAApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@Slf4j
public class AAAAApiImpl implements AAAAApi {
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
