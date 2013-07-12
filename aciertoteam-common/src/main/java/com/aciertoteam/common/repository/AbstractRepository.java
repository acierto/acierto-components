package com.aciertoteam.common.repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @param <T> Entity
 * @author Bogdan Nechyporenko
 */
public interface AbstractRepository<T> {

    /**
     * Returns all entries from the database
     * 
     * @return
     */
    List<T> getAll();

    /**
     * Returns an entry by identifier
     * 
     * @param id
     * @return
     */
    T get(Long id);

    /**
     * Returns the list of entries by the list of identifiers
     * 
     * @param ids
     * @return
     */
    List<T> getList(List<Long> ids);

    /**
     * Returns the list of entries by the list of identifiers
     * 
     * @param ids
     * @return
     */
    Set<T> getSet(List<Long> ids);

    /**
     * Saves the list of entities
     * 
     * @param entities
     */
    void saveAll(Collection<T> entities);

    /**
     * Saves an entry
     * 
     * @param entity
     * @return
     */
    T save(T entity);

    /**
     * Returns the entry from the specified table by specific field
     * 
     * @param fieldName
     * @param value
     * @return
     */
    T findByField(String fieldName, Object value);

    /**
     * Returns the entries from the specified table by specific field
     * 
     * @param fieldName
     * @param value
     * @return
     */
    Collection<T> findCollectionByField(String fieldName, Object value);

    /**
     * Saves or update transferred entity
     * 
     * @param t
     */
    void saveOrUpdate(T t);

    /**
     * Close end period of entity by id.
     * 
     * @param id
     */
    void markAsDeleted(Long id);

    /**
     * Close end period for all passed entities.
     * @param entities
     */
    void markAsDeleted(List<T> entities);

    /**
     * Close end period of entity.
     * 
     * @param entity
     */
    void markAsDeleted(T entity);

    /**
     * Deletes entity from the database by identifier
     * 
     * @param id
     */
    void delete(Long id);

    /**
     * Deletes the list of entities
     * 
     * @param entities
     */
    void delete(List<T> entities);

    /**
     * Deletes the entity
     * 
     * @param entity
     */
    void delete(T entity);

    /**
     * Delete all entities from the specified table
     */
    void deleteAll();

    /**
     * Returns the number of entities in the table
     * @return
     */
    long count();

    /**
     * Indicator whether any entities contain table.
     * @return
     */
    boolean isEmpty();
}
