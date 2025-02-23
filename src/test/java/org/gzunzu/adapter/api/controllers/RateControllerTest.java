package org.gzunzu.adapter.api.controllers;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.gzunzu.adapter.api.controller.RateController;
import org.gzunzu.adapter.api.dto.request.RateAddRq;
import org.gzunzu.adapter.api.dto.request.RateUpdateRq;
import org.gzunzu.adapter.api.dto.response.RateRs;
import org.gzunzu.adapter.api.mapper.RateMapper;
import org.gzunzu.domain.model.Rate;
import org.gzunzu.domain.ports.RateService;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
@Slf4j
class RateControllerTest {

    private static AutoCloseable autoCloseable;
    @InjectMocks
    private RateController uat;
    @Mock
    private RateService service;
    @Mock
    private RateMapper mapper;

    @BeforeAll
    static void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(RateControllerTest.class);
    }

    @AfterAll
    static void tearDown() {
        try {
            autoCloseable.close();
        } catch (final Exception exception) {
            log.warn("Error trying to close {} class mocks.", RateControllerTest.class, exception);
        }
    }

    @Test
    void test_get_entityFound() {
        final Rate entity = Rate.builder()
                .id(1L)
                .name("ENTITY1")
                .build();
        final RateRs dto = RateRs.builder()
                .id(1L)
                .name("ENTITY1")
                .build();

        doReturn(entity)
                .when(service)
                .getById(1L);
        doReturn(dto)
                .when(mapper)
                .toRs(entity);

        final ResponseEntity<RateRs> actual = uat.get(1L);

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

        final ResponseEntity<RateRs> actual = uat.get(1L);

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

        final ResponseEntity<RateRs> actual = uat.get(1L);

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
        final Rate entity1 = Rate.builder()
                .id(1L)
                .name("ENTITY1")
                .build();
        final Rate entity2 = Rate.builder()
                .id(2L)
                .name("ENTITY2")
                .build();
        final RateRs dto1 = RateRs.builder()
                .id(1L)
                .name("ENTITY1")
                .build();
        final RateRs dto2 = RateRs.builder()
                .id(2L)
                .name("ENTITY2")
                .build();

        doReturn(List.of(entity1, entity2))
                .when(service)
                .getAll();
        doReturn(List.of(dto1, dto2))
                .when(mapper)
                .toRs(List.of(entity1, entity2));

        final ResponseEntity<List<RateRs>> actual = uat.getAll();

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

        final ResponseEntity<List<RateRs>> actual = uat.getAll();

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

        final ResponseEntity<List<RateRs>> actual = uat.getAll();

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
        final RateAddRq rq = RateAddRq.builder()
                .name("ENTITY1")
                .build();
        final Rate entity = Rate.builder()
                .id(1L)
                .name("ENTITY1")
                .build();
        final RateRs dto = RateRs.builder()
                .id(1L)
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

        final ResponseEntity<RateRs> actual = uat.add(rq);

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
        final RateAddRq rq = RateAddRq.builder()
                .name("ENTITY1")
                .build();
        final Rate entity = Rate.builder()
                .name("ENTITY1")
                .build();

        doReturn(entity)
                .when(mapper)
                .toEntity(rq);
        doThrow(InputMismatchException.class)
                .when(service)
                .save(entity);

        final ResponseEntity<RateRs> actual = uat.add(rq);

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
        final RateUpdateRq rq = RateUpdateRq.builder()
                .name("ENTITY1")
                .build();
        final Rate entity = Rate.builder()
                .id(1L)
                .name("ENTITY1")
                .build();
        final RateRs dto = RateRs.builder()
                .id(1L)
                .name("ENTITY1")
                .build();

        doReturn(entity)
                .when(mapper)
                .toEntity(rq);
        doReturn(entity)
                .when(service)
                .update(2L, entity);
        doReturn(dto)
                .when(mapper)
                .toRs(entity);

        final ResponseEntity<RateRs> actual = uat.update(2L, rq);

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
        final RateUpdateRq rq = RateUpdateRq.builder()
                .name("ENTITY1")
                .build();
        final Rate entity = Rate.builder()
                .name("ENTITY1")
                .build();

        doReturn(entity)
                .when(mapper)
                .toEntity(rq);
        doThrow(EntityNotFoundException.class)
                .when(service)
                .update(2L, entity);

        final ResponseEntity<RateRs> actual = uat.update(2L, rq);

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
        final RateUpdateRq rq = RateUpdateRq.builder()
                .name("ENTITY1")
                .build();
        final Rate entity = Rate.builder()
                .name("ENTITY1")
                .build();

        doReturn(entity)
                .when(mapper)
                .toEntity(rq);
        doThrow(InputMismatchException.class)
                .when(service)
                .update(2L, entity);

        final ResponseEntity<RateRs> actual = uat.update(2L, rq);

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
}