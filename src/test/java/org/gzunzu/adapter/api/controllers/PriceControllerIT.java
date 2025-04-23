package org.gzunzu.adapter.api.controllers;

import org.gzunzu.adapter.api.dto.request.PricePriorityRq;
import org.gzunzu.adapter.api.dto.response.PricePriorityRs;
import org.gzunzu.adapter.api.exception.HttpError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PriceControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void test_pricePriority_20200614T1000_standardRate1() {
        final PricePriorityRq rq = new PricePriorityRq(1, 35455L, LocalDateTime.of(2020, 6, 14, 10, 0));

        final ResponseEntity<PricePriorityRs> response = restTemplate.postForEntity("/price/priority", rq, PricePriorityRs.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        final PricePriorityRs pricePriority = response.getBody();
        assertEquals(1, pricePriority.getBrandId());
        assertEquals(35455L, pricePriority.getProductId());
        assertEquals(1L, pricePriority.getRateId());
        assertEquals(LocalDateTime.of(2020, 6, 14, 0, 0), pricePriority.getStartDate());
        assertEquals(LocalDateTime.of(2020, 12, 31, 23, 59, 59), pricePriority.getEndDate());
        assertEquals(35.5f, pricePriority.getAmount());
        assertEquals("EUR", pricePriority.getCurrencyName());
    }

    @Test
    void test_pricePriority_20200614_1600_standardRate2() {
        final PricePriorityRq rq = new PricePriorityRq(1, 35455L, LocalDateTime.of(2020, 6, 14, 16, 0));

        final ResponseEntity<PricePriorityRs> response = restTemplate.postForEntity("/price/priority", rq, PricePriorityRs.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        final PricePriorityRs pricePriority = response.getBody();
        assertEquals(1, pricePriority.getBrandId());
        assertEquals(35455L, pricePriority.getProductId());
        assertEquals(2L, pricePriority.getRateId());
        assertEquals(LocalDateTime.of(2020, 6, 14, 15, 0), pricePriority.getStartDate());
        assertEquals(LocalDateTime.of(2020, 6, 14, 18, 30), pricePriority.getEndDate());
        assertEquals(25.45f, pricePriority.getAmount());
        assertEquals("EUR", pricePriority.getCurrencyName());
    }

    @Test
    void test_pricePriority_20200614_2100_standardRate1() {
        final PricePriorityRq rq = new PricePriorityRq(1, 35455L, LocalDateTime.of(2020, 6, 14, 21, 0));

        final ResponseEntity<PricePriorityRs> response = restTemplate.postForEntity("/price/priority", rq, PricePriorityRs.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        final PricePriorityRs pricePriority = response.getBody();
        assertEquals(1, pricePriority.getBrandId());
        assertEquals(35455L, pricePriority.getProductId());
        assertEquals(1L, pricePriority.getRateId());
        assertEquals(LocalDateTime.of(2020, 6, 14, 0, 0), pricePriority.getStartDate());
        assertEquals(LocalDateTime.of(2020, 12, 31, 23, 59, 59), pricePriority.getEndDate());
        assertEquals(35.5f, pricePriority.getAmount());
        assertEquals("EUR", pricePriority.getCurrencyName());
    }

    @Test
    void test_pricePriority_20200615_1000_standardRate3() {
        final PricePriorityRq rq = new PricePriorityRq(1, 35455L, LocalDateTime.of(2020, 6, 15, 10, 0));

        final ResponseEntity<PricePriorityRs> response = restTemplate.postForEntity("/price/priority", rq, PricePriorityRs.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        final PricePriorityRs pricePriority = response.getBody();
        assertEquals(1, pricePriority.getBrandId());
        assertEquals(35455L, pricePriority.getProductId());
        assertEquals(3L, pricePriority.getRateId());
        assertEquals(LocalDateTime.of(2020, 6, 15, 0, 0), pricePriority.getStartDate());
        assertEquals(LocalDateTime.of(2020, 6, 15, 11, 0), pricePriority.getEndDate());
        assertEquals(30.5f, pricePriority.getAmount());
        assertEquals("EUR", pricePriority.getCurrencyName());
    }

    @Test
    void test_pricePriority_20200615_2100_standardRate4() {
        final PricePriorityRq rq = new PricePriorityRq(1, 35455L, LocalDateTime.of(2020, 6, 15, 21, 0));

        final ResponseEntity<PricePriorityRs> response = restTemplate.postForEntity("/price/priority", rq, PricePriorityRs.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        final PricePriorityRs pricePriority = response.getBody();
        assertEquals(1, pricePriority.getBrandId());
        assertEquals(35455L, pricePriority.getProductId());
        assertEquals(4L, pricePriority.getRateId());
        assertEquals(LocalDateTime.of(2020, 6, 15, 16, 0), pricePriority.getStartDate());
        assertEquals(LocalDateTime.of(2020, 12, 31, 23, 59, 59), pricePriority.getEndDate());
        assertEquals(38.95f, pricePriority.getAmount());
        assertEquals("EUR", pricePriority.getCurrencyName());
    }

    @Test
    void test_pricePriority_20250615_2100_noRateFound() {
        final PricePriorityRq rq = new PricePriorityRq(1, 35455L, LocalDateTime.of(2025, 6, 15, 21, 0));

        final ResponseEntity<HttpError> response = restTemplate.postForEntity("/price/priority", rq, HttpError.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        final HttpError httpError = response.getBody();
        assertEquals(404, httpError.httpValue());
        assertNotNull(httpError.timestamp());
        assertEquals("No Price configured in DB with brandId «1», productId «35455» and date «2025-06-15T21:00».", httpError.message());
    }
}