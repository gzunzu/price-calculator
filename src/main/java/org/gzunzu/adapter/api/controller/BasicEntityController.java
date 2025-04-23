package org.gzunzu.adapter.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.gzunzu.adapter.api.dto.request.BasicCrudAddRq;
import org.gzunzu.adapter.api.dto.request.BasicCrudUpdateRq;
import org.gzunzu.adapter.api.dto.response.BasicCrudRs;
import org.gzunzu.adapter.api.dto.response.BrandRs;
import org.gzunzu.adapter.api.mapper.BasicEntityMapper;
import org.gzunzu.domain.model.BasicEntity;
import org.gzunzu.domain.ports.BasicEntityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public abstract class BasicEntityController<E extends BasicEntity<E, K>,
        K,
        A extends BasicCrudAddRq,
        U extends BasicCrudUpdateRq,
        R extends BasicCrudRs> {

    protected final BasicEntityService<E, K> service;
    protected final BasicEntityMapper<E, K, A, U, R> mapper;

    @Operation(summary = "Gets the entity by ID",
            description = "Searches for the entity  in DB whose ID is equal to the value given by parameter.")
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
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<R> get(@PathVariable(name = "id") final K id) {
        try {
            return ResponseEntity.ok(mapper.toRs(service.getById(id)));
        } catch (final Exception exception) {
            log.warn(exception.getMessage(), exception);
            throw exception;
        }
    }

    @Operation(summary = "Gets all entities",
            description = "Searches for all entities in DB.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Entities found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "204",
                    description = "No entities found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500",
                    description = "Error searching entities",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<R>> getAll() {
        try {
            List<R> result = mapper.toRs(service.getAll());
            return CollectionUtils.isEmpty(result) ? ResponseEntity.noContent().build() : ResponseEntity.ok(result);
        } catch (final Exception exception) {
            log.warn(exception.getMessage(), exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Inserts a new entity",
            description = "Inserts a new entity in DB with the given name parameter.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Entity created",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500",
                    description = "Error creating entity",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<R> add(@RequestBody @Valid final A rq) {
        try {
            return new ResponseEntity<>(mapper.toRs(service.save(mapper.toEntity(rq))), HttpStatus.CREATED);
        } catch (final Exception exception) {
            log.warn(exception.getMessage(), exception);
            throw exception;
        }
    }

    @Operation(summary = "Updates an existing entity",
            description = "Updates the name of an existing entity in DB with the given name parameter.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Entity updated",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BrandRs.class))),
            @ApiResponse(responseCode = "404",
                    description = "No entity found with provided ID",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500",
                    description = "Error updating entity",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @PatchMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<R> update(@PathVariable(name = "id") final K id,
                                    @RequestBody @Valid final U rq) {
        try {
            return ResponseEntity.ok(mapper.toRs(service.update(id, mapper.toEntity(rq))));
        } catch (final Exception exception) {
            log.warn(exception.getMessage(), exception);
            throw exception;
        }
    }

    @Operation(summary = "Deletes an existing entity",
            description = "Deletes an existing entity in DB with the given id parameter.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Entity deleted"),
            @ApiResponse(responseCode = "500",
                    description = "Error deleting entity",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") final K id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (final Exception exception) {
            log.warn(exception.getMessage(), exception);
            throw exception;
        }
    }

    @Operation(summary = "Deletes all existing entities",
            description = "Deletes all existing entities in DB.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "All entities deleted"),
            @ApiResponse(responseCode = "500",
                    description = "Error deleting entities",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @DeleteMapping(value = "/all")
    public ResponseEntity<Void> deleteAll() {
        try {
            service.deleteAll();
            return ResponseEntity.noContent().build();
        } catch (final Exception exception) {
            log.warn(exception.getMessage(), exception);
            throw exception;
        }
    }
}
