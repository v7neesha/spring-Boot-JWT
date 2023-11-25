package com.example.springboottest.service;



import com.example.springboottest.entity.UserInfo;
import com.example.springboottest.repository.UserInfoRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LogoutService implements LogoutHandler {

    @Autowired
    JwtService jwtService;

    @Autowired
    UserInfoRepository userInfoRepository;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) {

        String authHeader = request.getHeader("Authorization");
        String token=null;
        String username = null;

        if(authHeader==null && !authHeader.startsWith("Bearer ")){
            return;
        }
        else {
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);
            UserInfo userInfo = userInfoRepository.findByName(username).get();
            userInfoRepository.delete(userInfo);


        }



    }
}

