package cz.machovec.endpointmonitor.monitoring;

import cz.machovec.endpointmonitor.security.SecUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter @Setter
@EntityListeners({AuditingEntityListener.class})
public class MonitoredEndpoint {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String url;
    @CreatedDate
    private LocalDateTime dateOfCreation;
    @Column
    private LocalDateTime dateOfLastCheck;
    @Column(nullable = false)
    private Integer monitoredInterval;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private SecUser owner;
    @OneToMany(mappedBy = "monitoredEndpoint", cascade = CascadeType.ALL)
    private Set<MonitoringResult> monitoringResults;

    public LocalDateTime getDateOfNextCheck() {
        if (this.getDateOfLastCheck() != null && this.getDateOfLastCheck().plusSeconds(monitoredInterval).isAfter(LocalDateTime.now())) {
            return this.getDateOfLastCheck().plusSeconds(monitoredInterval);
        } else {
            return LocalDateTime.now();
        }
    }

}
