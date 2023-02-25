package cz.machovec.endpointmonitor.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(profiles="test")
public class SecUserRepositoryTest {

    @Autowired
    private SecUserRepository repository;

    @Test
    public void testFindSecUser() {
        // when
        String username = "Tyrion Lannister";
        SecUser secUser = repository.findByUsername(username);
        // then
        assertNotNull(secUser);
        assertTrue(secUser.getId().equals(2000L));
        assertTrue(secUser.getUsername().equals("Tyrion Lannister"));

        // when
        username = "Gandalf";
        secUser = repository.findByUsername(username);
        // then
        assertNull(secUser);
    }

    @Test
    public void testFindSecUserIdByUsername() {
        //when
        String username = "Aria Stark";
        Long id = repository.findSecUserIdByUsername(username);
        //then
        assertNotNull(id);
        assertEquals(2001, id);
    }
}
