package com.openclassrooms.mddapi.services.auth;

import com.openclassrooms.mddapi.dto.auth.LoginRequestDto;
import com.openclassrooms.mddapi.models.Token;
import com.openclassrooms.mddapi.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public Token authenticate(LoginRequestDto request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );
        
        if (auth == null || !auth.isAuthenticated()) {
            throw new BadCredentialsException("Invalid username or password");
        }


        User user = (User) auth.getPrincipal();
        return generateToken(user);
    }

    public Token generateToken(User user) {

        String jwtToken = jwtService.generateToken(user);

        return Token.builder()
                .token(jwtToken)
                .user(user)
                .build();
    }

}