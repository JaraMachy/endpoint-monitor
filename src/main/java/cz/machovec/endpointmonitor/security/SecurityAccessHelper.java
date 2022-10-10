package cz.machovec.endpointmonitor.security;

import cz.machovec.endpointmonitor.monitoring.MonitoredEndpointRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityAccessHelper {

    @NonNull private final MonitoredEndpointRepository monitoredEndpointRepo;

    public boolean checkMonitoredEndpointOwnership(Long monitoredEndpointId, Authentication authentication) {
        SecUser secUser = monitoredEndpointRepo.findOwnerById(monitoredEndpointId);

        if (secUser != null && secUser.getUsername().equals(authentication.getName())) {
            return true;
        } else {
            return false;
        }
    }

}
