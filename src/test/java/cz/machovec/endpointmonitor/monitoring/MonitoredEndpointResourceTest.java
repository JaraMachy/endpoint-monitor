package cz.machovec.endpointmonitor.monitoring;
import cz.machovec.endpointmonitor.commons.BasicWebTest;
import cz.machovec.endpointmonitor.commons.JwtTokenTest;
import cz.machovec.endpointmonitor.commons.repo.RepoUtils;
import cz.machovec.endpointmonitor.monitoring.MonitoredEndpointResource.GetMonitoredEndpointResTo;
import cz.machovec.endpointmonitor.monitoring.MonitoredEndpointResource.SaveMonitoredEndpointReqTo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class MonitoredEndpointResourceTest extends BasicWebTest {

    @Autowired
    protected JwtTokenTest jwtTokenTest;

    @Autowired
    protected MonitoredEndpointRepository repo;

    @Test
    public void testGetMonitoredEndpoint_correct() {
        HttpEntity<?> request;
        ResponseEntity<GetMonitoredEndpointResTo> resp;
        GetMonitoredEndpointResTo resTo;

        // Setup
        String url = "/api/monitored-endpoints/1000";
        String correctToken = jwtTokenTest.getCorrectToken("Tyrion Lannister");
        request = new HttpEntity<>(httpHeaders(correctToken));

        // When
        resp = restTemplate.exchange(url, HttpMethod.GET, request, GetMonitoredEndpointResTo.class);

        // Then
        assertNotNull(resp);
        assertEquals(HttpStatus.OK, resp.getStatusCode());

        resTo = resp.getBody();
        assertNotNull(resTo);
        assertEquals(1000L, resTo.getId());
        assertEquals("Tipsport", resTo.getName());
        assertEquals("https://www.tipsport.cz/", resTo.getUrl());
        assertEquals(20, resTo.getMonitoredInterval());
        assertEquals(2000L, resTo.getOwner().getId());
        assertEquals("Tyrion Lannister", resTo.getOwner().getUsername());
    }

    @Test
    public void testGetMonitoredEndpoint_notAuthorized() {
        HttpEntity<?> request;
        ResponseEntity<GetMonitoredEndpointResTo> resp;

        // Setup
        String url = "/api/monitored-endpoints/1000";
        String correctTokenForWrongUser = jwtTokenTest.getCorrectToken("Aria Stark");
        request = new HttpEntity<>(httpHeaders(correctTokenForWrongUser));

        // When
        resp = restTemplate.exchange(url, HttpMethod.GET, request, GetMonitoredEndpointResTo.class);

        // Then
        assertNotNull(resp);
        assertEquals(HttpStatus.FORBIDDEN, resp.getStatusCode());
    }

    @Test
    public void testGetMonitoredEndpoint_withoutToken(){
        HttpEntity<?> request;
        ResponseEntity<GetMonitoredEndpointResTo> resp;

        // Setup
        String url = "/api/monitored-endpoints/1000";
        request = new HttpEntity<>(httpHeaders());

        // When
        resp = restTemplate.exchange(url, HttpMethod.GET, request, GetMonitoredEndpointResTo.class);

        // Then
        assertNotNull(resp);
        assertEquals(HttpStatus.FORBIDDEN, resp.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testCreateMonitoredEndpoint_correct(){
        SaveMonitoredEndpointReqTo reqTo;
        HttpEntity<SaveMonitoredEndpointReqTo> request;
        ResponseEntity<?> resp;

        // Setup
        String url = "/api/monitored-endpoints";
        String correctToken = jwtTokenTest.getCorrectToken("Tyrion Lannister");
        reqTo = saveMonitoredEndpointReqTo();
        request = new HttpEntity<>(reqTo, httpHeaders(correctToken));

        // When
        resp = restTemplate.exchange(url, HttpMethod.POST, request, Void.class);

        // Then
        assertNotNull(resp);
        assertEquals(HttpStatus.CREATED, resp.getStatusCode());

        assertNotNull(resp.getHeaders());
        assertNotNull(resp.getHeaders().getLocation());
        String location = resp.getHeaders().getLocation().toString();
        Long createdId = getIdFromUri(location);
        assertNotNull(createdId);

        MonitoredEndpoint createdEndpoint = RepoUtils.mustFindOneById(createdId, repo);
        assertNotNull(createdEndpoint);
        assertEquals("ČSFD", createdEndpoint.getName());
        assertEquals("https://www.csfd.cz/", createdEndpoint.getUrl());
        assertEquals(12, createdEndpoint.getMonitoredInterval());

    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testCreateMonitoredEndpoint_incorrectUrl(){
        SaveMonitoredEndpointReqTo reqTo;
        HttpEntity<SaveMonitoredEndpointReqTo> request;
        ResponseEntity<?> resp;

        // Setup
        String url = "/api/monitored-endpoints";
        String correctToken = jwtTokenTest.getCorrectToken("Tyrion Lannister");
        reqTo = saveMonitoredEndpointReqTo_incorrectUrl();
        request = new HttpEntity<>(reqTo, httpHeaders(correctToken));

        // When
        resp = restTemplate.exchange(url, HttpMethod.POST, request, Void.class);

        // Then
        assertNotNull(resp);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testCreateMonitoredEndpoint_incorrectInterval(){
        SaveMonitoredEndpointReqTo reqTo;
        HttpEntity<SaveMonitoredEndpointReqTo> request;
        ResponseEntity<?> resp;

        // Setup
        String url = "/api/monitored-endpoints";
        String correctToken = jwtTokenTest.getCorrectToken("Tyrion Lannister");
        reqTo = saveMonitoredEndpointReqTo_incorrectInterval();
        request = new HttpEntity<>(reqTo, httpHeaders(correctToken));

        // When
        resp = restTemplate.exchange(url, HttpMethod.POST, request, Void.class);

        // Then
        assertNotNull(resp);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testCreateMonitoredEndpoint_badToken(){
        SaveMonitoredEndpointReqTo reqTo;
        HttpEntity<SaveMonitoredEndpointReqTo> request;
        ResponseEntity<?> resp;

        // Setup
        String url = "/api/monitored-endpoints";
        String badToken = jwtTokenTest.getBadToken();
        reqTo = saveMonitoredEndpointReqTo();
        request = new HttpEntity<>(reqTo, httpHeaders(badToken));

        // When
        resp = restTemplate.exchange(url, HttpMethod.POST, request, Void.class);

        // Then
        assertNotNull(resp);
        assertEquals(HttpStatus.FORBIDDEN, resp.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testCreateMonitoredEndpoint_withoutToken(){
        SaveMonitoredEndpointReqTo reqTo;
        HttpEntity<SaveMonitoredEndpointReqTo> request;
        ResponseEntity<?> resp;

        // Setup
        String url = "/api/monitored-endpoints";
        reqTo = saveMonitoredEndpointReqTo();
        request = new HttpEntity<>(reqTo, httpHeaders());

        // When
        resp = restTemplate.exchange(url, HttpMethod.GET, request, Void.class);

        // Then
        assertNotNull(resp);
        assertEquals(HttpStatus.FORBIDDEN, resp.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testUpdateMonitoredEndpoint_correct(){
        SaveMonitoredEndpointReqTo reqTo;
        HttpEntity<SaveMonitoredEndpointReqTo> request;
        ResponseEntity<?> resp;

        // Setup
        String url = "/api/monitored-endpoints/1000";
        String correctToken = jwtTokenTest.getCorrectToken("Tyrion Lannister");
        reqTo = saveMonitoredEndpointReqTo();
        request = new HttpEntity<>(reqTo, httpHeaders(correctToken));

        // Before
        MonitoredEndpoint updateEndpointBefore = RepoUtils.mustFindOneById(1000L, repo);
        assertNotNull(updateEndpointBefore);
        assertNotEquals("ČSFD", updateEndpointBefore.getName());
        assertNotEquals("https://www.csfd.cz/", updateEndpointBefore.getUrl());
        assertNotEquals(12, updateEndpointBefore.getMonitoredInterval());

        // When
        resp = restTemplate.exchange(url, HttpMethod.PUT, request, Void.class);

        // Then
        assertNotNull(resp);
        assertEquals(HttpStatus.OK, resp.getStatusCode());

        MonitoredEndpoint updateEndpointAfter = RepoUtils.mustFindOneById(1000L, repo);
        assertNotNull(updateEndpointAfter);
        assertEquals("ČSFD", updateEndpointAfter.getName());
        assertEquals("https://www.csfd.cz/", updateEndpointAfter.getUrl());
        assertEquals(12, updateEndpointAfter.getMonitoredInterval());
    }


    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testUpdateMonitoredEndpoint_incorrectUrl(){
        SaveMonitoredEndpointReqTo reqTo;
        HttpEntity<SaveMonitoredEndpointReqTo> request;
        ResponseEntity<?> resp;

        // Setup
        String url = "/api/monitored-endpoints/1000";
        String correctToken = jwtTokenTest.getCorrectToken("Tyrion Lannister");
        reqTo = saveMonitoredEndpointReqTo_incorrectUrl();
        request = new HttpEntity<>(reqTo, httpHeaders(correctToken));

        // When
        resp = restTemplate.exchange(url, HttpMethod.PUT, request, Void.class);

        // Then
        assertNotNull(resp);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testUpdateMonitoredEndpoint_incorrectInterval(){
        SaveMonitoredEndpointReqTo reqTo;
        HttpEntity<SaveMonitoredEndpointReqTo> request;
        ResponseEntity<?> resp;

        // Setup
        String url = "/api/monitored-endpoints/1000";
        String correctToken = jwtTokenTest.getCorrectToken("Tyrion Lannister");
        reqTo = saveMonitoredEndpointReqTo_incorrectInterval();
        request = new HttpEntity<>(reqTo, httpHeaders(correctToken));

        // When
        resp = restTemplate.exchange(url, HttpMethod.PUT, request, Void.class);

        // Then
        assertNotNull(resp);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testUpdateMonitoredEndpoint_notAuthorized(){
        SaveMonitoredEndpointReqTo reqTo;
        HttpEntity<SaveMonitoredEndpointReqTo> request;
        ResponseEntity<?> resp;

        // Setup
        String url = "/api/monitored-endpoints/1000";
        String correctTokenForWrongUser = jwtTokenTest.getCorrectToken("Aria Stark");
        reqTo = saveMonitoredEndpointReqTo();
        request = new HttpEntity<>(reqTo, httpHeaders(correctTokenForWrongUser));

        // When
        resp = restTemplate.exchange(url, HttpMethod.PUT, request, Void.class);

        // Then
        assertNotNull(resp);
        assertEquals(HttpStatus.FORBIDDEN, resp.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testUpdateMonitoredEndpoint_withoutToken(){
        SaveMonitoredEndpointReqTo reqTo;
        HttpEntity<SaveMonitoredEndpointReqTo> request;
        ResponseEntity<?> resp;

        // Setup
        String url = "/api/monitored-endpoints/1000";
        reqTo = saveMonitoredEndpointReqTo();
        request = new HttpEntity<>(reqTo, httpHeaders());

        // When
        resp = restTemplate.exchange(url, HttpMethod.PUT, request, Void.class);

        // Then
        assertNotNull(resp);
        assertEquals(HttpStatus.FORBIDDEN, resp.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testDeleteMonitoredEndpoint_correct(){
        HttpEntity<?> request;
        ResponseEntity<?> resp;

        // Setup
        String url = "/api/monitored-endpoints/1000";
        String correctToken = jwtTokenTest.getCorrectToken("Tyrion Lannister");
        request = new HttpEntity<>(httpHeaders(correctToken));

        // Before
        MonitoredEndpoint deleteEndpointBefore = RepoUtils.mustFindOneById(1000L, repo);
        assertNotNull(deleteEndpointBefore);

        // When
        resp = restTemplate.exchange(url, HttpMethod.DELETE, request, Void.class);

        // Then
        assertNotNull(resp);
        assertEquals(HttpStatus.OK, resp.getStatusCode());

        Optional<MonitoredEndpoint> deleteEndpointAfter = repo.findById(1000L);
        assertTrue(deleteEndpointAfter.isEmpty());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testDeleteMonitoredEndpoint_notAuthorized(){
        HttpEntity<?> request;
        ResponseEntity<?> resp;

        // Setup
        String url = "/api/monitored-endpoints/1000";
        String correctTokenForWrongUser = jwtTokenTest.getCorrectToken("Aria Stark");
        request = new HttpEntity<>(httpHeaders(correctTokenForWrongUser));

        // When
        resp = restTemplate.exchange(url, HttpMethod.DELETE, request, Void.class);

        // Then
        assertNotNull(resp);
        assertEquals(HttpStatus.FORBIDDEN, resp.getStatusCode());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testDeleteMonitoredEndpoint_withoutToken(){
        HttpEntity<?> request;
        ResponseEntity<?> resp;

        // Setup
        String url = "/api/monitored-endpoints/1000";
        request = new HttpEntity<>(httpHeaders());

        // When
        resp = restTemplate.exchange(url, HttpMethod.DELETE, request, Void.class);

        // Then
        assertNotNull(resp);
        assertEquals(HttpStatus.FORBIDDEN, resp.getStatusCode());
    }

    @Test
    public void testGetMonitoredEndpoints_correct() {
        HttpEntity<?> request;
        ResponseEntity<?> resp;
        List<GetMonitoredEndpointResTo> resTo;

        // Setup
        String url = "/api/monitored-endpoints";
        String correctToken = jwtTokenTest.getCorrectToken("Tyrion Lannister");
        request = new HttpEntity<>(httpHeaders(correctToken));

        // When
        resp = restTemplate.exchange(url, HttpMethod.GET, request, Object.class);

        // Then
        assertNotNull(resp);
        assertEquals(HttpStatus.OK, resp.getStatusCode());

        assertNotNull(resp.getBody());
        resTo = (List<GetMonitoredEndpointResTo>) resp.getBody();
        assertEquals(3, resTo.size());

        // TODO: check if monitored endpoints in the list contain proper data
    }

    @Test
    public void testGetMonitoredEndpoints_badToken(){
        HttpEntity<?> request;
        ResponseEntity<?> resp;

        // Setup
        String url = "/api/monitored-endpoints";
        String badToken = jwtTokenTest.getBadToken();
        request = new HttpEntity<>(httpHeaders(badToken));

        // When
        resp = restTemplate.exchange(url, HttpMethod.GET, request, Object.class);

        // Then
        assertNotNull(resp);
        assertEquals(HttpStatus.FORBIDDEN, resp.getStatusCode());
    }

    @Test
    public void testGetMonitoredEndpoints_withoutToken(){
        HttpEntity<?> request;
        ResponseEntity<?> resp;

        // Setup
        String url = "/api/monitored-endpoints";
        request = new HttpEntity<>(httpHeaders());

        // When
        resp = restTemplate.exchange(url, HttpMethod.GET, request, Object.class);

        // Then
        assertNotNull(resp);
        assertEquals(HttpStatus.FORBIDDEN, resp.getStatusCode());
    }

    @Test
    public void testGetMonitoringResults_correct() {
        HttpEntity<?> request;
        ResponseEntity<?> resp;

        // Setup
        String url = "/api/monitored-endpoints";
        String correctToken = jwtTokenTest.getCorrectToken("Tyrion Lannister");
        request = new HttpEntity<>(httpHeaders(correctToken));

        // When
        resp = restTemplate.exchange(url, HttpMethod.GET, request, Object.class);

        // Then
        assertNotNull(resp);
        assertEquals(HttpStatus.OK, resp.getStatusCode());

        // TODO: check if all monitoring results contain proper data
    }

    @Test
    public void testGetMonitoringResults_notAuthorized() {
        HttpEntity<?> request;
        ResponseEntity<?> resp;

        // Setup
        String url = "/api/monitored-endpoints/1000/monitoring-results";
        String correctTokenForWrongUser = jwtTokenTest.getCorrectToken("Aria Stark");
        request = new HttpEntity<>(httpHeaders(correctTokenForWrongUser));

        // When
        resp = restTemplate.exchange(url, HttpMethod.GET, request, Object.class);

        // Then
        assertNotNull(resp);
        assertEquals(HttpStatus.FORBIDDEN, resp.getStatusCode());
    }

    @Test
    public void testGetMonitoringResults_withoutToken(){
        HttpEntity<?> request;
        ResponseEntity<?> resp;

        // Setup
        String url = "/api/monitored-endpoints/1000/monitoring-results";
        request = new HttpEntity<>(httpHeaders());

        // When
        resp = restTemplate.exchange(url, HttpMethod.GET, request, Object.class);

        // Then
        assertNotNull(resp);
        assertEquals(HttpStatus.FORBIDDEN, resp.getStatusCode());
    }

    //~ Private methods

    private SaveMonitoredEndpointReqTo saveMonitoredEndpointReqTo() {
        SaveMonitoredEndpointReqTo monitoredEndpointReqTo = new SaveMonitoredEndpointReqTo();
        monitoredEndpointReqTo.setName("ČSFD");
        monitoredEndpointReqTo.setUrl("https://www.csfd.cz/");
        monitoredEndpointReqTo.setMonitoredInterval(12);

        return monitoredEndpointReqTo;
    }

    private SaveMonitoredEndpointReqTo saveMonitoredEndpointReqTo_incorrectUrl() {
        SaveMonitoredEndpointReqTo monitoredEndpointReqTo = new SaveMonitoredEndpointReqTo();
        monitoredEndpointReqTo.setName("ČSFD");
        monitoredEndpointReqTo.setUrl("www.csfd.cz/");
        monitoredEndpointReqTo.setMonitoredInterval(12);

        return monitoredEndpointReqTo;
    }

    private SaveMonitoredEndpointReqTo saveMonitoredEndpointReqTo_incorrectInterval() {
        SaveMonitoredEndpointReqTo monitoredEndpointReqTo = new SaveMonitoredEndpointReqTo();
        monitoredEndpointReqTo.setName("ČSFD");
        monitoredEndpointReqTo.setUrl("https://www.csfd.cz/");
        monitoredEndpointReqTo.setMonitoredInterval(0);

        return monitoredEndpointReqTo;
    }

    private Long getIdFromUri(String location) {
        // Position of the last '/' in String
        int lastSlashIndex = location.lastIndexOf('/');

        // Number behind the last '/' if it exists
        if (lastSlashIndex != -1) {
            String createdId = location.substring(lastSlashIndex + 1);
            return Long.parseLong(createdId);
        } else {
            return null;
        }
    }
}
