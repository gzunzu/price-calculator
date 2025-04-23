package org.gzunzu.adapter.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.gzunzu.adapter.api.dto.request.PriceAddRq;
import org.gzunzu.adapter.api.dto.request.PricePriorityRq;
import org.gzunzu.adapter.api.dto.request.PriceUpdateRq;
import org.gzunzu.adapter.api.dto.response.PricePriorityRs;
import org.gzunzu.adapter.api.dto.response.PriceRs;
import org.gzunzu.adapter.api.mapper.PriceMapper;
import org.gzunzu.domain.model.Price;
import org.gzunzu.domain.ports.PriceService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/price")
@Slf4j
public class PriceController extends BasicEntityController<Price, Long, PriceAddRq, PriceUpdateRq, PriceRs> {

    private final PriceService priceService;
    private final PriceMapper priceMapper;

    public PriceController(final PriceService service,
                           final PriceMapper mapper) {
        super(service, mapper);
        this.priceService = service;
        this.priceMapper = mapper;
    }

    @Operation(summary = "Gets the price for a specific product and date",
            description = "Searches for the price assigned to an specific product, brand and date, considering the highest priority in DB.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Entity found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404",
                    description = "Entity not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500",
                    description = "Error searching entity",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @PostMapping(value = "/priority", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PricePriorityRs> getPriority(@RequestBody @Valid final PricePriorityRq rq) {
        try {
            final Price priorityPrice = priceService.getHighestPriorityByProductIdAndBrandIdAndStartDateNotAfterAndEndDateNotBefore(rq.getBrandId(),
                    rq.getProductId(),
                    rq.getPurchaseDatetime());
            return ResponseEntity.ok(priceMapper.toPricePriorityRs(priorityPrice));
        } catch (final Exception exception) {
            log.warn(exception.getMessage(), exception);
            throw exception;
        }
    }
}
