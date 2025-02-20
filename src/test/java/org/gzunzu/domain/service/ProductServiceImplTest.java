package org.gzunzu.domain.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.gzunzu.domain.model.Product;
import org.gzunzu.domain.repositories.ProductRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
@Slf4j
class ProductServiceImplTest {

    private static AutoCloseable autoCloseable;
    @InjectMocks
    private ProductServiceImpl uat;
    @Mock
    private ProductRepository repository;

    @BeforeAll
    static void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(ProductServiceImplTest.class);
    }

    @AfterAll
    static void tearDown() {
        try {
            autoCloseable.close();
        } catch (final Exception exception) {
            log.warn("Error trying to close {} class mocks.", ProductServiceImplTest.class, exception);
        }
    }

    @Test
    void test_getById_entityFound() {
        final Product entity = Product.builder()
                .id(1L)
                .name("ENTITY1")
                .build();

        doReturn(Optional.of(entity))
                .when(repository)
                .findById(1L);

        final Product actual = uat.getById(1L);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(entity);
        assertThat(actual.getId())
                .isEqualTo(1L);
        verify(repository)
                .findById(1L);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_getById_entityNotFound() {
        doReturn(Optional.empty())
                .when(repository)
                .findById(1L);

        assertThatThrownBy(() -> uat.getById(1L))
                .isInstanceOf(EntityNotFoundException.class);

        verify(repository)
                .findById(1L);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_getAll_entitiesFound() {
        final Product entity1 = Product.builder()
                .id(1L)
                .name("ENTITY1")
                .build();
        final Product entity2 = Product.builder()
                .id(2L)
                .name("ENTITY2")
                .build();

        doReturn(List.of(entity1, entity2))
                .when(repository)
                .findAll();

        final List<Product> actual = uat.getAll();

        assertThat(actual)
                .isNotEmpty()
                .contains(entity1, entity2);
        verify(repository)
                .findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_getAll_entitiesNotFound() {
        doReturn(CollectionUtils.emptyCollection())
                .when(repository)
                .findAll();

        final List<Product> actual = uat.getAll();

        assertThat(actual)
                .isNotNull()
                .isEmpty();
        verify(repository)
                .findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_save_notNullEntity() {
        final Product entity = Product.builder()
                .id(1L)
                .name("ENTITY1")
                .build();

        doReturn(entity)
                .when(repository)
                .save(entity);

        final Product actual = uat.save(entity);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(entity);
        verify(repository)
                .save(entity);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_save_nullEntity() {
        doThrow(IllegalArgumentException.class)
                .when(repository)
                .save(null);

        assertThatThrownBy(() -> uat.save(null))
                .isInstanceOf(IllegalArgumentException.class);

        verify(repository)
                .save(null);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_update_notNullEntity() {
        final Product entity1 = Product.builder()
                .id(1L)
                .name("ENTITY1")
                .build();
        final Product entity2 = Product.builder()
                .name("ENTITY2")
                .build();

        doReturn(Optional.of(entity1))
                .when(repository)
                .findById(1L);
        doReturn(entity1)
                .when(repository)
                .save(entity1);

        final Product actual = uat.update(1L, entity2);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(entity1);
        assertThat(actual.getId())
                .isEqualTo(1L);
        assertThat(actual.getName())
                .isEqualTo("ENTITY2");

        verify(repository)
                .findById(1L);
        verify(repository)
                .save(entity1);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_update_notFoundEntity() {
        final Product entity2 = Product.builder()
                .name("ENTITY2")
                .build();

        doReturn(Optional.empty())
                .when(repository)
                .findById(1L);

        assertThatThrownBy(() -> uat.update(1L, entity2))
                .isInstanceOf(EntityNotFoundException.class);

        verify(repository)
                .findById(1L);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_update_nullEntity() {
        final Product entity1 = Product.builder()
                .id(1L)
                .name("ENTITY2")
                .build();

        doReturn(Optional.of(entity1))
                .when(repository)
                .findById(1L);
        doReturn(entity1)
                .when(repository)
                .save(entity1);

        final Product actual = uat.update(1L, null);

        assertThat(actual)
                .isEqualTo(entity1);
        verify(repository)
                .findById(1L);
        verify(repository)
                .save(entity1);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_delete_notNullId() {
        doNothing()
                .when(repository)
                .deleteById(1L);

        assertThatNoException()
                .isThrownBy(() -> uat.delete(1L));

        verify(repository)
                .deleteById(1L);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_delete_nullId() {
        doThrow(IllegalArgumentException.class)
                .when(repository)
                .deleteById(null);

        assertThatThrownBy(() -> uat.delete(null))
                .isInstanceOf(IllegalArgumentException.class);

        verify(repository)
                .deleteById(null);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_deleteAll_ok() {
        doNothing()
                .when(repository)
                .deleteAll();

        assertThatNoException()
                .isThrownBy(() -> uat.deleteAll());

        verify(repository)
                .deleteAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_getEntityClassName_ok() {
        final String actual = uat.getEntityClassName();

        assertThat(actual)
                .isNotBlank()
                .isEqualTo("Product");

        verifyNoInteractions(repository);
    }
}