package com.aciertoteam.common.repository;

import com.aciertoteam.common.entity.AbstractEntity;
import com.aciertoteam.common.interfaces.IAbstractEntity;

import java.util.List;

/**
 * @author Bogdan Nechyporenko
 */
public interface EntityRepository extends AbstractRepository<AbstractEntity> {

    /**
     * Returns the number of entities in the specified database.
     *
     * @param clazz
     * @return
     */
    long count(Class clazz);

    /**
     * Returns the entry from the specified table by specific field
     *
     * @param fieldName
     * @param value
     * @return
     */
    <T> T findByField(Class clazz, String fieldName, Object value);

    /**
     * Returns the entity of specified class by ID.
     *
     * @param clazz
     * @param id
     * @param <T>
     * @return
     */
    <T> T findById(Class clazz, Long id);

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
}
