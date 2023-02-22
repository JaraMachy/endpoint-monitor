package cz.machovec.endpointmonitor.monitoring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

        ResponseEntity<?> response = this.restTemplate.getForEntity(url, String.class);

        saveMonitoringResultIn.setReturnedHttpStatusCode(response.getStatusCodeValue());
        saveMonitoringResultIn.setReturnedPayload(response.getBody().toString());

        saveMonitoringResultIn.setDateOfCheck(scheduledTime);

        return saveMonitoringResultIn;
    }

}
