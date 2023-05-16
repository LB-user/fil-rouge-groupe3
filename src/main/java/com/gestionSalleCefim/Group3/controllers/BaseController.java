package com.gestionSalleCefim.Group3.controllers;

import com.gestionSalleCefim.Group3.exceptions.EntityAlreadyExistsException;
import com.gestionSalleCefim.Group3.exceptions.ExceptionMessage;
import com.gestionSalleCefim.Group3.exceptions.InvalidEntityException;
import com.gestionSalleCefim.Group3.repositories.BaseRepository;
import com.gestionSalleCefim.Group3.services.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This abstract class serves as a base controller for working with entities in a Spring Boot application.
 * It provides common CRUD operations for working with entities, such as getting all entities, getting an
 * entity by ID, creating a new entity, updating an existing entity, and deleting an entity.
 *
 * @param <T> the type of entity this controller handles
 */
@SecurityRequirement(name = "Authorization")
public abstract class BaseController<T, R extends BaseRepository<T, Integer>> {
    /**
     * The service layer for working with entities. This is autowired by Spring at runtime.
     */
    @Autowired
    protected BaseService<T, R> baseService;

    /**
     * Create a new entity.
     *
     * @param entity the entity to create
     * @return a ResponseEntity containing the newly created entity
     */
    @Operation(summary = "Create a new entity", description = "Returns the created entity.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Entity created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "400", description = "Invalid filter criteria provided.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionMessage.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionMessage.class))),
            @ApiResponse(responseCode = "404", description = "No entities match the provided filter criteria.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionMessage.class))),
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody T entity) throws EntityAlreadyExistsException, InvalidEntityException {
        T createdEntity = (T) baseService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEntity);
    }

    /**
     * Returns a list of all entities.
     *
     * @return a ResponseEntity containing a list of all entities
     */
    @Operation(summary = "Get all entities", description = "Returns a list of the entity.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entities found", content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Object.class))
                            )
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Invalid filter criteria provided.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionMessage.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionMessage.class))),
            @ApiResponse(responseCode = "404", description = "No entities match the provided filter criteria.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionMessage.class))),
    })
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<?>> getAll() {
        return ResponseEntity.ok(baseService.getAll());
    }

    /**
     * Returns the entity with the specified ID.
     *
     * @param id the ID of the entity to retrieve
     * @return a ResponseEntity containing the entity with the specified ID, or null if no entity with that ID exists
     */
    @Operation(summary = "Get entity by ID", description = "Returns the entity from the the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entity found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "400", description = "Invalid filter criteria provided.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionMessage.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionMessage.class))),
            @ApiResponse(responseCode = "404", description = "No entities match the provided filter criteria.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionMessage.class))),
    })
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@Parameter(description = "ID of the entity to retrieve") @PathVariable Integer id) {
        return ResponseEntity.ok(baseService.getById(id));
    }

    /**
     * Returns the entity with the specified ID.
     *
     * @return a ResponseEntity containing a list of all entities with filter
     */
    @Operation(summary = "Get entities by filter", description = "Returns a list of entities that match the provided filters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entities found", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Object.class))
                    )
            }),
            @ApiResponse(responseCode = "400", description = "Invalid filter criteria provided.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionMessage.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionMessage.class))),
            @ApiResponse(responseCode = "404", description = "No entities match the provided filter criteria.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionMessage.class))),
    })
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/filter")
    public ResponseEntity<?> getAllByFilter(@RequestBody T entity) {
        return ResponseEntity.ok(baseService.getAllByFilter(entity));
    }

    /**
     * Updates an existing entity.
     *
     * @param entity the entity to update
     * @return a ResponseEntity containing the updated entity
     */
    @Operation(summary = "Updates an existing entity", description = "Returns a modified entity.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The entity has been updated",
                    content = @Content(schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "400", description = "The request is invalid",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionMessage.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionMessage.class))),
            @ApiResponse(responseCode = "404", description = "No entities match the provided filter criteria.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionMessage.class)))
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody T entity) throws InvalidEntityException {
        return ResponseEntity.ok(baseService.update(entity));
    }

    /**
     * Deletes the entity with the specified ID.
     *
     * @param id the ID of the entity to delete
     * @return a ResponseEntity with no response body
     */
    @Operation(summary = "Deletes the entity with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "The entity has been deleted",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "The request is invalid",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionMessage.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionMessage.class))),
            @ApiResponse(responseCode = "404", description = "The entity with the specified ID does not exist",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionMessage.class)))
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        baseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
