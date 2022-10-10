package cz.machovec.endpointmonitor.security;

import cz.machovec.endpointmonitor.commons.repo.ByIdFinder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SecUserRepository extends JpaRepository<SecUser, Long>, ByIdFinder<Long, SecUser> {

    SecUser findByUsername(String username);

    @Query("SELECT secUser.id FROM SecUser secUser WHERE secUser.username = ?1")
    Long findSecUserIdByUsername(String username);

}
