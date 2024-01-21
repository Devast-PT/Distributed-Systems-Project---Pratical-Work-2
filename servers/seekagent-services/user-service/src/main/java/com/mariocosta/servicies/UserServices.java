package com.mariocosta.servicies;


import com.mariocosta.clients.auth.AuthClient;
import com.mariocosta.clients.auth.dtos.AuthRegistrationRequest;
import com.mariocosta.clients.auth.dtos.AuthUserTokenCreateDto;
import com.mariocosta.clients.user.dtos.UserDto;
import com.mariocosta.entities.Userr;
import com.mariocosta.entities.mappers.UserMapper;
import com.mariocosta.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class UserServices {

    private final AuthClient authClient;
    private final UserMapper userMapper;
    private final UserRepository userRepository;


    public Boolean doesUserExists(UserDto userDto){
      if (this.userRepository.findByEmail(
              userDto.getEmail()
      ) == null)
          return false;
      return this.userRepository.findByUsername(
                userDto.getUsername()
        ) != null;

    };

    public UserDto userRegistration(AuthRegistrationRequest request){
        Userr userr = Userr.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(request.getPassword())
                .registerDate(LocalDateTime.now())
                .role("USER")
                .build();
        this.userRepository.saveAndFlush(userr);
        return this.userMapper.UserToUserDto(userr);
    }

    public Boolean loginUser(String username, String password) {

        Userr user = this.userRepository.findByUsername(username);
        if (user == null)
            throw new RuntimeException("doesnt exist username");
        return BCrypt.checkpw(password, user.getPassword());
    }

    public AuthUserTokenCreateDto getCreds(String username) {
        Userr user = this.userRepository.findByUsername(username);
        log.info("-> creds {}", user );
        AuthUserTokenCreateDto dto = AuthUserTokenCreateDto.builder()
                .id(user.getId())
                .role(user.getRole())
                .build();
        log.info("-> dto {}", dto );
        return dto;
    }

    public String turningAUserIntoAdmin(String username) {
        Userr user = this.userRepository.findByUsername(username);
        user.setRole("ADMIN");
        String awner = user.getUsername() + " with email address of " + user.getEmail() + " turned " + user.getRole();
        this.userRepository.saveAndFlush(user);
        return awner;
    }
}
