package cz.machovec.endpointmonitor.monitoring;

import cz.machovec.endpointmonitor.commons.repo.RepoUtils;
import cz.machovec.endpointmonitor.monitoring.MonitoredEndpointService.SaveMonitoredEndpointIn;
import cz.machovec.endpointmonitor.security.SecUser;
import cz.machovec.endpointmonitor.security.SecUserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class MonitoredEndpointFactory {
    private final @NonNull SecUserRepository secUserRepo;

    public MonitoredEndpoint createFrom(SaveMonitoredEndpointIn in) {
        MonitoredEndpoint monitoredEndpoint = new MonitoredEndpoint();

        SecUser secUser = RepoUtils.mustFindOneById(1L, secUserRepo);
        monitoredEndpoint.setOwner(secUser);

        return mapMonitoredEndpoint(monitoredEndpoint, in);
    }

    public MonitoredEndpoint updateFrom(SaveMonitoredEndpointIn in, MonitoredEndpoint monitoredEndpoint) {
        return mapMonitoredEndpoint(monitoredEndpoint, in);
    }

    //~ Private methods

    private static MonitoredEndpoint mapMonitoredEndpoint(MonitoredEndpoint monitoredEndpoint, SaveMonitoredEndpointIn in) {
        monitoredEndpoint.setName(in.getName());
        monitoredEndpoint.setUrl(in.getUrl());
        monitoredEndpoint.setMonitoredInterval(in.getMonitoredInterval());

        return monitoredEndpoint;
    }
}
