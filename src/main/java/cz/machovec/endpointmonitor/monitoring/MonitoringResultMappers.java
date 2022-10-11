package cz.machovec.endpointmonitor.monitoring;

import cz.machovec.endpointmonitor.monitoring.MonitoringResultService.GetMonitoringResultOut;
import cz.machovec.endpointmonitor.monitoring.MonitoringResultService.GetMonitoringResultOut.MonitoredEndpointOut;
import cz.machovec.endpointmonitor.monitoring.MonitoredEndpointResource.GetMonitoringResultResTo;
import cz.machovec.endpointmonitor.monitoring.MonitoredEndpointResource.GetMonitoringResultResTo.MonitoredEndpointResTo;
import java.util.ArrayList;
import java.util.List;

public class MonitoringResultMappers {

    public static List<GetMonitoringResultOut> fromMonitoringResults(List<MonitoringResult> monitoringResults) {
        List<GetMonitoringResultOut> listOut = new ArrayList<>();

        for (MonitoringResult item : monitoringResults) {
            listOut.add(fromMonitoringResult(item));
        }

        return listOut;
    }

    public static GetMonitoringResultOut fromMonitoringResult(MonitoringResult monitoringResult) {
        GetMonitoringResultOut out = new GetMonitoringResultOut();

        out.setId(monitoringResult.getId());
        out.setDateOfCheck(monitoringResult.getDateOfCheck());
        out.setReturnedHttpStatusCode(monitoringResult.getReturnedHttpStatusCode());
        out.setReturnedPayload(monitoringResult.getReturnedPayload());

        if (monitoringResult.getMonitoredEndpoint() != null) {
            MonitoredEndpointOut monitoredEndpointOut = new MonitoredEndpointOut();
            monitoredEndpointOut.setId(monitoringResult.getMonitoredEndpoint().getId());
            monitoredEndpointOut.setUrl(monitoringResult.getMonitoredEndpoint().getUrl());
            out.setMonitoredEndpointOut(monitoredEndpointOut);
        }

        return out;
    }

    public static List<GetMonitoringResultResTo> fromMonitoringResultsOut(List<GetMonitoringResultOut> listOut) {
        List<GetMonitoringResultResTo> listResTo = new ArrayList<>();

        for (GetMonitoringResultOut itemOut : listOut) {
            listResTo.add(fromMonitoringResultOut(itemOut));
        }

        return listResTo;
    }

    public static GetMonitoringResultResTo fromMonitoringResultOut(GetMonitoringResultOut out) {
        GetMonitoringResultResTo resTo = new GetMonitoringResultResTo();

        resTo.setId(out.getId());
        resTo.setDateOfCheck(out.getDateOfCheck());
        resTo.setReturnedPayload(out.getReturnedPayload());
        resTo.setReturnedHttpStatusCode(out.getReturnedHttpStatusCode());

        if (out.getMonitoredEndpointOut() != null) {
            MonitoredEndpointResTo monitoredEndpointResTo = new MonitoredEndpointResTo();
            monitoredEndpointResTo.setId(out.getMonitoredEndpointOut().getId());
            monitoredEndpointResTo.setUrl(out.getMonitoredEndpointOut().getUrl());
            resTo.setMonitoredEndpointResTo(monitoredEndpointResTo);
        }

        return resTo;
    }
}
