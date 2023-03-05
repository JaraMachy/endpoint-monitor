package cz.machovec.endpointmonitor.monitoring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import cz.machovec.endpointmonitor.monitoring.MonitoringResultService.SaveMonitoringResultIn;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class MonitoringRequestSender {

    private final @NonNull RestTemplate restTemplate;

    public SaveMonitoringResultIn sendRequest(String url, LocalDateTime scheduledTime) {
        SaveMonitoringResultIn saveMonitoringResultIn = new SaveMonitoringResultIn();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String responseBody = response.getBody();

        saveMonitoringResultIn.setReturnedHttpStatusCode(response.getStatusCodeValue());
        saveMonitoringResultIn.setReturnedPayload(responseBody);
        saveMonitoringResultIn.setDateOfCheck(scheduledTime);

        return saveMonitoringResultIn;
    }

}
