package org.gzunzu.domain.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.gzunzu.domain.model.Currency;
import org.gzunzu.domain.repositories.CurrencyRepository;
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
class CurrencyServiceImplTest {

    private static AutoCloseable autoCloseable;
    @InjectMocks
    private CurrencyServiceImpl uat;
    @Mock
    private CurrencyRepository repository;

    @BeforeAll
    static void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(CurrencyServiceImplTest.class);
    }

    @AfterAll
    static void tearDown() {
        try {
            autoCloseable.close();
        } catch (final Exception exception) {
            log.warn("Error trying to close {} class mocks.", CurrencyServiceImplTest.class, exception);
        }
    }

    @Test
    void test_getById_entityFound() {
        final Currency entity = Currency.builder()
                .id(1)
                .name("ENTITY1")
                .build();

        doReturn(Optional.of(entity))
                .when(repository)
                .findById(1);

        final Currency actual = uat.getById(1);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(entity);
        assertThat(actual.getId())
                .isEqualTo(1);
        verify(repository)
                .findById(1);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_getById_entityNotFound() {
        doReturn(Optional.empty())
                .when(repository)
                .findById(1);

        assertThatThrownBy(() -> uat.getById(1))
                .isInstanceOf(EntityNotFoundException.class);

        verify(repository)
                .findById(1);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_getAll_entitiesFound() {
        final Currency entity1 = Currency.builder()
                .id(1)
                .name("ENTITY1")
                .build();
        final Currency entity2 = Currency.builder()
                .id(2)
                .name("ENTITY2")
                .build();

        doReturn(List.of(entity1, entity2))
                .when(repository)
                .findAll();

        final List<Currency> actual = uat.getAll();

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

        final List<Currency> actual = uat.getAll();

        assertThat(actual)
                .isNotNull()
                .isEmpty();
        verify(repository)
                .findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_save_notNullEntity() {
        final Currency entity = Currency.builder()
                .id(1)
                .name("ENTITY1")
                .build();

        doReturn(entity)
                .when(repository)
                .save(entity);

        final Currency actual = uat.save(entity);

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
        final Currency entity1 = Currency.builder()
                .id(1)
                .name("ENTITY1")
                .build();
        final Currency entity2 = Currency.builder()
                .name("ENTITY2")
                .build();

        doReturn(Optional.of(entity1))
                .when(repository)
                .findById(1);
        doReturn(entity1)
                .when(repository)
                .save(entity1);

        final Currency actual = uat.update(1, entity2);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(entity1);
        assertThat(actual.getId())
                .isEqualTo(1);
        assertThat(actual.getName())
                .isEqualTo("ENTITY2");

        verify(repository)
                .findById(1);
        verify(repository)
                .save(entity1);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_update_notFoundEntity() {
        final Currency entity2 = Currency.builder()
                .name("ENTITY2")
                .build();

        doReturn(Optional.empty())
                .when(repository)
                .findById(1);

        assertThatThrownBy(() -> uat.update(1, entity2))
                .isInstanceOf(EntityNotFoundException.class);

        verify(repository)
                .findById(1);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_update_nullEntity() {
        final Currency entity1 = Currency.builder()
                .id(1)
                .name("ENTITY2")
                .build();

        doReturn(Optional.of(entity1))
                .when(repository)
                .findById(1);
        doReturn(entity1)
                .when(repository)
                .save(entity1);

        final Currency actual = uat.update(1, null);

        assertThat(actual)
                .isEqualTo(entity1);
        verify(repository)
                .findById(1);
        verify(repository)
                .save(entity1);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_delete_notNullId() {
        doNothing()
                .when(repository)
                .deleteById(1);

        assertThatNoException()
                .isThrownBy(() -> uat.delete(1));

        verify(repository)
                .deleteById(1);
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
                .isEqualTo("Currency");

        verifyNoInteractions(repository);
    }
}