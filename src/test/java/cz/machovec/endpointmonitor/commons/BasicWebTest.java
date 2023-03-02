package cz.machovec.endpointmonitor.commons;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class BasicWebTest {

    @Autowired
    protected TestRestTemplate restTemplate;

    //~ Protected methods

    protected HttpHeaders httpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Charset", "utf-8");
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    protected HttpHeaders httpHeaders(String token) {
        HttpHeaders headers = httpHeaders();
        headers.add("Authorization", token);
        return headers;
    }
}
