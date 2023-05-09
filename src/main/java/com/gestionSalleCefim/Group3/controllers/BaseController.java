package com.gestionSalleCefim.Group3.controllers;

import com.gestionSalleCefim.Group3.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This abstract class serves as a base controller for working with entities in a Spring Boot application.
 * It provides common CRUD operations for working with entities, such as getting all entities, getting an
 * entity by ID, creating a new entity, updating an existing entity, and deleting an entity.
 *
 * @param <T> the type of entity this controller handles
 */
public abstract class BaseController<T> {
    protected static final String NOT_AVAILABLE_CONTENT = "Désolé, ce contenu n'est pas disponible pour le moment.";
    /**
     * The service layer for working with entities. This is autowired by Spring at runtime.
     */
    @Autowired
    private BaseService<T> baseService;

    /**
     * Create a new entity.
     *
     * @param entity the entity to create
     * @return a ResponseEntity containing the newly created entity
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody T entity) {
        try {
            T createdEntity = baseService.save(entity);
            return ResponseEntity.ok(createdEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Returns a list of all entities.
     *
     * @return a ResponseEntity containing a list of all entities
     */
    @GetMapping("/all")
    public ResponseEntity<List<?>> getAll() {
        try {
            List<T> entities = baseService.getAll();
            return ResponseEntity.ok(entities);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Returns the entity with the specified ID.
     *
     * @param id the ID of the entity to retrieve
     * @return a ResponseEntity containing the entity with the specified ID, or null if no entity with that ID exists
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            T entity = baseService.getById(id);
            if (entity == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(entity);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Updates an existing entity.
     *
     * @param entity the entity to update
     * @return a ResponseEntity containing the updated entity
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody T entity) {
        try {
            T updatedEntity = baseService.update(id, entity);
            return ResponseEntity.ok(updatedEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Deletes the entity with the specified ID.
     *
     * @param id the ID of the entity to delete
     * @return a ResponseEntity with no response body
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            baseService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
