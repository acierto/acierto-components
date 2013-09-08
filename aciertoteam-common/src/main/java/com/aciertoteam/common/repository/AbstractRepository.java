package com.aciertoteam.common.repository;

import com.aciertoteam.common.entity.AbstractEntity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @param <T> Entity
 * @author Bogdan Nechyporenko
 */
public interface AbstractRepository<T> {

    /**
     * Returns all including deleted entries from the database
     * 
     * @return
     */
    List<T> getAll();

    /**
     * Returns entries by restricted bounds, aka pagination approach.
     *
     * @return
     */
    List<T> getAll(int from, int to);

    /**
     * Returns all not deleted entries from the database
     *
     * @return
     */
    List<T> getAllIncludingDeleted();

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
    void saveAll(Collection<? extends T> entities);

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
     * Returns the entry from the specified table by specific field
     *
     * @param fieldName
     * @param value
     * @return
     */
    T findByField(String fieldName, Object value, boolean includingDeleted);

    /**
     * Returns the list of entries from the specified table by specific field
     * @param fieldName
     * @param value
     * @return
     */
    List<T> findListByField(String fieldName, Object value);

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
    T saveOrUpdate(T t);

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
     * Deletes entities from the database by specified list of identifiers
     *
     * @param ids
     */
    void deleteByIds(final List<Long> ids);

    /**
     * Deletes from the table the entry(-ies) which has the specified value by selected field.
     *
     * @param fieldName
     * @param value
     */
    void deleteByFieldName(String fieldName, Object value);

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
