package cz.machovec.endpointmonitor.monitoring;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public interface MonitoringResultService {

    List<GetMonitoringResultOut> getLatest10MonitoringResults(Long monitoredEndpointId);

    Long createMonitoringResult(SaveMonitoringResultIn saveMonitoringResultIn, Long monitoredEndpointId);

    //////////////////////////////////////////////////////////
    //                                                      //
    //                          GET                         //
    //                                                      //
    //////////////////////////////////////////////////////////

    @Getter @Setter
    class GetMonitoringResultOut {
        private Long id;
        private LocalDateTime dateOfCheck;
        private Integer returnedHttpStatusCode;
        private String returnedPayload;
        private MonitoredEndpointOut monitoredEndpointOut;

        @Getter @Setter
        static class MonitoredEndpointOut {
            private Long id;
            private String url;
        }
    }

    //////////////////////////////////////////////////////////
    //                                                      //
    //                          SAVE                        //
    //                                                      //
    //////////////////////////////////////////////////////////

    @Getter @Setter
    class SaveMonitoringResultIn {
        private LocalDateTime dateOfCheck;
        private Integer returnedHttpStatusCode;
        private String returnedPayload;
    }
}
