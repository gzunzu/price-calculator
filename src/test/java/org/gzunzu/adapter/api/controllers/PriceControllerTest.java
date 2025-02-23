package org.gzunzu.adapter.api.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.gzunzu.adapter.api.controller.PriceController;
import org.gzunzu.adapter.api.dto.request.PriceAddRq;
import org.gzunzu.adapter.api.dto.request.PricePriorityRq;
import org.gzunzu.adapter.api.dto.request.PriceUpdateRq;
import org.gzunzu.adapter.api.dto.response.BrandRs;
import org.gzunzu.adapter.api.dto.response.CurrencyRs;
import org.gzunzu.adapter.api.dto.response.PricePriorityRs;
import org.gzunzu.adapter.api.dto.response.PriceRs;
import org.gzunzu.adapter.api.dto.response.ProductRs;
import org.gzunzu.adapter.api.dto.response.RateRs;
import org.gzunzu.adapter.api.mapper.PriceMapper;
import org.gzunzu.domain.model.Brand;
import org.gzunzu.domain.model.Currency;
import org.gzunzu.domain.model.Price;
import org.gzunzu.domain.model.Product;
import org.gzunzu.domain.model.Rate;
import org.gzunzu.domain.ports.PriceService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
@Slf4j
class PriceControllerTest {

    private static AutoCloseable autoCloseable;
    @InjectMocks
    private PriceController uat;
    @Mock
    private PriceService service;
    @Mock
    private PriceMapper mapper;

    @BeforeAll
    static void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(PriceControllerTest.class);
    }

    @AfterAll
    static void tearDown() {
        try {
            autoCloseable.close();
        } catch (final Exception exception) {
            log.warn("Error trying to close {} class mocks.", PriceControllerTest.class, exception);
        }
    }

    @Test
    void test_get_entityFound() {
        final Price entity = getPriceEntitySample(1L, 1);
        final PriceRs dto = getPriceDtoSample(1L, 1);

        doReturn(entity)
                .when(service)
                .getById(1L);
        doReturn(dto)
                .when(mapper)
                .toRs(entity);

        final ResponseEntity<PriceRs> actual = uat.get(1L);

        verifyNoMoreInteractions(service,
                mapper);

        assertThat(actual)
                .isNotNull();
        assertThat(actual.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody())
                .isNotNull()
                .isEqualTo(dto);
    }

    @Test
    void test_get_entityNotFound() {
        doThrow(EntityNotFoundException.class)
                .when(service)
                .getById(1L);

        final ResponseEntity<PriceRs> actual = uat.get(1L);

        verifyNoMoreInteractions(service);
        verifyNoInteractions(mapper);

        assertThat(actual)
                .isNotNull();
        assertThat(actual.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(actual.getBody())
                .isNull();
    }

    @Test
    void test_get_anyException() {
        doThrow(InputMismatchException.class)
                .when(service)
                .getById(1L);

        final ResponseEntity<PriceRs> actual = uat.get(1L);

        verifyNoMoreInteractions(service);
        verifyNoInteractions(mapper);

        assertThat(actual)
                .isNotNull();
        assertThat(actual.getStatusCode())
                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(actual.getBody())
                .isNull();
    }

    @Test
    void test_getAll_entitiesFound() {
        final Price entity1 = getPriceEntitySample(1L, 1);
        final Price entity2 = getPriceEntitySample(2L, 1);
        final PriceRs dto1 = getPriceDtoSample(1L, 1);
        final PriceRs dto2 = getPriceDtoSample(2L, 1);

        doReturn(List.of(entity1, entity2))
                .when(service)
                .getAll();
        doReturn(List.of(dto1, dto2))
                .when(mapper)
                .toRs(List.of(entity1, entity2));

        final ResponseEntity<List<PriceRs>> actual = uat.getAll();

        verifyNoMoreInteractions(service,
                mapper);

        assertThat(actual)
                .isNotNull();
        assertThat(actual.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody())
                .isNotNull()
                .isEqualTo(List.of(dto1, dto2));
    }

    @Test
    void test_getAll_noEntitiesFound() {
        doReturn(Collections.emptyList())
                .when(service)
                .getAll();
        doReturn(Collections.emptyList())
                .when(mapper)
                .toRs(Collections.emptyList());

        final ResponseEntity<List<PriceRs>> actual = uat.getAll();

        verifyNoMoreInteractions(service,
                mapper);

        assertThat(actual)
                .isNotNull();
        assertThat(actual.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(actual.getBody())
                .isNull();
    }

    @Test
    void test_getAll_anyException() {
        doThrow(InputMismatchException.class)
                .when(service)
                .getAll();

        final ResponseEntity<List<PriceRs>> actual = uat.getAll();

        verifyNoMoreInteractions(service);
        verifyNoInteractions(mapper);

        assertThat(actual)
                .isNotNull();
        assertThat(actual.getStatusCode())
                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(actual.getBody())
                .isNull();
    }

    @Test
    void test_add_ok() {
        final PriceAddRq rq = getPriceAddRqSample(1, 1L);
        final Price entity = getPriceEntitySample(1L, 1);
        final PriceRs dto = getPriceDtoSample(1L, 1);

        doReturn(entity)
                .when(mapper)
                .toEntity(rq);
        doReturn(entity)
                .when(service)
                .save(entity);
        doReturn(dto)
                .when(mapper)
                .toRs(entity);

        final ResponseEntity<PriceRs> actual = uat.add(rq);

        verifyNoMoreInteractions(service,
                mapper);

        assertThat(actual)
                .isNotNull();
        assertThat(actual.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);
        assertThat(actual.getBody())
                .isNotNull()
                .isEqualTo(dto);
    }

    @Test
    void test_add_anyException() {
        final PriceAddRq rq = getPriceAddRqSample(1, 1L);
        final Price entity = getPriceEntitySample(1L, 1);

        doReturn(entity)
                .when(mapper)
                .toEntity(rq);
        doThrow(InputMismatchException.class)
                .when(service)
                .save(entity);

        final ResponseEntity<PriceRs> actual = uat.add(rq);

        verifyNoMoreInteractions(service,
                mapper);

        assertThat(actual)
                .isNotNull();
        assertThat(actual.getStatusCode())
                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(actual.getBody())
                .isNull();
    }

    @Test
    void test_update_ok() {
        final PriceUpdateRq rq = getPriceUpdateRqSample(1, 1L);
        final Price entity = getPriceEntitySample(1L, 1);
        final PriceRs dto = getPriceDtoSample(1L, 1);

        doReturn(entity)
                .when(mapper)
                .toEntity(rq);
        doReturn(entity)
                .when(service)
                .update(2L, entity);
        doReturn(dto)
                .when(mapper)
                .toRs(entity);

        final ResponseEntity<PriceRs> actual = uat.update(2L, rq);

        verifyNoMoreInteractions(service,
                mapper);

        assertThat(actual)
                .isNotNull();
        assertThat(actual.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody())
                .isNotNull()
                .isEqualTo(dto);
    }

    @Test
    void test_update_idNotFound() {
        final PriceUpdateRq rq = getPriceUpdateRqSample(1, 1L);
        final Price entity = getPriceEntitySample(1L, 1);

        doReturn(entity)
                .when(mapper)
                .toEntity(rq);
        doThrow(EntityNotFoundException.class)
                .when(service)
                .update(2L, entity);

        final ResponseEntity<PriceRs> actual = uat.update(2L, rq);

        verifyNoMoreInteractions(service,
                mapper);

        assertThat(actual)
                .isNotNull();
        assertThat(actual.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(actual.getBody())
                .isNull();
    }

    @Test
    void test_update_anyException() {
        final PriceUpdateRq rq = getPriceUpdateRqSample(1, 1L);
        final Price entity = getPriceEntitySample(1L, 1);

        doReturn(entity)
                .when(mapper)
                .toEntity(rq);
        doThrow(InputMismatchException.class)
                .when(service)
                .update(2L, entity);

        final ResponseEntity<PriceRs> actual = uat.update(2L, rq);

        verifyNoMoreInteractions(service,
                mapper);

        assertThat(actual)
                .isNotNull();
        assertThat(actual.getStatusCode())
                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(actual.getBody())
                .isNull();
    }

    @Test
    void test_delete_ok() {
        doNothing()
                .when(service)
                .delete(1L);

        final ResponseEntity<Void> actual = uat.delete(1L);

        verifyNoMoreInteractions(service);
        verifyNoInteractions(mapper);

        assertThat(actual)
                .isNotNull();
        assertThat(actual.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(actual.getBody())
                .isNull();
    }

    @Test
    void test_delete_anyException() {
        doThrow(InputMismatchException.class)
                .when(service)
                .delete(1L);

        final ResponseEntity<Void> actual = uat.delete(1L);

        verifyNoMoreInteractions(service);
        verifyNoInteractions(mapper);

        assertThat(actual)
                .isNotNull();
        assertThat(actual.getStatusCode())
                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(actual.getBody())
                .isNull();
    }

    @Test
    void test_deleteAll_ok() {
        doNothing()
                .when(service)
                .deleteAll();

        final ResponseEntity<Void> actual = uat.deleteAll();

        verifyNoMoreInteractions(service);
        verifyNoInteractions(mapper);

        assertThat(actual)
                .isNotNull();
        assertThat(actual.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(actual.getBody())
                .isNull();
    }

    @Test
    void test_deleteAll_anyException() {
        doThrow(InputMismatchException.class)
                .when(service)
                .deleteAll();

        final ResponseEntity<Void> actual = uat.deleteAll();

        verifyNoMoreInteractions(service);
        verifyNoInteractions(mapper);

        assertThat(actual)
                .isNotNull();
        assertThat(actual.getStatusCode())
                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(actual.getBody())
                .isNull();
    }

    @Test
    void test_getPriority_priceFound() {
        final PricePriorityRq rq = getPricePriorityRqSample();
        final PricePriorityRs dto = getPricePriorityDtoSample(1L);
        final Price entity = getPriceEntitySample(1L, 1);

        doReturn(entity)
                .when(service)
                .getHighestPriorityByProductIdAndBrandIdAndStartDateNotAfterAndEndDateNotBefore(rq.getBrandId(),
                        rq.getProductId(),
                        rq.getPurchaseDatetime());
        doReturn(dto)
                .when(mapper)
                .toPricePriorityRs(entity);

        final ResponseEntity<PricePriorityRs> actual = uat.getPriority(rq);

        verifyNoMoreInteractions(service,
                mapper);

        assertThat(actual)
                .isNotNull();
        assertThat(actual.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody())
                .isNotNull()
                .isEqualTo(dto);
    }

    @Test
    void test_getPriority_noPriceFound() {
        final PricePriorityRq rq = getPricePriorityRqSample();

        doThrow(EntityNotFoundException.class)
                .when(service)
                .getHighestPriorityByProductIdAndBrandIdAndStartDateNotAfterAndEndDateNotBefore(rq.getBrandId(),
                        rq.getProductId(),
                        rq.getPurchaseDatetime());

        final ResponseEntity<PricePriorityRs> actual = uat.getPriority(rq);

        verifyNoMoreInteractions(service);
        verifyNoInteractions(mapper);

        assertThat(actual)
                .isNotNull();
        assertThat(actual.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(actual.getBody())
                .isNull();
    }

    @Test
    void test_getPriority_anyException() {
        final PricePriorityRq rq = getPricePriorityRqSample();

        doThrow(InputMismatchException.class)
                .when(service)
                .getHighestPriorityByProductIdAndBrandIdAndStartDateNotAfterAndEndDateNotBefore(rq.getBrandId(),
                        rq.getProductId(),
                        rq.getPurchaseDatetime());

        final ResponseEntity<PricePriorityRs> actual = uat.getPriority(rq);

        verifyNoMoreInteractions(service);
        verifyNoInteractions(mapper);

        assertThat(actual)
                .isNotNull();
        assertThat(actual.getStatusCode())
                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(actual.getBody())
                .isNull();
    }

    private PriceAddRq getPriceAddRqSample(final Integer priority,
                                           final Long rateId,
                                           final LocalDateTime startDate,
                                           final LocalDateTime endDate) {
        return PriceAddRq.builder()
                .brandId(1)
                .productId(35455L)
                .startDate(startDate)
                .endDate(endDate)
                .rateId(rateId)
                .amount(new Random(10L).nextFloat())
                .currencyId(1)
                .priority(priority)
                .build();
    }

    private PriceAddRq getPriceAddRqSample(final Integer priority,
                                           final Long rateId) {
        return getPriceAddRqSample(priority, rateId, LocalDateTime.now().minusHours(1L), LocalDateTime.now().plusDays(1L).minusHours(1L));
    }

    private PriceUpdateRq getPriceUpdateRqSample(final Integer priority,
                                                 final Long rateId,
                                                 final LocalDateTime startDate,
                                                 final LocalDateTime endDate) {
        return PriceUpdateRq.builder()
                .brandId(1)
                .productId(35455L)
                .startDate(startDate)
                .endDate(endDate)
                .rateId(rateId)
                .amount(new Random(10L).nextFloat())
                .currencyId(1)
                .priority(priority)
                .build();
    }

    private PriceUpdateRq getPriceUpdateRqSample(final Integer priority,
                                                 final Long rateId) {
        return getPriceUpdateRqSample(priority, rateId, LocalDateTime.now().minusHours(1L), LocalDateTime.now().plusDays(1L).minusHours(1L));
    }

    private Price getPriceEntitySample(final Long id,
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

    private Price getPriceEntitySample(final Long id,
                                       final Integer priority) {
        return getPriceEntitySample(id, priority, LocalDateTime.now().minusHours(1L), LocalDateTime.now().plusDays(1L).minusHours(1L));
    }

    private PriceRs getPriceDtoSample(final Long id,
                                      final Integer priority,
                                      final LocalDateTime startDate,
                                      final LocalDateTime endDate) {
        return PriceRs.builder()
                .id(id)
                .brand(BrandRs.builder()
                        .id(1)
                        .name("BRAND1")
                        .build())
                .product(ProductRs.builder()
                        .id(1L)
                        .name("PRODUCT1")
                        .build())
                .startDate(startDate)
                .endDate(endDate)
                .rate(RateRs.builder()
                        .id(id)
                        .name(String.format("Standard rate %d", id))
                        .build())
                .amount(new Random(10L).nextFloat())
                .currency(CurrencyRs.builder()
                        .id(1)
                        .name("EUR")
                        .build())
                .priority(priority)
                .build();
    }

    private PriceRs getPriceDtoSample(final Long id,
                                      final Integer priority) {
        return getPriceDtoSample(id, priority, LocalDateTime.now().minusHours(1L), LocalDateTime.now().plusDays(1L).minusHours(1L));
    }

    private PricePriorityRs getPricePriorityDtoSample(final Long rateId,
                                                      final LocalDateTime startDate,
                                                      final LocalDateTime endDate) {
        return PricePriorityRs.builder()
                .brandId(1)
                .productId(1L)
                .rateId(rateId)
                .startDate(startDate)
                .endDate(endDate)
                .amount(new Random(10L).nextFloat())
                .currencyName("EUR")
                .build();
    }

    private PricePriorityRs getPricePriorityDtoSample(final Long rateId) {
        return getPricePriorityDtoSample(rateId, LocalDateTime.now().minusHours(1L), LocalDateTime.now().plusDays(1L).minusHours(1L));
    }

    private PricePriorityRq getPricePriorityRqSample(final LocalDateTime purchaseTime) {
        return PricePriorityRq.builder()
                .brandId(1)
                .productId(1L)
                .purchaseDatetime(purchaseTime)
                .build();
    }

    private PricePriorityRq getPricePriorityRqSample() {
        return getPricePriorityRqSample(LocalDateTime.now());
    }
}