package cz.machovec.endpointmonitor.monitoring;

import cz.machovec.endpointmonitor.monitoring.MonitoredEndpointService.SaveMonitoredEndpointIn;
import cz.machovec.endpointmonitor.monitoring.MonitoredEndpointService.UpdateMonitoredEndpointOut;
import cz.machovec.endpointmonitor.monitoring.MonitoredEndpointService.DeleteMonitoredEndpointOut;
import cz.machovec.endpointmonitor.monitoring.MonitoredEndpointService.GetMonitoredEndpointOut;

import cz.machovec.endpointmonitor.security.SecurityAccessHelper;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

import static cz.machovec.endpointmonitor.commons.api.HttpResponses.*;

@RestController
@RequestMapping("/api/monitored-endpoints")
@RequiredArgsConstructor
public class MonitoredEndpointResource {

    private final @NonNull MonitoredEndpointService monitoredEndpointService;
    private final @NonNull SecurityAccessHelper securityAccessHelper;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> createMonitoredEndpoint(@RequestBody @Valid SaveMonitoredEndpointReqTo reqTo) {

        // Prepare object for service layer
        SaveMonitoredEndpointIn in = MonitoredEndpointMappers.fromSaveMonitoredEndpointReqTo(reqTo);
        // Call service layer
        monitoredEndpointService.createMonitoredEndpoint(in);

        return created();
    }

    @GetMapping("/{monitoredEndpointId}")
    @PreAuthorize("@securityAccessHelper.checkMonitoredEndpointOwnership(#monitoredEndpointId, #authentication)")
    public ResponseEntity<?> getMonitoredEndpoint(@PathVariable Long monitoredEndpointId, Authentication authentication) {

        // Call service layer
        GetMonitoredEndpointOut out = monitoredEndpointService.getMonitoredEndpoint(monitoredEndpointId);
        GetMonitoredEndpointResTo resTo = MonitoredEndpointMappers.fromMonitoredEndpointOut(out);

        return ok(resTo);

    }

    @PutMapping("/{monitoredEndpointId}")
    @PreAuthorize("@securityAccessHelper.checkMonitoredEndpointOwnership(#monitoredEndpointId, #authentication)")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> updateMonitoredEndpoint(@RequestBody @Valid SaveMonitoredEndpointReqTo reqTo, @PathVariable Long monitoredEndpointId, Authentication authentication) {

        // Prepare object for service layer
        SaveMonitoredEndpointIn in = MonitoredEndpointMappers.fromSaveMonitoredEndpointReqTo(reqTo);
        // Call service layer
        UpdateMonitoredEndpointOut updateOut = monitoredEndpointService.updateMonitoredEndpoint(in, monitoredEndpointId);
        if (!updateOut.isUpdated()) {
            return badRequest();
        }

        return ok();
    }

    @DeleteMapping("/{monitoredEndpointId}")
    @PreAuthorize("@securityAccessHelper.checkMonitoredEndpointOwnership(#monitoredEndpointId, #authentication)")
    public ResponseEntity<?> deleteMonitoredEndpoint(@PathVariable Long monitoredEndpointId, Authentication authentication) {

        // Call service layer
        DeleteMonitoredEndpointOut deleteOut = monitoredEndpointService.deleteMonitoredEndpoint(monitoredEndpointId);
        if (!deleteOut.isDeleted()) {
            return badRequest();
        }

        return ok();
    }

    @GetMapping("/list")
    public ResponseEntity<?> getMonitoredEndpoints() {
        Long loggedUserId = securityAccessHelper.getLoggedUserId();

        // Call service layer
        List<GetMonitoredEndpointOut> out = monitoredEndpointService.getMonitoredEndpoints(loggedUserId);
        List<GetMonitoredEndpointResTo> resTo = MonitoredEndpointMappers.fromMonitoredEndpointsOut(out);

        return ok(resTo);

    }

    //~ Transfer objects

    //=====================================//
    //                                     //
    //          Request Objects            //
    //                                     //
    //=====================================//

    @Getter @Setter
    static class SaveMonitoredEndpointReqTo {
        @NotBlank
        private String name;
        @NotBlank
        private String url;
        @NotNull
        private Integer monitoredInterval;

    }


    //=====================================//
    //                                     //
    //          Response Objects           //
    //                                     //
    //=====================================//

    @Getter @Setter
    static class GetMonitoredEndpointResTo {
        private Long id;
        private String name;
        private String url;
        private LocalDateTime dateOfCreation;
        private LocalDateTime dateOfLastCheck;
        private Integer monitoredInterval;
        private MonitoredEndpointService.GetMonitoredEndpointOut.UserOut owner;

        @Getter @Setter
        class UserOut {
            private Long id;
            private String username;
        }
    }
}
