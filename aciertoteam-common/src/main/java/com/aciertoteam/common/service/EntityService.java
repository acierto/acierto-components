package com.aciertoteam.common.service;

import com.aciertoteam.common.entity.AbstractEntity;
import com.aciertoteam.common.interfaces.IAbstractEntity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author Bogdan Nechyporenko
 */
public interface EntityService {

    /**
     * Returns the result if has any entity in the database of specified class.
     *
     * @param clazz
     * @return
     */
    boolean isEmpty(Class clazz);

    /**
     * Returns the entity of specified class by ID.
     *
     * @param clazz
     * @param id
     * @param <T>
     * @return
     */
    <T extends IAbstractEntity> T findById(Class<T> clazz, Long id);

    /**
     * Returns all entities from the specified database
     *
     * @param clazz
     * @param <T>
     * @return
     */
    <T extends IAbstractEntity> List<T> findAll(Class<T> clazz);

    /**
     * Returns all entities from the specified database
     *
     * @param clazz
     * @param <T>
     * @return
     */
    <T extends IAbstractEntity> List<T> findAllOrderedByTimestamp(Class<T> clazz, boolean asc);

    /**
     * Returns all entities from the specified database
     *
     * @param clazz
     * @param <T>
     * @return
     */
    <T extends IAbstractEntity> Set<T> findAllAsSet(Class<T> clazz);

    /**
     * Saves or updates entity depends on id
     *
     * @param t
     * @param <T>
     */
    <T extends IAbstractEntity> void saveOrUpdate(T... t);

    /**
     * Saves or updates entity depends on id
     *
     * @param collection
     * @param <T>
     */
    <T extends IAbstractEntity> void saveOrUpdate(Collection<T> collection);

    /**
     * Close validThru period for transferred entity
     *
     * @param t
     * @param <T>
     */
    <T extends IAbstractEntity> void markAsDeleted(T t);

    /**
     * Returns the entry from the specified table by specific field
     *
     * @param fieldName
     * @param value
     * @return
     */
    <T> T findByField(Class clazz, String fieldName, Object value);

    /**
     * Physically deletes records from the database
     * @param entities
     */
    void delete(List<AbstractEntity> entities);

    /**
     * Saves the list of entities
     *
     * @param entities
     */
    void saveAll(Collection<AbstractEntity> entities);
}
