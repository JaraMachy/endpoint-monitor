package cz.machovec.endpointmonitor.security;

import cz.machovec.endpointmonitor.commons.BasicWebTest;
import cz.machovec.endpointmonitor.security.AuthenticationResource.AuthenticationResTo;
import cz.machovec.endpointmonitor.security.AuthenticationResource.AuthenticationReqTo;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class JwtAuthenticationResourceTest extends BasicWebTest {

    @Test
    public void testLogin_correct() {
        String url = "/authenticate";
        AuthenticationReqTo reqTo;
        HttpEntity<?> request;
        ResponseEntity<?> resp;

        reqTo = new AuthenticationReqTo();
        reqTo.setUsername("Tyrion Lannister");
        reqTo.setPassword("12345");

        // Setup
        request = new HttpEntity<>(reqTo, httpHeaders());

        // When
        resp = restTemplate.exchange(url, HttpMethod.POST, request, AuthenticationResTo.class);

        // Then
        assertNotNull(resp);
        assertNotNull(resp.getBody());
        assertEquals(HttpStatus.OK, resp.getStatusCode());
    }

    @Test
    public void testLogin_invalidCredentials() {
        String url = "/authenticate";
        AuthenticationReqTo reqTo;
        HttpEntity<?> request;
        ResponseEntity<?> resp;

        reqTo = new AuthenticationReqTo();
        reqTo.setUsername("Tyrion Lannister");
        reqTo.setPassword("wrong password");

        // Setup
        request = new HttpEntity<>(reqTo, httpHeaders());

        // When
        resp = restTemplate.exchange(url, HttpMethod.POST, request, AuthenticationResTo.class);

        // Then
        assertNotNull(resp);
        assertNotNull(resp.getBody());
        assertEquals(HttpStatus.UNAUTHORIZED, resp.getStatusCode());
    }

}
