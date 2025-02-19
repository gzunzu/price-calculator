package org.gzunzu.domain.ports;


import jakarta.persistence.EntityNotFoundException;
import org.gzunzu.domain.model.BasicEntity;

import java.util.List;

public interface BasicEntityService<E extends BasicEntity<E, K>, K> {

    E getById(final K id) throws EntityNotFoundException;

    List<E> getAll();

    E save(final E entity);

    E update(final K id, final E entity) throws EntityNotFoundException;

    void delete(final K id);

    void deleteAll();
}
