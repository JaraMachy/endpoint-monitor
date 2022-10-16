package cz.machovec.endpointmonitor.monitoring;

import cz.machovec.endpointmonitor.monitoring.MonitoringResultService.SaveMonitoringResultIn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class MonitoringResultFactory {

    public MonitoringResult createFrom(SaveMonitoringResultIn in, MonitoredEndpoint monitoredEndpoint) {
        MonitoringResult monitoringResult = new MonitoringResult();

        monitoringResult.setReturnedPayload(in.getReturnedPayload());
        monitoringResult.setReturnedHttpStatusCode(in.getReturnedHttpStatusCode());
        monitoringResult.setDateOfCheck(in.getDateOfCheck());
        monitoredEndpoint.setDateOfLastCheck(in.getDateOfCheck());
        monitoringResult.setMonitoredEndpoint(monitoredEndpoint);

        return monitoringResult;
    }
}
