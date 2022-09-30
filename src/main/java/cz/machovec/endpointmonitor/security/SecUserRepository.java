package cz.machovec.endpointmonitor.security;

import cz.machovec.endpointmonitor.commons.repo.ByIdFinder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecUserRepository extends JpaRepository<SecUser, Long>, ByIdFinder<Long, SecUser> {

}
