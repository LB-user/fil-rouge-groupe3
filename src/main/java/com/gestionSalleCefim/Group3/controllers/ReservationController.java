package com.gestionSalleCefim.Group3.controllers;

import com.gestionSalleCefim.Group3.entities.Reservation;
import com.gestionSalleCefim.Group3.exceptions.EntityAlreadyExistsException;
import com.gestionSalleCefim.Group3.exceptions.ExceptionMessage;
import com.gestionSalleCefim.Group3.exceptions.InvalidEntityException;
import com.gestionSalleCefim.Group3.repositories.ReservationRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@RestController
@Validated
@RequestMapping("/api/reservation")
public class ReservationController extends BaseController<Reservation, ReservationRepository>{

    /**
     * Create a new entity.
     *
     * @param entity the entity to create
     * @return a ResponseEntity containing the newly created entity
     */
    @Override
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Reservation entity) throws EntityAlreadyExistsException, InvalidEntityException {
        Reservation createdEntity = baseService.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEntity);
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PutMapping
    @Override
    public ResponseEntity<?> update(@RequestBody Reservation entity) throws InvalidEntityException {
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Override
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        baseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
