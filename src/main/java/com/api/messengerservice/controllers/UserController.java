package com.api.messengerservice.controllers;

import com.api.messengerservice.dtos.UserDTO;
import com.api.messengerservice.security.JwtUtils;
import com.api.messengerservice.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.StringToClassMapItem;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Operation(summary = "Endpoint to log in",description = "In the body of the request it receives a Json with the user's email and password.")
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Schema(
            type = "object",
            properties = {
                    @StringToClassMapItem(key = "email",value = String.class),
                    @StringToClassMapItem(key = "password",value = String.class)
            }
    ) Map<String,String> loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.get("email"), loginRequest.get("password"))
            );
            String token = JwtUtils.createToken(loginRequest.get("email"));
            return ResponseEntity.ok(JwtUtils.getAuthentication(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication error: " + e.getMessage());
        }
    }
    @Operation(summary = "Endpoint to create a user",description = "In the body of the request it receives a Json with the user data needed to create it. " +
            "Remember that there are only two types of user (ADMIN and USER) and only an admin registered in the database can create other admin users. Users of type 'USER' do not require the 'idAdmin' field to be sent.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Created. Shipment successfully created"),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Must be authorized"),
            @ApiResponse(responseCode = "404", description = "Not found. You cannot create a user of type admin because the idAdmin does not exist"),
            @ApiResponse(responseCode = "409", description = "Conflict. The values cannot be null or the user already exists")})
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserDTO user){
       return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(user));
    }

}
