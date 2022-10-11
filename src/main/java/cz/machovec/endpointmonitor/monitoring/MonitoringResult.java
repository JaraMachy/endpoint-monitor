package cz.machovec.endpointmonitor.monitoring;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class MonitoringResult {

    @Id
    @GeneratedValue(generator="idgen_monitoring_result", strategy= GenerationType.SEQUENCE)
    @SequenceGenerator(allocationSize = 1, name = "idgen_monitoring_result", sequenceName = "seq_monitoring_result")
    public Long id;
    @Column(nullable = false)
    public LocalDateTime dateOfCheck;
    @Column(nullable = false)
    public Integer returnedHttpStatusCode;
    @Column
    public String returnedPayload;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monitored_endpoint_id", nullable = false)
    public MonitoredEndpoint monitoredEndpoint;
}
