package cz.machovec.endpointmonitor.monitoring;

import cz.machovec.endpointmonitor.monitoring.MonitoredEndpointService.SaveMonitoredEndpointIn;
import cz.machovec.endpointmonitor.monitoring.MonitoredEndpointService.UpdateMonitoredEndpointOut;
import cz.machovec.endpointmonitor.monitoring.MonitoredEndpointService.DeleteMonitoredEndpointOut;
import cz.machovec.endpointmonitor.monitoring.MonitoredEndpointService.GetMonitoredEndpointOut;
import cz.machovec.endpointmonitor.monitoring.MonitoringResultService.GetMonitoringResultOut;

import cz.machovec.endpointmonitor.security.SecurityAccessHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
import javax.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

import static cz.machovec.endpointmonitor.commons.api.HttpResponses.*;

@RestController
@SecurityRequirement(name = "JWT")
@RequestMapping("/api/monitored-endpoints")
@RequiredArgsConstructor
public class MonitoredEndpointResource {

    private final @NonNull MonitoredEndpointService monitoredEndpointService;
    private final @NonNull MonitoringResultService monitoringResultService;
    private final @NonNull SecurityAccessHelper securityAccessHelper;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(description = "Creates new monitored endpoint.")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "201", description = "created"),
                    @ApiResponse(responseCode = "400", description = "bad request"),
                    @ApiResponse(responseCode = "403", description = "forbidden")}
    )
    public ResponseEntity<?> createMonitoredEndpoint(@RequestBody @Valid SaveMonitoredEndpointReqTo reqTo) {

        // Prepare object for service layer
        SaveMonitoredEndpointIn in = MonitoredEndpointMappers.fromSaveMonitoredEndpointReqTo(reqTo);
        // Call service layer
        Long monitoredEndpointId = monitoredEndpointService.createMonitoredEndpoint(in);

        return created("/api/monitored-endpoints/" + monitoredEndpointId);
    }

    @GetMapping("/{monitoredEndpointId}")
    @PreAuthorize("@securityAccessHelper.checkMonitoredEndpointOwnership(#monitoredEndpointId, #authentication)")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Gets detail of monitored endpoint by id.")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "ok"),
                    @ApiResponse(responseCode = "403", description = "forbidden")}
    )
    public ResponseEntity<?> getMonitoredEndpoint(@PathVariable Long monitoredEndpointId, Authentication authentication) {

        // Call service layer
        GetMonitoredEndpointOut out = monitoredEndpointService.getMonitoredEndpoint(monitoredEndpointId);
        GetMonitoredEndpointResTo resTo = MonitoredEndpointMappers.fromMonitoredEndpointOut(out);

        return ok(resTo);

    }

    @PutMapping("/{monitoredEndpointId}")
    @PreAuthorize("@securityAccessHelper.checkMonitoredEndpointOwnership(#monitoredEndpointId, #authentication)")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Updates monitored endpoint with given id.")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "ok"),
                    @ApiResponse(responseCode = "400", description = "bad request"),
                    @ApiResponse(responseCode = "403", description = "forbidden")}
    )
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
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Deletes monitored endpoint by given id.")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "ok"),
                    @ApiResponse(responseCode = "400", description = "bad request"),
                    @ApiResponse(responseCode = "403", description = "forbidden")}
    )
    public ResponseEntity<?> deleteMonitoredEndpoint(@PathVariable Long monitoredEndpointId, Authentication authentication) {

        // Call service layer
        DeleteMonitoredEndpointOut deleteOut = monitoredEndpointService.deleteMonitoredEndpoint(monitoredEndpointId);
        if (!deleteOut.isDeleted()) {
            return badRequest();
        }

        return ok();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Gets monitored endpoint list of authenticated user.")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "ok"),
                    @ApiResponse(responseCode = "403", description = "forbidden")}
    )
    public ResponseEntity<?> getMonitoredEndpoints() {
        Long loggedUserId = securityAccessHelper.getLoggedUserId();

        // Call service layer
        List<GetMonitoredEndpointOut> out = monitoredEndpointService.getMonitoredEndpoints(loggedUserId);
        List<GetMonitoredEndpointResTo> resTo = MonitoredEndpointMappers.fromMonitoredEndpointsOut(out);

        return ok(resTo);

    }

    @GetMapping("/{monitoredEndpointId}/monitoring-results")
    @PreAuthorize("@securityAccessHelper.checkMonitoredEndpointOwnership(#monitoredEndpointId, #authentication)")
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Gets last 10 monitoring results of monitored endpoint by given id.")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "ok"),
                    @ApiResponse(responseCode = "403", description = "forbidden")}
    )
    public ResponseEntity<?> getMonitoringResults(@PathVariable Long monitoredEndpointId, Authentication authentication) {

        // Call service layer
        List<GetMonitoringResultOut> out = monitoringResultService.getLatest10MonitoringResults(monitoredEndpointId);
        List<GetMonitoringResultResTo> resTo = MonitoringResultMappers.fromMonitoringResultsOut(out);

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
        @NotNull
        @NotBlank
        private String name;
        @NotNull
        @NotBlank
        @Pattern(regexp = "^(https?://).*", message = "Url must start either with 'https://' or 'http://'")
        private String url;
        @NotNull
        @Min(value = 1, message = "Monitored interval must be at least 1 second")
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
        private UserResTo owner;

        @Getter @Setter
        static class UserResTo {
            private Long id;
            private String username;
        }
    }

    @Getter
    @Setter
    static class GetMonitoringResultResTo {
        private Long id;
        private LocalDateTime dateOfCheck;
        private Integer returnedHttpStatusCode;
        private String returnedPayload;
        private MonitoredEndpointResTo monitoredEndpointResTo;

        @Getter @Setter
        static class MonitoredEndpointResTo {
            private Long id;
            private String url;
        }
    }
}
