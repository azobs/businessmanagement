package com.c2psi.businessmanagement.controllers.auth;

import com.c2psi.businessmanagement.dtos.auth.AuthRequest;
import com.c2psi.businessmanagement.dtos.auth.AuthResponse;
import com.c2psi.businessmanagement.models.auth.ExtendedUser;
import com.c2psi.businessmanagement.services.auth.ApplicationUserDetailsService;
import com.c2psi.businessmanagement.utils.JwtUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.c2psi.businessmanagement.utils.Constants.*;

@RestController
//@RequestMapping(APP_ROOT)
@Slf4j
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private ApplicationUserDetailsService userDetailsService;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    ApplicationUserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping(value = AUTHENTICATION_ENDPOINT,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "authenticate user",
            notes = "This method is used to authenticate a user in the DB",
            response = AuthResponse.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message="User authenticate successfully"),
            @ApiResponse(code=400, message="username and/or password is/are inconrrect")
    })
    public ResponseEntity authenticate(@RequestBody @Valid AuthRequest request){
        log.info("execution de la methode authenticate");
        Map<String, Object> map = new LinkedHashMap<>();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserToken(),
                request.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserToken());

        final String jwtToken = jwtUtil.generateToken((ExtendedUser) userDetails);

        log.info("The method authenticate is being executed");
        //return new ResponseEntity(clientDtoSaved, HttpStatus.CREATED);
        map.clear();
        map.put("status", HttpStatus.OK);
        map.put("message", "User authenticated successfully");
        map.put("data", AuthResponse.builder().accessToken(jwtToken).build());
        map.put("cause", "RAS");
        return new ResponseEntity(map, HttpStatus.OK);
    }
}
