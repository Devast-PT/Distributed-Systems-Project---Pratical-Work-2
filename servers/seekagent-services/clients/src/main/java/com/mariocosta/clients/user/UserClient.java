package com.mariocosta.clients.user;

import com.mariocosta.clients.auth.dtos.AuthRegistrationRequest;
import com.mariocosta.clients.auth.dtos.AuthUserTokenCreateDto;
import com.mariocosta.clients.user.dtos.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user",
        path = "api/v1/user")
public interface UserClient {


    @PostMapping("/userExists")
    ResponseEntity<Boolean> userExists(@RequestBody UserDto user);

    @PostMapping("/registerUser")
    ResponseEntity<UserDto> save(@RequestBody AuthRegistrationRequest request);

    @PostMapping("/loginUser")
    ResponseEntity<Boolean> userLogin(@RequestParam("username") String username,
                                             @RequestParam("password") String password);

    @PostMapping("/userCreds")
    AuthUserTokenCreateDto getCreds(@RequestParam("username") String username);


    @PostMapping("/turnAdmin")
    String turningAUserIntoAdmin(@RequestParam("username") String username);
}
