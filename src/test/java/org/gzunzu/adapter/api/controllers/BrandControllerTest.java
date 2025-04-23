package org.gzunzu.adapter.api.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.gzunzu.adapter.api.controller.BrandController;
import org.gzunzu.adapter.api.dto.request.BrandAddRq;
import org.gzunzu.adapter.api.dto.request.BrandUpdateRq;
import org.gzunzu.adapter.api.dto.response.BrandRs;
import org.gzunzu.adapter.api.mapper.BrandMapper;
import org.gzunzu.domain.model.Brand;
import org.gzunzu.domain.ports.BrandService;
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

import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
@Slf4j
class BrandControllerTest {

    private static AutoCloseable autoCloseable;
    @InjectMocks
    private BrandController uat;
    @Mock
    private BrandService service;
    @Mock
    private BrandMapper mapper;

    @BeforeAll
    static void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(BrandControllerTest.class);
    }

    @AfterAll
    static void tearDown() {
        try {
            autoCloseable.close();
        } catch (final Exception exception) {
            log.warn("Error trying to close {} class mocks.", BrandControllerTest.class, exception);
        }
    }

    @Test
    void test_get_entityFound() {
        final Brand entity = Brand.builder()
                .id(1)
                .name("ENTITY1")
                .build();
        final BrandRs dto = BrandRs.builder()
                .id(1)
                .name("ENTITY1")
                .build();

        doReturn(entity)
                .when(service)
                .getById(1);
        doReturn(dto)
                .when(mapper)
                .toRs(entity);

        final ResponseEntity<BrandRs> actual = uat.get(1);

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
                .getById(1);

        final Throwable thrown = catchThrowable(() -> uat.get(1));

        verifyNoMoreInteractions(service);
        verifyNoInteractions(mapper);

        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void test_get_anyException() {
        doThrow(InputMismatchException.class)
                .when(service)
                .getById(1);

        final Throwable thrown = catchThrowable(() -> uat.get(1));

        verifyNoMoreInteractions(service);
        verifyNoInteractions(mapper);

        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(InputMismatchException.class);
    }

    @Test
    void test_getAll_entitiesFound() {
        final Brand entity1 = Brand.builder()
                .id(1)
                .name("ENTITY1")
                .build();
        final Brand entity2 = Brand.builder()
                .id(2)
                .name("ENTITY2")
                .build();
        final BrandRs dto1 = BrandRs.builder()
                .id(1)
                .name("ENTITY1")
                .build();
        final BrandRs dto2 = BrandRs.builder()
                .id(2)
                .name("ENTITY2")
                .build();

        doReturn(List.of(entity1, entity2))
                .when(service)
                .getAll();
        doReturn(List.of(dto1, dto2))
                .when(mapper)
                .toRs(List.of(entity1, entity2));

        final ResponseEntity<List<BrandRs>> actual = uat.getAll();

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

        final ResponseEntity<List<BrandRs>> actual = uat.getAll();

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

        final ResponseEntity<List<BrandRs>> actual = uat.getAll();

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
        final BrandAddRq rq = BrandAddRq.builder()
                .name("ENTITY1")
                .build();
        final Brand entity = Brand.builder()
                .id(1)
                .name("ENTITY1")
                .build();
        final BrandRs dto = BrandRs.builder()
                .id(1)
                .name("ENTITY1")
                .build();

        doReturn(entity)
                .when(mapper)
                .toEntity(rq);
        doReturn(entity)
                .when(service)
                .save(entity);
        doReturn(dto)
                .when(mapper)
                .toRs(entity);

        final ResponseEntity<BrandRs> actual = uat.add(rq);

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
        final BrandAddRq rq = BrandAddRq.builder()
                .name("ENTITY1")
                .build();
        final Brand entity = Brand.builder()
                .name("ENTITY1")
                .build();

        doReturn(entity)
                .when(mapper)
                .toEntity(rq);
        doThrow(InputMismatchException.class)
                .when(service)
                .save(entity);

        final Throwable thrown = catchThrowable(() -> uat.add(rq));

        verifyNoMoreInteractions(service,
                mapper);

        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(InputMismatchException.class);
    }

    @Test
    void test_update_ok() {
        final BrandUpdateRq rq = BrandUpdateRq.builder()
                .name("ENTITY1")
                .build();
        final Brand entity = Brand.builder()
                .id(1)
                .name("ENTITY1")
                .build();
        final BrandRs dto = BrandRs.builder()
                .id(1)
                .name("ENTITY1")
                .build();

        doReturn(entity)
                .when(mapper)
                .toEntity(rq);
        doReturn(entity)
                .when(service)
                .update(1, entity);
        doReturn(dto)
                .when(mapper)
                .toRs(entity);

        final ResponseEntity<BrandRs> actual = uat.update(1, rq);

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
        final BrandUpdateRq rq = BrandUpdateRq.builder()
                .name("ENTITY1")
                .build();
        final Brand entity = Brand.builder()
                .name("ENTITY1")
                .build();

        doReturn(entity)
                .when(mapper)
                .toEntity(rq);
        doThrow(EntityNotFoundException.class)
                .when(service)
                .update(1, entity);

        final Throwable thrown = catchThrowable(() -> uat.update(1, rq));

        verifyNoMoreInteractions(service,
                mapper);

        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void test_update_anyException() {
        final BrandUpdateRq rq = BrandUpdateRq.builder()
                .name("ENTITY1")
                .build();
        final Brand entity = Brand.builder()
                .name("ENTITY1")
                .build();

        doReturn(entity)
                .when(mapper)
                .toEntity(rq);
        doThrow(InputMismatchException.class)
                .when(service)
                .update(1, entity);

        final Throwable thrown = catchThrowable(() -> uat.update(1, rq));

        verifyNoMoreInteractions(service,
                mapper);

        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(InputMismatchException.class);
    }

    @Test
    void test_delete_ok() {
        doNothing()
                .when(service)
                .delete(1);

        final ResponseEntity<Void> actual = uat.delete(1);

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
                .delete(1);

        final Throwable thrown = catchThrowable(() -> uat.delete(1));

        verifyNoMoreInteractions(service);
        verifyNoInteractions(mapper);

        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(InputMismatchException.class);
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

        final Throwable thrown = catchThrowable(() -> uat.deleteAll());

        verifyNoMoreInteractions(service);
        verifyNoInteractions(mapper);

        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(InputMismatchException.class);
    }
}