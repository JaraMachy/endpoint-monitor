package cz.machovec.endpointmonitor.monitoring;

import cz.machovec.endpointmonitor.monitoring.MonitoredEndpointService.GetMonitoredEndpointOut;
import cz.machovec.endpointmonitor.monitoring.MonitoredEndpointService.SaveMonitoredEndpointIn;
import cz.machovec.endpointmonitor.monitoring.MonitoredEndpointResource.SaveMonitoredEndpointReqTo;
import cz.machovec.endpointmonitor.monitoring.MonitoredEndpointResource.GetMonitoredEndpointResTo;
import cz.machovec.endpointmonitor.monitoring.MonitoredEndpointResource.GetMonitoredEndpointResTo.UserResTo;

import java.util.ArrayList;
import java.util.List;

public class MonitoredEndpointMappers {

    public static List<GetMonitoredEndpointOut> fromMonitoredEndpoints(List<MonitoredEndpoint> monitoredEndpoints) {
        List<GetMonitoredEndpointOut> listOut = new ArrayList<>();

        for (MonitoredEndpoint item : monitoredEndpoints) {
            listOut.add(fromMonitoredEndpoint(item));
        }

        return listOut;
    }

    public static GetMonitoredEndpointOut fromMonitoredEndpoint(MonitoredEndpoint monitoredEndpoint) {
        GetMonitoredEndpointOut out = new GetMonitoredEndpointOut();

        out.setId(monitoredEndpoint.getId());
        out.setDateOfCreation(monitoredEndpoint.getDateOfCreation());
        out.setDateOfLastCheck(monitoredEndpoint.getDateOfLastCheck());
        out.setMonitoredInterval(monitoredEndpoint.getMonitoredInterval());
        out.setName(monitoredEndpoint.getName());
        out.setUrl(monitoredEndpoint.getUrl());

        if (monitoredEndpoint.getOwner() != null) {
            GetMonitoredEndpointOut.UserOut userOut = new GetMonitoredEndpointOut.UserOut();
            userOut.setId(monitoredEndpoint.getOwner().getId());
            userOut.setUsername(monitoredEndpoint.getOwner().getUsername());
            out.setOwner(userOut);
        }

        return out;
    }

    public static SaveMonitoredEndpointIn fromSaveMonitoredEndpointReqTo(SaveMonitoredEndpointReqTo reqTo) {
        SaveMonitoredEndpointIn projectIn = new SaveMonitoredEndpointIn();

        projectIn.setUrl(reqTo.getUrl());
        projectIn.setName(reqTo.getName());
        projectIn.setMonitoredInterval(reqTo.getMonitoredInterval());

        return projectIn;
    }

    public static List<GetMonitoredEndpointResTo> fromMonitoredEndpointsOut(List<GetMonitoredEndpointOut> listOut) {
        List<GetMonitoredEndpointResTo> listResTo = new ArrayList<>();

        for (GetMonitoredEndpointOut itemOut : listOut) {
            listResTo.add(fromMonitoredEndpointOut(itemOut));
        }

        return listResTo;
    }

    public static GetMonitoredEndpointResTo fromMonitoredEndpointOut(GetMonitoredEndpointOut out) {
        GetMonitoredEndpointResTo resTo = new GetMonitoredEndpointResTo();

        resTo.setId(out.getId());
        resTo.setDateOfCreation(out.getDateOfCreation());
        resTo.setDateOfLastCheck(out.getDateOfLastCheck());
        resTo.setMonitoredInterval(out.getMonitoredInterval());
        resTo.setName(out.getName());
        resTo.setUrl(out.getUrl());

        if (out.getOwner() != null) {
            UserResTo userResTo = new UserResTo();
            userResTo.setId(out.getOwner().getId());
            userResTo.setUsername(out.getOwner().getUsername());
            resTo.setOwner(userResTo);
        }

        return resTo;
    }
}
