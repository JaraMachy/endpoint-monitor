package cz.machovec.endpointmonitor.monitoring;

import cz.machovec.endpointmonitor.commons.repo.RepoUtils;
import cz.machovec.endpointmonitor.security.SecUser;
import cz.machovec.endpointmonitor.security.SecUserRepository;
import cz.machovec.endpointmonitor.security.SecurityAccessHelper;
import lombok.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Transactional
public class MonitoredEndpointServiceImpl implements MonitoredEndpointService {

    private final @NonNull SecUserRepository secUserRepo;
    private final @NonNull SecurityAccessHelper securityAccessHelper;
    private final @NonNull MonitoredEndpointRepository monitoredEndpointRepo;
    private final @NonNull MonitoredEndpointFactory monitoredEndpointFactory;
    private final MonitoringThreadPoolManager monitoringThreadPoolManager;

    public MonitoredEndpointServiceImpl(@NonNull SecUserRepository secUserRepo,
                                        @NonNull SecurityAccessHelper securityAccessHelper,
                                        @NonNull MonitoredEndpointRepository monitoredEndpointRepo,
                                        @NonNull MonitoredEndpointFactory monitoredEndpointFactory,
                                        @Lazy MonitoringThreadPoolManager monitoringThreadPoolManager) {
        this.secUserRepo = secUserRepo;
        this.securityAccessHelper = securityAccessHelper;
        this.monitoredEndpointRepo = monitoredEndpointRepo;
        this.monitoredEndpointFactory = monitoredEndpointFactory;
        this.monitoringThreadPoolManager = monitoringThreadPoolManager;
    }

    @Override
    public Long createMonitoredEndpoint(SaveMonitoredEndpointIn monitoredEndpointIn) {
        Assert.notNull(monitoredEndpointIn, "monitoredEndpointIn must not be null!");

        SecUser secUser = securityAccessHelper.getLoggedUser();
        MonitoredEndpoint monitoredEndpoint = monitoredEndpointFactory.createFrom(monitoredEndpointIn, secUser);

        monitoredEndpointRepo.save(monitoredEndpoint);

        monitoringThreadPoolManager.createTask(monitoredEndpoint.getId());

        return monitoredEndpoint.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public GetMonitoredEndpointOut getMonitoredEndpoint(Long monitoredEndpointId) {
        Assert.notNull(monitoredEndpointId, "monitoredEndpointId must not be null!");

        return MonitoredEndpointMappers.fromMonitoredEndpoint(RepoUtils.mustFindOneById(monitoredEndpointId, monitoredEndpointRepo));
    }

    @Override
    public UpdateMonitoredEndpointOut updateMonitoredEndpoint(SaveMonitoredEndpointIn monitoredEndpointIn, Long monitoredEndpointId) {
        Assert.notNull(monitoredEndpointIn, "monitoredEndpointIn must not be null!");
        Assert.notNull(monitoredEndpointId, "monitoredEndpointId must not be null!");

        MonitoredEndpoint monitoredEndpoint = RepoUtils.mustFindOneById(monitoredEndpointId, monitoredEndpointRepo);

        UpdateMonitoredEndpointOut out = new UpdateMonitoredEndpointOut();
        out.setMonitoredEndpointId(monitoredEndpointId);
        monitoredEndpointFactory.updateFrom(monitoredEndpointIn, monitoredEndpoint);
        monitoredEndpointRepo.save(monitoredEndpoint);
        out.setUpdated(true);

        monitoringThreadPoolManager.updateTask(monitoredEndpoint.getId());

        return out;
    }

    @Override
    public DeleteMonitoredEndpointOut deleteMonitoredEndpoint(Long monitoredEndpointId) {
        Assert.notNull(monitoredEndpointId, "monitoredEndpointId must not be null!");

        MonitoredEndpoint monitoredEndpoint = RepoUtils.mustFindOneById(monitoredEndpointId, monitoredEndpointRepo);

        DeleteMonitoredEndpointOut out = new DeleteMonitoredEndpointOut();
        out.setMonitoredEndpointId(monitoredEndpointId);
        monitoredEndpointRepo.delete(monitoredEndpoint);

        out.setDeleted(true);

        monitoringThreadPoolManager.deleteTask(out.getMonitoredEndpointId());

        return out;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetMonitoredEndpointOut> getMonitoredEndpoints(Long secUserId) {

        SecUser requestingSecUser = RepoUtils.mustFindOneById(secUserId, secUserRepo);
        List<MonitoredEndpoint> monitoredEndpoints = monitoredEndpointRepo.findAllByOwner(requestingSecUser);

        return MonitoredEndpointMappers.fromMonitoredEndpoints(monitoredEndpoints);
    }

    @Override
    @Transactional(readOnly = true)
    public MonitoredEndpointReq getMonitoredEndpointReq(Long monitoredEndpointId) {

        MonitoredEndpoint monitoredEndpoint = RepoUtils.mustFindOneById(monitoredEndpointId, monitoredEndpointRepo);

        return MonitoredEndpointMappers.fromMonitoredEndpointToReq(monitoredEndpoint);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MonitoredEndpointReq> getAllMonitoredEndpointReqs() {

        List<MonitoredEndpoint> monitoredEndpoints = monitoredEndpointRepo.findAll();

        return MonitoredEndpointMappers.fromMonitoredEndpointToReqs(monitoredEndpoints);
    }
}
