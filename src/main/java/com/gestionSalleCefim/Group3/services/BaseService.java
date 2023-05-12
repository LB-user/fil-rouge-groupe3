package com.gestionSalleCefim.Group3.services;

import com.gestionSalleCefim.Group3.exceptions.EntityAlreadyExistsException;
import com.gestionSalleCefim.Group3.exceptions.InvalidEntityException;
import com.gestionSalleCefim.Group3.repositories.BaseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * This abstract class serves as a base service for working with entities in a Spring Boot application.
 * It provides common CRUD operations for working with entities, such as getting all entities, getting an
 * entity by ID, creating a new entity, updating an existing entity, and deleting an entity.
 *
 * @param <T> the type of entity this service handles
 */
@Service
public abstract class BaseService<T, R extends BaseRepository<T, Integer>> {
    private Class<T> type;

    public Class<T> getType() {
        return type;
    }

    @PersistenceContext
    protected EntityManager entityManager;
    /**
     * The repository layer for working with entities. This is autowired by Spring at runtime.
     */
    @Autowired(required = true)
    protected R repository;

    /**
     * Creates a new entity.
     *
     * @param entity the entity to create
     * @return the newly created entity
     * @throws EntityAlreadyExistsException if an entity with the same ID already exists in the database
     * @throws InvalidEntityException if the entity does not have a valid ID field
     */
    @Transactional
    public T save(T entity) throws EntityAlreadyExistsException, InvalidEntityException {
        try {
            Integer entityId = (Integer) entity.getClass().getMethod("getId").invoke(entity);
            if (entityId == null || !repository.existsById(entityId)) {
                return repository.save(entity);
            }
            throw new EntityAlreadyExistsException("Entity with id " + entityId + " already exists");
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new InvalidEntityException("Failed to get ID field from entity", e);
        }
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
        getAll();
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + id));
    }

    /**
     * Returns a list of all entities from filter
     *
     * @param entity the entity with filters
     */
    public List<T> getAllByFilter(T entity) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        return repository.findAll(Example.of(entity, exampleMatcher));
    }

    /**
     * Updates an existing entity.
     *
     * @param entity the entity to update
     * @return the updated entity
     */
    @Transactional
    public T update(T entity) throws InvalidEntityException {
        try {
            Integer entityId = (Integer) entity.getClass().getMethod("getId").invoke(entity);
            T entityToFind = getById(entityId);
            if (entityToFind != null){
                return repository.save(entity);
            }
            throw new EntityNotFoundException("Entity not found with id: " + entityId);
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            throw new InvalidEntityException("Failed to get ID field from entity", e);
        }
    }

    /**
     * Deletes the entity with the specified ID.
     *
     * @param id the ID of the entity to delete
     */
    @Transactional
    public void delete(int id) {
        T entityToFind = getById(id);
        if (entityToFind != null){
            repository.deleteById(id);
        }
    }
}