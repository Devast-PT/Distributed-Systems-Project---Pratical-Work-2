package com.mariocosta.controllers;


import com.mariocosta.clients.auth.dtos.AuthRegistrationRequest;
import com.mariocosta.clients.auth.dtos.AuthUserTokenCreateDto;
import com.mariocosta.clients.user.dtos.UserDto;
import com.mariocosta.servicies.UserServices;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserServices userServices;

    @PostMapping("/userExists")
    public ResponseEntity<Boolean> userExists(@RequestBody UserDto user){
        log.info("Got the following user to authenticate {}", user);
        return ResponseEntity.ok().body(
                this.userServices.doesUserExists(user)
        );
    }

    @PostMapping("/registerUser")
    public ResponseEntity<UserDto> save(@RequestBody AuthRegistrationRequest request){

        return ResponseEntity.ok().body(
                this.userServices.userRegistration(request)
        );
    }

    @PostMapping("/loginUser")
    public ResponseEntity<Boolean> userLogin(@RequestParam("username") String username,
                                             @RequestParam("password") String password){
        return ResponseEntity.ok().body(
                this.userServices.loginUser(username, password)
        );
    }

    @PostMapping("/userCreds")
    AuthUserTokenCreateDto getCreds(@RequestParam("username") String username){
        return this.userServices.getCreds(username);
    }


    @PostMapping("/turnAdmin")
    public String turningAUserIntoAdmin(@RequestParam("username") String username){
        log.info("turning admin " + username);
        return this.userServices.turningAUserIntoAdmin(username);
    }


}
