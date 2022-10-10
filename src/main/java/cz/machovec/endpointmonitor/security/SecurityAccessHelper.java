package cz.machovec.endpointmonitor.security;

import cz.machovec.endpointmonitor.monitoring.MonitoredEndpointRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityAccessHelper {

    @NonNull private final MonitoredEndpointRepository monitoredEndpointRepo;
    @NonNull private final SecUserRepository secUserRepo;

    public boolean checkMonitoredEndpointOwnership(Long monitoredEndpointId, Authentication authentication) {
        SecUser secUser = monitoredEndpointRepo.findOwnerById(monitoredEndpointId);

        if (secUser != null && secUser.getUsername().equals(authentication.getName())) {
            return true;
        } else {
            return false;
        }
    }

    public SecUser getLoggedUser() {
        String loggedUsername = getLoggedUserUsername();

        return secUserRepo.findByUsername(loggedUsername);
    }

    public Long getLoggedUserId() {
        String loggedUsername = getLoggedUserUsername();

        return secUserRepo.findSecUserIdByUsername(loggedUsername);
    }

    private String getLoggedUserUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loggedUserUsername;

        if (principal instanceof UserDetails) {
            loggedUserUsername = ((UserDetails)principal).getUsername();
        } else {
            loggedUserUsername = null;
        }

        return loggedUserUsername;
    }

}
