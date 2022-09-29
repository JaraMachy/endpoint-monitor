package cz.machovec.endpointmonitor.monitoring;

import cz.machovec.endpointmonitor.security.SecUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class MonitoredEndpoint {

    @Id
    @GeneratedValue(generator="idgen_monitored_endpoint", strategy= GenerationType.SEQUENCE)
    @SequenceGenerator(allocationSize = 1, name = "idgen_monitored_endpoint", sequenceName = "seq_monitored_endpoint")
    private Long id;
    private String name;
    private String url;
    private LocalDateTime dateOfCreation;
    private LocalDateTime dateOfLastCheck;
    private Integer monitoredInterval;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private SecUser owner;

}
