package org.gzunzu.adapter.api.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@Slf4j
class GlobalExceptionHandlerTest {

    private static AutoCloseable autoCloseable;
    @InjectMocks
    private GlobalExceptionHandler uat;

    @BeforeAll
    static void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(GlobalExceptionHandlerTest.class);
    }

    @AfterAll
    static void tearDown() {
        try {
            autoCloseable.close();
        } catch (final Exception exception) {
            log.warn("Error trying to close {} class mocks.", GlobalExceptionHandlerTest.class, exception);
        }
    }

    @Test
    void test_handleVariousErrors_entityNotFoundException() {
        final ResponseEntity<HttpError> actual = uat.handleVariousErrors(new EntityNotFoundException("Test"));

        assertThat(actual).isNotNull();
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(actual.getBody())
                .isNotNull()
                .isInstanceOf(HttpError.class);
        assertThat(actual.getBody().message()).isEqualTo("Test");
    }

    @Test
    void test_handleVariousErrors_badRequestException() {
        final ResponseEntity<HttpError> actual = uat.handleVariousErrors(new BadRequestException("Test"));

        assertThat(actual).isNotNull();
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(actual.getBody())
                .isNotNull()
                .isInstanceOf(HttpError.class);
        assertThat(actual.getBody().message()).isEqualTo("Test");
    }

    @Test
    void test_handleVariousErrors_internalServerError() {
        final ResponseEntity<HttpError> actual = uat.handleVariousErrors(new ArrayIndexOutOfBoundsException("Test"));

        assertThat(actual).isNotNull();
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(actual.getBody())
                .isNotNull()
                .isInstanceOf(HttpError.class);
        assertThat(actual.getBody().message()).isEqualTo("Test");
    }
}