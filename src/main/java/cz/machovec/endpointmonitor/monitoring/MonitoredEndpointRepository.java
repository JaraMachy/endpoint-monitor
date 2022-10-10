package cz.machovec.endpointmonitor.monitoring;

import cz.machovec.endpointmonitor.commons.repo.ByIdFinder;
import cz.machovec.endpointmonitor.security.SecUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MonitoredEndpointRepository extends JpaRepository<MonitoredEndpoint, Long>, ByIdFinder<Long, MonitoredEndpoint> {

    List<MonitoredEndpoint> findAllByOwner(SecUser owner);

    @Query("SELECT monitoredEndpoint.owner FROM MonitoredEndpoint monitoredEndpoint WHERE monitoredEndpoint.id = ?1")
    SecUser findOwnerById(Long id);

}
