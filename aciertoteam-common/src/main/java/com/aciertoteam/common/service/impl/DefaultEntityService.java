package com.aciertoteam.common.service.impl;

import com.aciertoteam.common.entity.AbstractEntity;
import com.aciertoteam.common.interfaces.IAbstractEntity;
import com.aciertoteam.common.model.DeletionPropagated;
import com.aciertoteam.common.repository.EntityRepository;
import com.aciertoteam.common.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Bogdan Nechyporenko
 */
@Service(value = "entityService")
@Transactional
public class DefaultEntityService implements EntityService {

    @Autowired
    private EntityRepository entityRepository;

    @Override
    public boolean isEmpty(Class clazz) {
        return entityRepository.count(clazz) == 0;
    }

    @Override
    public <T extends IAbstractEntity> T findById(Class<T> clazz, Long id) {
        return entityRepository.findById(clazz, id);
    }

    @Override
    public <T extends IAbstractEntity> List<T> findByIds(Class<T> clazz, List<Long> ids) {
        return entityRepository.findByIds(clazz, ids);
    }

    @Override
    public <T extends IAbstractEntity> List<T> findAll(Class<T> clazz) {
        return entityRepository.findAll(clazz);
    }

    @Override
    public <T extends IAbstractEntity> List<T> findAll(Class<T> clazz, Pageable pageable) {
        return entityRepository.findAll(clazz, pageable);
    }

    @Override
    public <T extends IAbstractEntity> List<T> findAllOrderedByTimestamp(Class<T> clazz, boolean asc) {
        return entityRepository.findAllOrderedByTimestamp(clazz, asc);
    }

    @Override
    public <T extends IAbstractEntity> Set<T> findAllAsSet(Class<T> clazz) {
        return new HashSet<T>(findAll(clazz));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IAbstractEntity> T saveOrUpdate(T entity) {
        return (T) entityRepository.saveOrUpdate((AbstractEntity) entity);
    }

    @Override
    public <T extends IAbstractEntity> void saveOrUpdate(Collection<T> collection) {
        for (T t : collection) {
            entityRepository.saveOrUpdate((AbstractEntity) t);
        }
    }

    @Override
    public <T extends IAbstractEntity> void markAsDeleted(T entity) {
        entity.closeEndPeriod();
        entityRepository.saveOrUpdate((AbstractEntity) entity);
        propagateDelete(entity);
    }

    @Override
    public <T extends IAbstractEntity> void markAsDeleted(List<T> entities) {
        for (T entity : entities) {
            entity.closeEndPeriod();
            entityRepository.saveOrUpdate((AbstractEntity) entity);
            propagateDelete(entity);
        }
    }

    @Override
    public <T extends IAbstractEntity> void markAsDeletedById(Class<T> clazz, Long id) {
        IAbstractEntity entity = findById(clazz, id);
        if (entity != null) {
            markAsDeleted(entity);
        }
    }

    private <T extends IAbstractEntity> void propagateDelete(T entity) {
        if (entity instanceof DeletionPropagated) {
            List<AbstractEntity> entitiesForDeletionPropagation = ((DeletionPropagated) entity)
                    .getEntitiesForDeletionPropagation();
            for (AbstractEntity entityToDelete : entitiesForDeletionPropagation) {
                if (entityToDelete != null) {
                    markAsDeleted(entityToDelete);
                }
            }
        }
    }

    @Override
    public <T> T findByField(Class clazz, String fieldName, Object value) {
        return entityRepository.findByField(clazz, fieldName, value);
    }

    @Override
    public void delete(List<AbstractEntity> entities) {
        entityRepository.delete(entities);
    }

    @Override
    public void delete(AbstractEntity entity) {
        if (entity != null) {
            entityRepository.delete(entity);
        }
    }

    @Override
    public void saveAll(Collection<? extends AbstractEntity> entities) {
        entityRepository.saveAll(entities);
    }

    @Override
    public long count(Class clazz) {
        return entityRepository.count(clazz);
    }
}
