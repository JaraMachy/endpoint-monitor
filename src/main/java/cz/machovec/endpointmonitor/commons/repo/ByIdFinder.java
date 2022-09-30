package cz.machovec.endpointmonitor.commons.repo;

import java.util.Optional;

public interface ByIdFinder<ID, T> {

    Optional<T> findById(ID id);

}
