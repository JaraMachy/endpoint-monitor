package cz.machovec.endpointmonitor.commons;

import cz.machovec.endpointmonitor.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenTest {

    @Autowired
    protected JwtUtils jwtUtils;

    @Autowired
    protected UserDetailsService userDetailsService;

    private String badToken = "eyJhbGciOiJLMzUxMiJ9.eyJzdWIiOiJhZG1pbkBhZG1pbi5jeiIsImV4cCI6MTU3MzczNzg3MywiaWF0IjoxNTczNzM3NTczfQ.oMv68OEdsIJouM5s65srKRKf3F32Ogquq7eaEK4aEGFqCXoIcgexRlwICyewLcpxRvjbRXVhSnjYpIZDQHIewg";

    public String getCorrectToken(String username) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return "Bearer " + jwtUtils.generateAccessToken(userDetails);
    }

    public String getBadToken() {
        return badToken;
    }
}
