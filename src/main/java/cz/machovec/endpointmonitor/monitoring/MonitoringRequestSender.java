package cz.machovec.endpointmonitor.monitoring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import cz.machovec.endpointmonitor.monitoring.MonitoringResultService.SaveMonitoringResultIn;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@Component
@RequiredArgsConstructor
public class MonitoringRequestSender {

    private final @NonNull RestTemplate restTemplate;

    public SaveMonitoringResultIn sendRequest(String url) {
        SaveMonitoringResultIn saveMonitoringResultIn = new SaveMonitoringResultIn();

        ResponseEntity<?> response = this.restTemplate.getForEntity(url, String.class);

        saveMonitoringResultIn.setReturnedHttpStatusCode(response.getStatusCodeValue());
        saveMonitoringResultIn.setReturnedPayload(response.getBody().toString());

        long responseTimestamp = response.getHeaders().getDate();
        LocalDateTime responseDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(responseTimestamp), TimeZone.getDefault().toZoneId());
        saveMonitoringResultIn.setDateOfCheck(responseDateTime);

        return saveMonitoringResultIn;
    }

}
