package cz.machovec.endpointmonitor.monitoring;

import cz.machovec.endpointmonitor.commons.repo.ByIdFinder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonitoringResultRepository extends JpaRepository<MonitoringResult, Long>, ByIdFinder<Long, MonitoringResult> {

    List<MonitoringResult> findTop10ByMonitoredEndpoint_IdOrderByDateOfCheckDesc(Long monitoredEndpointId);

}
