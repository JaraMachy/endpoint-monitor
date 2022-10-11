package cz.machovec.endpointmonitor.monitoring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MonitoringResultServiceImpl implements MonitoringResultService {

    private final @NonNull MonitoringResultRepository monitoringResultRepo;

    @Override
    @Transactional(readOnly = true)
    public List<GetMonitoringResultOut> getLatest10MonitoringResults(Long monitoredEndpointId) {
        Assert.notNull(monitoredEndpointId, "monitoredEndpointId must not be null!");

        List<MonitoringResult> monitoringResults = monitoringResultRepo.findFirst10ByMonitoredEndpoint_Id(monitoredEndpointId);

        return MonitoringResultMappers.fromMonitoringResults(monitoringResults);
    }
}
