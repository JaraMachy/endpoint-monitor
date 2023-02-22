package cz.machovec.endpointmonitor.monitoring;

import lombok.*;
import cz.machovec.endpointmonitor.monitoring.MonitoringResultService.SaveMonitoringResultIn;

import java.time.LocalDateTime;

@Getter @Setter
public class CallMonitoredEndpointCmd implements Runnable {

    private Long endpointId;
    private String url;
    private LocalDateTime scheduledTime;
    private final @NonNull MonitoringResultService monitoringResultService;
    private final @NonNull MonitoringRequestSender monitoringRequestSender;
    private final @NonNull MonitoringThreadPoolManager monitoringThreadPoolManager;

    public CallMonitoredEndpointCmd(Long endpointId, String url, LocalDateTime scheduledTime,
                                    @NonNull MonitoringResultService monitoringResultService,
                                    @NonNull MonitoringRequestSender monitoringRequestSender,
                                    @NonNull MonitoringThreadPoolManager monitoringThreadPoolManager) {
        this.endpointId = endpointId;
        this.url = url;
        this.scheduledTime = scheduledTime;
        this.monitoringResultService = monitoringResultService;
        this.monitoringRequestSender = monitoringRequestSender;
        this.monitoringThreadPoolManager = monitoringThreadPoolManager;
    }

    @Override
    public void run() {
        // send request
        SaveMonitoringResultIn in = monitoringRequestSender.sendRequest(url, scheduledTime);
        // save result
        monitoringResultService.createMonitoringResult(in, endpointId);

        // put task to thread pool again
        monitoringThreadPoolManager.reloadTask(endpointId);
    }

}
