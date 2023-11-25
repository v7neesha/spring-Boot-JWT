package com.example.springboottest.service;

import com.example.springboottest.config.UserInfoUserDetailsService;
import com.example.springboottest.entity.RefreshToken;
import com.example.springboottest.repository.RefreshTokenRepository;
import com.example.springboottest.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository tokenRepository;

    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    UserInfoUserDetailsService userDetailsService;
    @Autowired
    JwtService jwtService;


    public RefreshToken createRefreshToken(String username){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiry_date(Instant.now().minusMillis(600000));//10 minutes
        refreshToken.setUserInfo(userInfoRepository.findByName(username).get());
        tokenRepository.save(refreshToken);
        return refreshToken;
    }

    public Optional<RefreshToken> findByToken(String token){
        Optional<RefreshToken> refreshToken = tokenRepository.findByToken(token);
        return refreshToken;
    }

    public RefreshToken expiryToken(RefreshToken refreshToken){
        if (refreshToken.getExpiry_date().compareTo(Instant.now()) < 0){
            tokenRepository.delete(refreshToken);
            throw new RuntimeException(refreshToken.getToken() + "Refresh Token ");
        }
        return refreshToken;

    }


}

