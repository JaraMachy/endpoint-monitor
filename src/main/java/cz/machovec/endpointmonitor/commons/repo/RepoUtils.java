package cz.machovec.endpointmonitor.commons.repo;

import org.springframework.util.Assert;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

public class RepoUtils {

    /**
     * Finds record by its id. When no record is found then {@link EntityNotFoundException}
     * is thrown.
     *
     * @param id Id of record to find.
     * @return Found record.
     * @throws EntityNotFoundException when no entity is found for the given <code>id</code>.
     */
    public static <ID,T> T mustFindOneById(ID id, ByIdFinder<ID, T> byIdFinder) {
        Assert.notNull(id, "id is null");
        final Optional<T> optional = byIdFinder.findById(id);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Entity not found for id " + id);
        }
        return optional.get();
    }
}

