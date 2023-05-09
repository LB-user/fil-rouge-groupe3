package com.gestionSalleCefim.Group3.services;

import com.gestionSalleCefim.Group3.repositories.BaseRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This abstract class serves as a base service for working with entities in a Spring Boot application.
 * It provides common CRUD operations for working with entities, such as getting all entities, getting an
 * entity by ID, creating a new entity, updating an existing entity, and deleting an entity.
 *
 * @param <T> the type of entity this service handles
 */
@Service
public abstract class BaseService<T> {

    /**
     * The repository layer for working with entities. This is autowired by Spring at runtime.
     */
    @Autowired(required = true)
    protected BaseRepository<T, Integer> repository;

    /**
     * Creates a new entity.
     *
     * @param entity the entity to create
     * @return the newly created entity
     */
    @Transactional
    public T save(T entity) throws Exception {
        int entityId = (Integer) entity.getClass().getMethod("getId").invoke(entity);
        Optional<T> originalBook = repository.findById(entityId);
        if (originalBook.isEmpty()){
            return repository.save(entity);
        }
        throw new RuntimeException("Entity with id " + entityId + " already exists");
    }

    /**
     * Returns a list of all entities.
     *
     * @return a list of all entities
     */
    @Transactional
    public List<T> getAll() {
        return repository.findAll();
    }

    /**
     * Returns the entity with the specified ID.
     *
     * @param id the ID of the entity to retrieve
     * @return the entity with the specified ID, or null if no entity with that ID exists
     */
    @Transactional
    public T getById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + id));
    }

    /**
     * Updates an existing entity.
     *
     * @param id the ID of the entity to update
     * @param entity the entity to update
     * @return the updated entity
     */
    @Transactional
    public T update(int id, T entity) {
        T originalEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + id));
        return repository.save(originalEntity);
    }

    /**
     * Deletes the entity with the specified ID.
     *
     * @param id the ID of the entity to delete
     */
    @Transactional
    public void delete(int id) {
        T entityToDelete = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + id));
        repository.delete(entityToDelete);
    }

}
