package com.gestionSalleCefim.Group3.repositories;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

/**
 * This is a base repository interface that extends the Spring Data JPA's JpaRepository interface.
 * It provides common CRUD operations for working with entities in a Spring Boot application.
 *
 * @param <T> the type of entity this repository handles
 * @param <ID> the type of ID of the entity
 */
@Lazy
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {}