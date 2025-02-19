package org.gzunzu.domain.model;

public interface BasicEntity<T extends BasicEntity<T, K>, K> {

    K getId();

    void setId(final K id);

    void merge(final T other);
}
