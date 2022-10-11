package cz.machovec.endpointmonitor.monitoring;

import cz.machovec.endpointmonitor.commons.repo.ByIdFinder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonitoringResultRepository extends JpaRepository<MonitoringResult, Long>, ByIdFinder<Long, MonitoringResult> {

    // TODO: check if first 10 is selected correctly
    List<MonitoringResult> findFirst10ByMonitoredEndpoint_Id(Long monitoredEndpointId);

}
