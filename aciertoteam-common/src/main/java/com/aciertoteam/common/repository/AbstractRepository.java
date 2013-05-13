package com.aciertoteam.common.repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
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
     * Deletes entity from the database by identifier
     *
     * @param Id
     */
    void delete(Long Id);

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
