package cz.machovec.endpointmonitor.monitoring;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public interface MonitoredEndpointService {

    Long createMonitoredEndpoint(SaveMonitoredEndpointIn saveMonitoredEndpointIn);

    UpdateMonitoredEndpointOut updateMonitoredEndpoint(SaveMonitoredEndpointIn saveMonitoredEndpointIn, Long monitoredEnpointId);

    DeleteMonitoredEndpointOut deleteMonitoredEndpoint(Long monitoredEndpointId);

    List<GetMonitoredEndpointOut> getMonitoredEndpoints(Long secUserId);

    //////////////////////////////////////////////////////////
    //                                                      //
    //                          GET                         //
    //                                                      //
    //////////////////////////////////////////////////////////

    @Getter @Setter
    class GetMonitoredEndpointOut {
        private Long id;
        private String name;
        private String url;
        private LocalDateTime dateOfCreation;
        private LocalDateTime dateOfLastCheck;
        private Integer monitoredInterval;
        private UserOut owner;

        @Getter @Setter
        static class UserOut {
            private Long id;
            private String username;
        }
    }

    //////////////////////////////////////////////////////////
    //                                                      //
    //                          SAVE                        //
    //                                                      //
    //////////////////////////////////////////////////////////

    @Getter @Setter
    class SaveMonitoredEndpointIn {
        private String name;
        private String url;
        private Integer monitoredInterval;
    }

    @Getter @Setter
    class UpdateMonitoredEndpointOut {
        private Long monitoredEndpointId;
        private boolean updated = false;
    }

    //////////////////////////////////////////////////////////
    //                                                      //
    //                        DELETE                        //
    //                                                      //
    //////////////////////////////////////////////////////////

    @Getter @Setter
    class DeleteMonitoredEndpointOut {
        private Long monitoredEndpointId;
        private boolean deleted = false;
    }

}