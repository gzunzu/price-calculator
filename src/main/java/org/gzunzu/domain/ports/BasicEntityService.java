package org.gzunzu.domain.ports;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.gzunzu.domain.model.BasicEntity;

import java.util.List;

public interface BasicEntityService<E extends BasicEntity<E, K>, K> {

    E getById(@NotNull final K id) throws EntityNotFoundException;

    List<E> getAll();

    E save(@NotNull final E entity);

    E update(@NotNull final K id, @NotNull final E entity) throws EntityNotFoundException;

    void delete(@NotNull final K id);

    void deleteAll();
}
