package cz.machovec.endpointmonitor.monitoring;

import cz.machovec.endpointmonitor.security.SecUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(profiles="test")
public class MonitoredEndpointRepositoryTest {

    @Autowired
    protected MonitoredEndpointRepository repo;

    @Test
    public void testFindOwnerById() {
        // Case 1 - correct
        // when
        Long id = 1000L;
        SecUser secUser = repo.findOwnerById(id);

        // then
        assertNotNull(secUser);
        assertEquals(2000L, secUser.getId());

        // Case 2 - not existing endpoint with given id
        // when
        id = 4000L;
        secUser = repo.findOwnerById(id);

        // then
        assertNull(secUser);
    }
}
