package cz.machovec.endpointmonitor.monitoring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import cz.machovec.endpointmonitor.monitoring.MonitoredEndpointService.MonitoredEndpointReq;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class MonitoringThreadPoolManager {

    private static ScheduledThreadPoolExecutor threadPool = new ScheduledThreadPoolExecutor(10);
    private static Map<Long, Future> runningThreads = new HashMap();

    private final @NonNull MonitoredEndpointService monitoredEndpointService;
    private final @NonNull MonitoringResultService monitoringResultService;
    private final @NonNull MonitoringRequestSender monitoringRequestSender;

    // init add all monitored endpoints
    @EventListener(ApplicationReadyEvent.class)
    public void startMonitoring() {
        List<MonitoredEndpointReq> dbList = monitoredEndpointService.getAllMonitoredEndpointReqs();
        addToThreadPool(dbList);
    }

    // add called endpoint again
    public void reloadTask(Long endpointId) {
        MonitoredEndpointReq req = monitoredEndpointService.getMonitoredEndpointReq(endpointId);
        addToThreadPool(req);
    }

    // create monitored endpoint
    public void createTask(Long endpointId) {
        MonitoredEndpointReq req = monitoredEndpointService.getMonitoredEndpointReq(endpointId);
        addToThreadPool(req);
    }

    // update monitored endpoint
    public void updateTask(Long endpointId) {
        MonitoredEndpointReq req = monitoredEndpointService.getMonitoredEndpointReq(endpointId);
        removeFromThreadPool(endpointId);
        addToThreadPool(req);
    }

    // delete monitored endpoint
    public void deleteTask(Long endpointId) {
        removeFromThreadPool(endpointId);
    }

    //~ Private methods

    private void addToThreadPool(List<MonitoredEndpointReq> endpoints) {
        for (MonitoredEndpointReq req : endpoints) {
            addToThreadPool(req);
        }
    }

    private void removeFromThreadPool(Long endpointId) {
        Future threadToBeRemoved = runningThreads.remove(endpointId);
        threadToBeRemoved.cancel(true);
    }

    private void addToThreadPool(MonitoredEndpointReq req) {
        // create task
        CallMonitoredEndpointCmd task = new CallMonitoredEndpointCmd(req.getId(), req.getUrl(), req.getDateOfNextCheck(),
                monitoringResultService, monitoringRequestSender, this);

        // calculate delay
        int millis = (int) ChronoUnit.MILLIS.between(LocalDateTime.now(), req.getDateOfNextCheck());
        if (millis < 0 ) millis = 0;

        // add task to thread pool. After task is executed its deleted from thread pool
        ScheduledFuture<?> futureThread = threadPool.schedule(task, millis, TimeUnit.MILLISECONDS);
        runningThreads.put(task.getEndpointId(), futureThread);
    }
}
