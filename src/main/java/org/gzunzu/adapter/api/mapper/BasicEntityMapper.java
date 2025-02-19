package org.gzunzu.adapter.api.mapper;

import org.gzunzu.adapter.api.dto.request.BasicCrudAddRq;
import org.gzunzu.adapter.api.dto.request.BasicCrudUpdateRq;
import org.gzunzu.adapter.api.dto.response.BasicCrudRs;
import org.gzunzu.domain.model.BasicEntity;

import java.util.List;

public interface BasicEntityMapper<E extends BasicEntity<E, K>,
        K,
        A extends BasicCrudAddRq,
        U extends BasicCrudUpdateRq,
        R extends BasicCrudRs> {

    R toRs(final E entity);

    List<R> toRs(final List<E> entities);

    E toEntity(final A rq);

    E toEntity(final U rq);
}
