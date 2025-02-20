package org.gzunzu.domain.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.gzunzu.domain.model.Brand;
import org.gzunzu.domain.model.Currency;
import org.gzunzu.domain.model.Price;
import org.gzunzu.domain.model.Product;
import org.gzunzu.domain.model.Rate;
import org.gzunzu.domain.repositories.PriceRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
class PriceServiceImplTest {

    private static AutoCloseable autoCloseable;
    @InjectMocks
    private PriceServiceImpl uat;
    @Mock
    private PriceRepository repository;

    @BeforeAll
    static void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(PriceServiceImplTest.class);
    }

    @AfterAll
    static void tearDown() {
        try {
            autoCloseable.close();
        } catch (final Exception exception) {
            log.warn("Error trying to close {} class mocks.", PriceServiceImplTest.class, exception);
        }
    }

    @Test
    void test_getById_entityFound() {
        final Price entity = getSamplePrice(1L, 1);

        doReturn(Optional.of(entity))
                .when(repository)
                .findById(1L);

        final Price actual = uat.getById(1L);

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
        final Price entity1 = getSamplePrice(1L, 1);
        final Price entity2 = getSamplePrice(2L, 2);

        doReturn(List.of(entity1, entity2))
                .when(repository)
                .findAll();

        final List<Price> actual = uat.getAll();

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

        final List<Price> actual = uat.getAll();

        assertThat(actual)
                .isNotNull()
                .isEmpty();
        verify(repository)
                .findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_save_notNullEntity() {
        final Price entity = getSamplePrice(1L, 1);

        doReturn(entity)
                .when(repository)
                .save(entity);

        final Price actual = uat.save(entity);

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
        final LocalDateTime previousStartDate = LocalDateTime.now().minusHours(1L);
        final LocalDateTime previousEndDate = LocalDateTime.now().plusDays(1L).minusHours(1L);
        final Price entity1 = getSamplePrice(1L, 1, previousStartDate, previousEndDate);
        final LocalDateTime newStartDate = LocalDateTime.now().plusHours(1L);
        final LocalDateTime newEndDate = LocalDateTime.now().plusDays(1L).plusHours(1L);
        final Price entity2 = getSamplePrice(2L, 2, newStartDate, newEndDate);

        doReturn(Optional.of(entity1))
                .when(repository)
                .findById(1L);
        doReturn(entity1)
                .when(repository)
                .save(entity1);

        final Price actual = uat.update(1L, entity2);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(entity1);
        assertThat(actual.getId())
                .isEqualTo(1L);
        assertThat(actual.getBrand())
                .isEqualTo(entity1.getBrand());
        assertThat(actual.getProduct())
                .isEqualTo(entity1.getProduct());
        assertThat(actual.getPriority())
                .isNotEqualTo(1)
                .isEqualTo(2);
        assertThat(actual.getStartDate())
                .isNotEqualTo(previousStartDate)
                .isEqualTo(newStartDate);
        assertThat(actual.getEndDate())
                .isNotEqualTo(previousEndDate)
                .isEqualTo(newEndDate);
        assertThat(actual.getRate())
                .isEqualTo(entity2.getRate());
        assertThat(actual.getAmount())
                .isEqualTo(entity2.getAmount());
        assertThat(actual.getCurrency())
                .isEqualTo(entity2.getCurrency());

        verify(repository)
                .findById(1L);
        verify(repository)
                .save(entity1);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_update_notFoundEntity() {
        final Price entity2 = getSamplePrice(2L, 2);

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
        final Price entity1 = getSamplePrice(1L, 1);

        doReturn(Optional.of(entity1))
                .when(repository)
                .findById(1L);
        doReturn(entity1)
                .when(repository)
                .save(entity1);

        final Price actual = uat.update(1L, null);

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
    void test_getByBrandId_pricesFound() {
        final Price entity1 = getSamplePrice(1L, 1);
        final Price entity2 = getSamplePrice(2L, 2);

        doReturn(List.of(entity1, entity2))
                .when(repository)
                .findByBrandId(1);

        final List<Price> actual = uat.getByBrandId(1);

        assertThat(actual)
                .isNotEmpty()
                .contains(entity1, entity2);

        verify(repository)
                .findByBrandId(1);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_getByBrandId_pricesNotFound() {
        doReturn(Collections.emptyList())
                .when(repository)
                .findByBrandId(1);

        final List<Price> actual = uat.getByBrandId(1);

        assertThat(actual)
                .isNotNull()
                .isEmpty();

        verify(repository)
                .findByBrandId(1);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_getByBrandId_nullBrandId() {
        doReturn(CollectionUtils.emptyCollection())
                .when(repository)
                .findByBrandId(null);

        final List<Price> actual = uat.getByBrandId(null);

        assertThat(actual)
                .isNotNull()
                .isEmpty();

        verify(repository)
                .findByBrandId(null);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_getByProductId_pricesFound() {
        final Price entity1 = getSamplePrice(1L, 1);
        final Price entity2 = getSamplePrice(2L, 2);

        doReturn(List.of(entity1, entity2))
                .when(repository)
                .findByProductId(1L);

        final List<Price> actual = uat.getByProductId(1L);

        assertThat(actual)
                .isNotEmpty()
                .contains(entity1, entity2);

        verify(repository)
                .findByProductId(1L);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_getByProductId_pricesNotFound() {
        doReturn(Collections.emptyList())
                .when(repository)
                .findByProductId(1L);

        final List<Price> actual = uat.getByProductId(1L);

        assertThat(actual)
                .isNotNull()
                .isEmpty();

        verify(repository)
                .findByProductId(1L);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_getByProductId_nullProductId() {
        doReturn(CollectionUtils.emptyCollection())
                .when(repository)
                .findByProductId(null);

        final List<Price> actual = uat.getByProductId(null);

        assertThat(actual)
                .isNotNull()
                .isEmpty();

        verify(repository)
                .findByProductId(null);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_getByStartDateNotAfterAndEndDateNotBefore_pricesFound() {
        final LocalDateTime now = LocalDateTime.now();

        final Price entity1 = getSamplePrice(1L, 1, now.minusDays(1L), now.plusDays(1L));
        final Price entity2 = getSamplePrice(2L, 2, now.minusDays(1L), now.plusDays(1L));

        doReturn(List.of(entity1, entity2))
                .when(repository)
                .findByStartDateLessThanEqualAndEndDateGreaterThanEqual(now, now);

        final List<Price> actual = uat.getByStartDateNotAfterAndEndDateNotBefore(now);

        assertThat(actual)
                .isNotEmpty()
                .contains(entity1, entity2);

        verify(repository)
                .findByStartDateLessThanEqualAndEndDateGreaterThanEqual(now, now);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_getByStartDateNotAfterAndEndDateNotBefore_pricesNotFound() {
        final LocalDateTime now = LocalDateTime.now();

        doReturn(Collections.emptyList())
                .when(repository)
                .findByStartDateLessThanEqualAndEndDateGreaterThanEqual(now, now);

        final List<Price> actual = uat.getByStartDateNotAfterAndEndDateNotBefore(now);

        assertThat(actual)
                .isNotNull()
                .isEmpty();

        verify(repository)
                .findByStartDateLessThanEqualAndEndDateGreaterThanEqual(now, now);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_getByStartDateNotAfterAndEndDateNotBefore_nullDate() {
        doReturn(CollectionUtils.emptyCollection())
                .when(repository)
                .findByStartDateLessThanEqualAndEndDateGreaterThanEqual(null, null);

        final List<Price> actual = uat.getByStartDateNotAfterAndEndDateNotBefore(null);

        assertThat(actual)
                .isNotNull()
                .isEmpty();

        verify(repository)
                .findByStartDateLessThanEqualAndEndDateGreaterThanEqual(null, null);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_getHighestPriorityByProductIdAndBrandIdAndStartDateNotAfterAndEndDateNotBefore_pricesFound() {
        final LocalDateTime now = LocalDateTime.now();

        final Price entity1 = getSamplePrice(1L, 1, now.minusDays(1L), now.plusDays(1L));
        final Price entity2 = getSamplePrice(2L, 2, now.minusDays(1L), now.plusDays(1L));

        doReturn(List.of(entity1, entity2))
                .when(repository)
                .findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(1, 1L, now, now);

        final Price actual = uat.getHighestPriorityByProductIdAndBrandIdAndStartDateNotAfterAndEndDateNotBefore(1, 1L, now);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(entity2);

        verify(repository)
                .findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(1, 1L, now, now);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_getHighestPriorityByProductIdAndBrandIdAndStartDateNotAfterAndEndDateNotBefore_pricesNotFound() {
        final LocalDateTime now = LocalDateTime.now();

        doReturn(Collections.emptyList())
                .when(repository)
                .findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(1, 1L, now, now);

        assertThatThrownBy(() -> uat.getHighestPriorityByProductIdAndBrandIdAndStartDateNotAfterAndEndDateNotBefore(1, 1L, now))
                .isInstanceOf(EntityNotFoundException.class);

        verify(repository)
                .findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(1, 1L, now, now);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_getHighestPriorityByProductIdAndBrandIdAndStartDateNotAfterAndEndDateNotBefore_nullDate() {
        final LocalDateTime now = LocalDateTime.now();

        final Price entity1 = getSamplePrice(1L, 1, now.minusDays(1L), now.plusDays(1L));
        final Price entity2 = getSamplePrice(2L, 2, now.minusDays(1L), now.plusDays(1L));

        doReturn(List.of(entity1, entity2))
                .when(repository)
                .findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(1, 1L, null, null);

        final Price actual = uat.getHighestPriorityByProductIdAndBrandIdAndStartDateNotAfterAndEndDateNotBefore(1, 1L, null);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(entity2);

        verify(repository)
                .findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(1, 1L, null, null);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void test_getEntityClassName_ok() {
        final String actual = uat.getEntityClassName();

        assertThat(actual)
                .isNotBlank()
                .isEqualTo("Price");

        verifyNoInteractions(repository);
    }

    private Price getSamplePrice(final Long id,
                                 final Integer priority,
                                 final LocalDateTime startDate,
                                 final LocalDateTime endDate) {
        return Price.builder()
                .id(id)
                .brand(Brand.builder()
                        .id(1)
                        .name("BRAND1")
                        .build())
                .product(Product.builder()
                        .id(1L)
                        .name("PRODUCT1")
                        .build())
                .startDate(startDate)
                .endDate(endDate)
                .rate(Rate.builder()
                        .id(id)
                        .name(String.format("Standard rate %d", id))
                        .build())
                .amount(new Random(10L).nextFloat())
                .currency(Currency.builder()
                        .id(1)
                        .name("EUR")
                        .build())
                .priority(priority)
                .build();
    }

    private Price getSamplePrice(final Long id,
                                 final Integer priority) {
        return getSamplePrice(id, priority, LocalDateTime.now().minusHours(1L), LocalDateTime.now().plusDays(1L).minusHours(1L));
    }
}