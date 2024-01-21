package com.mariocosta.services;

import com.mariocosta.clients.auth.dtos.AuthRegistrationRequest;
import com.mariocosta.clients.auth.dtos.AuthTokenAcessRefreshResponse;
import com.mariocosta.clients.auth.dtos.AuthUserTokenCreateDto;
import com.mariocosta.clients.user.UserClient;
import com.mariocosta.clients.user.dtos.UserDto;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserClient userClient;
    private final JwtUtil jwtUtil;


    public String register(AuthRegistrationRequest request){
        UserDto exists = UserDto.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .build();

        request.setPassword(
                BCrypt.hashpw(
                        request.getPassword(),
                        BCrypt.gensalt()
                )
        );

        UserDto registeredUser = userClient.save(request).getBody();

        return "Registration Complete";
    }

    public AuthTokenAcessRefreshResponse login(AuthRegistrationRequest request) {

        if (!Boolean.TRUE.equals(userClient.userLogin(
                request.getUsername(),
                request.getPassword()).getBody()))
            throw new RuntimeException("Invalid Username or password");

        AuthUserTokenCreateDto userToken = userClient.getCreds(request.getUsername());
        log.info("auth {}", userToken);
        String accessToken = jwtUtil.generate(
                Long.toString(userToken.getId()),
                userToken.getRole(),
                "ACCESS"
        );
        String refreshToken = jwtUtil.generate(
                Long.toString(userToken.getId()),
                userToken.getRole(),
                "REFRESH"
        );
        return AuthTokenAcessRefreshResponse.builder()
                .accessToken(
                        accessToken
                )
                .refreshToken(
                        refreshToken
                ).build();
    }

    public AuthTokenAcessRefreshResponse refresh(String refreshToken) {
        log.info("Request to fresh token with date to : {}", this.jwtUtil.getExpirationDateFromRefresh(refreshToken));

        if (jwtUtil.isExpiredRefresh(refreshToken))
            throw new RuntimeException("Token is expired");

        Claims claims = this.jwtUtil.getClaimsRefresh(refreshToken);

        log.info("claims {}", claims);

        String newAccessToken = jwtUtil.buildToken(this.jwtUtil.getClaimsRefresh(refreshToken), "ACCESS");

        String newRefreshToken = jwtUtil.buildToken(this.jwtUtil.getClaimsRefresh(refreshToken), "REFRESH");

        return AuthTokenAcessRefreshResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();

    }

    public boolean verifyToken(String authorizationHeader) {
        log.info("{}", authorizationHeader);
        String token = this.extractToken(authorizationHeader);
        log.info("{}", token);
        return this.isValidTokenAndHasAdminRole(token);
    }

    private String extractToken(String authorizationHeader) {
        // Lógica para extrair o token do cabeçalho Authorization
        // Exemplo: "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
        // Deve retornar apenas o token, sem o prefixo "Bearer "
        return authorizationHeader.replace("Bearer ", "");
    }
    private boolean isValidTokenAndHasAdminRole(String token) {
        Claims claims = this.jwtUtil.getClaimsRefresh(token);
        String role = (String) claims.get("role");
        log.info("in claims {}", role);

        return role.equals("ADMIN");

    }

    public String turnAdmin(String username) {
        return this.userClient.turningAUserIntoAdmin(username);
    }
}

/*         String accessToken = jwtUtil.generate(
                registeredUser.getId(),
                registeredUser.getRole(),
                "ACCESS"
        );
        String refreshToken = jwtUtil.generate(
                registeredUser.getId(),
                registeredUser.getRole(),
                "ACCESS"
        ); */
