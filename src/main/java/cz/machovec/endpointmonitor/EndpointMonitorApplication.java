package cz.machovec.endpointmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EndpointMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(EndpointMonitorApplication.class, args);
    }

}
