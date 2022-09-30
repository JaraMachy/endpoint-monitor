package cz.machovec.endpointmonitor.monitoring;

import cz.machovec.endpointmonitor.security.SecUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@EntityListeners({AuditingEntityListener.class})
public class MonitoredEndpoint {

    @Id
    @GeneratedValue(generator="idgen_monitored_endpoint", strategy= GenerationType.SEQUENCE)
    @SequenceGenerator(allocationSize = 1, name = "idgen_monitored_endpoint", sequenceName = "seq_monitored_endpoint")
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String url;
    @CreatedDate
    private LocalDateTime dateOfCreation;
    private LocalDateTime dateOfLastCheck;
    @Column(nullable = false)
    private Integer monitoredInterval;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private SecUser owner;

}
