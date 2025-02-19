package org.gzunzu.domain.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.gzunzu.domain.model.BasicEntity;
import org.gzunzu.domain.ports.BasicEntityService;
import org.gzunzu.domain.repositories.BasicEntityRepository;

import java.util.List;

@RequiredArgsConstructor
public abstract class BasicEntityServiceImpl<E extends BasicEntity<E, K>, K> implements BasicEntityService<E, K> {

    protected final BasicEntityRepository<E, K> repository;

    @Override
    public final E getById(final K id) throws EntityNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No %s configured in DB with ID «%s»", getEntityClassName(), id)));
    }

    @Override
    public List<E> getAll() {
        return repository.findAll();
    }

    @Override
    public E save(final E entity) {
        return repository.save(entity);
    }

    @Override
    public E update(final K id, final E entity) throws EntityNotFoundException {
        final E currentEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No %s configured in DB with ID «%s»", getEntityClassName(), id)));
        currentEntity.merge(entity);
        return repository.save(currentEntity);
    }

    @Override
    public void delete(final K id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    protected abstract String getEntityClassName();
}
