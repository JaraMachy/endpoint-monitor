package cz.machovec.endpointmonitor.monitoring;

import cz.machovec.endpointmonitor.commons.repo.RepoUtils;
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
    private final @NonNull MonitoredEndpointRepository monitoredEndpointRepo;
    private final @NonNull MonitoringResultFactory monitoringResultFactory;

    @Override
    @Transactional(readOnly = true)
    public List<GetMonitoringResultOut> getLatest10MonitoringResults(Long monitoredEndpointId) {
        Assert.notNull(monitoredEndpointId, "monitoredEndpointId must not be null!");

        List<MonitoringResult> monitoringResults = monitoringResultRepo.findTop10ByMonitoredEndpoint_IdOrderByDateOfCheckDesc(monitoredEndpointId);

        return MonitoringResultMappers.fromMonitoringResults(monitoringResults);
    }

    @Override
    public Long createMonitoringResult(SaveMonitoringResultIn saveMonitoringResultIn, Long monitoredEndpointId) {
        Assert.notNull(saveMonitoringResultIn, "saveMonitoringResultIn must not be null!");
        Assert.notNull(monitoredEndpointId, "monitoredEndpointId must not be null!");

        MonitoredEndpoint monitoredEndpoint = RepoUtils.mustFindOneById(monitoredEndpointId, monitoredEndpointRepo);

        MonitoringResult monitoringResult = monitoringResultFactory.createFrom(saveMonitoringResultIn, monitoredEndpoint);

        monitoringResultRepo.save(monitoringResult);

        return monitoringResult.getId();
    }
}
