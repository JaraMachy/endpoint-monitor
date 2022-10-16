package cz.machovec.endpointmonitor.monitoring;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class MonitoringResult {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;
    @Column(nullable = false)
    public LocalDateTime dateOfCheck;
    @Column(nullable = false)
    public Integer returnedHttpStatusCode;
    @Lob
    public String returnedPayload;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monitored_endpoint_id", nullable = false)
    public MonitoredEndpoint monitoredEndpoint;
}
