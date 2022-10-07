package cz.machovec.endpointmonitor.monitoring;

import cz.machovec.endpointmonitor.monitoring.MonitoredEndpointService.SaveMonitoredEndpointIn;
import cz.machovec.endpointmonitor.monitoring.MonitoredEndpointService.UpdateMonitoredEndpointOut;
import cz.machovec.endpointmonitor.monitoring.MonitoredEndpointService.DeleteMonitoredEndpointOut;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static cz.machovec.endpointmonitor.commons.api.HttpResponses.*;

@RestController
@RequestMapping("/api/monitored-endpoints")
@RequiredArgsConstructor
public class MonitoredEndpointResource {

    private final @NonNull MonitoredEndpointService monitoredEndpointService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<?> createMonitoredEndpoint(@RequestBody @Valid SaveMonitoredEndpointReqTo reqTo) {

        // Prepare object for service layer
        SaveMonitoredEndpointIn in = MonitoredEndpointMappers.fromSaveMonitoredEndpointReqTo(reqTo);
        // Call service layer
        monitoredEndpointService.createMonitoredEndpoint(in);

        return created();
    }

    @PutMapping("/{monitoredEndpointId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<?> updateMonitoredEndpoint(@RequestBody @Valid SaveMonitoredEndpointReqTo reqTo, @PathVariable Long monitoredEndpointId) {

        // Prepare object for service layer
        SaveMonitoredEndpointIn in = MonitoredEndpointMappers.fromSaveMonitoredEndpointReqTo(reqTo);
        // Call service layer
        UpdateMonitoredEndpointOut updateOut = monitoredEndpointService.updateMonitoredEndpoint(in, monitoredEndpointId);
        if (!updateOut.isUpdated()) {
            return badRequest();
        }

        return ok();
    }

    @DeleteMapping("/{monitoredEndpointId}/delete")
    public ResponseEntity<?> deleteMonitoredEndpoint(@PathVariable Long monitoredEndpointId) {

        // Call service layer
        DeleteMonitoredEndpointOut deleteOut = monitoredEndpointService.deleteMonitoredEndpoint(monitoredEndpointId);
        if (!deleteOut.isDeleted()) {
            return badRequest();
        }

        return ok();
    }

    // TODO: list user's endpoints

    @Getter @Setter
    static class SaveMonitoredEndpointReqTo {
        @NotBlank
        private String name;
        @NotBlank
        private String url;
        @NotNull
        private Integer monitoredInterval;

    }
}
