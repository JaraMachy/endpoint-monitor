package cz.machovec.endpointmonitor.monitoring;

import cz.machovec.endpointmonitor.monitoring.MonitoredEndpointService.GetMonitoredEndpointOut;
import cz.machovec.endpointmonitor.monitoring.MonitoredEndpointService.SaveMonitoredEndpointIn;
import cz.machovec.endpointmonitor.security.SecUser;
import cz.machovec.endpointmonitor.monitoring.MonitoredEndpointApiController.SaveMonitoredEndpointReqTo;

import java.util.ArrayList;
import java.util.List;

public class MonitoredEndpointMappers {

    public static List<GetMonitoredEndpointOut> fromMonitoredEndpoints(List<MonitoredEndpoint> monitoredEndpoints) {
        List<GetMonitoredEndpointOut> monitoredEndpointsOut = new ArrayList<>();

        for (MonitoredEndpoint item : monitoredEndpoints) {
            GetMonitoredEndpointOut itemOut = new GetMonitoredEndpointOut();
            itemOut.setId(item.getId());
            itemOut.setName(item.getName());
            itemOut.setUrl(item.getUrl());
            itemOut.setMonitoredInterval(item.getMonitoredInterval());
            itemOut.setDateOfCreation(item.getDateOfLastCheck());
            itemOut.setDateOfLastCheck(item.getDateOfCreation());

            GetMonitoredEndpointOut.UserOut userOut = new GetMonitoredEndpointOut.UserOut();
            SecUser owner = item.getOwner();
            userOut.setId(owner.getId());
            userOut.setUsername(owner.getUsername());

            itemOut.setOwner(userOut);

            monitoredEndpointsOut.add(itemOut);
        }

        return monitoredEndpointsOut;
    }

    public static SaveMonitoredEndpointIn fromSaveMonitoredEndpointReqTo(SaveMonitoredEndpointReqTo reqTo) {
        SaveMonitoredEndpointIn projectIn = new SaveMonitoredEndpointIn();
        projectIn.setUrl(reqTo.getUrl());
        projectIn.setName(reqTo.getName());
        projectIn.setMonitoredInterval(reqTo.getMonitoredInterval());

        return projectIn;
    }
}
