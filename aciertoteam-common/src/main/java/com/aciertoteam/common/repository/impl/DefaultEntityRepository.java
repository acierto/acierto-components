package com.aciertoteam.common.repository.impl;

import com.aciertoteam.common.entity.AbstractEntity;
import com.aciertoteam.common.interfaces.IAbstractEntity;
import com.aciertoteam.common.repository.EntityRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Bogdan Nechyporenko
 */
@SuppressWarnings("unchecked")
@Repository(value = "entityRepository")
@Transactional
public class DefaultEntityRepository extends DefaultAbstractRepository<AbstractEntity> implements EntityRepository {

    public long count(Class clazz) {
        return Long.valueOf(String.valueOf(getSession().createCriteria(clazz).setProjection(Projections.rowCount())
                .uniqueResult()));
    }

    @Override
    public <T> T findByField(Class clazz, String fieldName, Object value) {
        return (T) getSession().createCriteria(clazz).add(Restrictions.eq(fieldName, value)).uniqueResult();
    }

    @Override
    public <T> List<T> findListByField(Class clazz, String fieldName, Object value) {
        Criteria criteria = getSession().createCriteria(clazz);
        return criteria.add(Restrictions.like(fieldName, value)).list();
    }

    @Override
    public <T> T findById(Class<T> clazz, Long id) {
        return (T) getSession().createCriteria(clazz).add(Restrictions.eq("id", id)).uniqueResult();
    }

    public void deleteById(Class clazz, Long id) {
        getSession().createQuery("delete from " + clazz.getName() + " where id = :id").setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public <T> List<T> findByIds(Class<T> clazz, List<Long> ids) {
        return getSession().createCriteria(clazz).add(Restrictions.in("id", ids)).list();
    }

    @Override
    public <T extends IAbstractEntity> List<T> findAll(Class<T> clazz) {
        return getSession().createQuery(String.format("from %s", clazz.getSimpleName())).list();
    }

    @Override
    public <T extends IAbstractEntity> List<T> findAll(Class<T> clazz, Pageable pageable) {
        return executeWithPaging(String.format("from %s", clazz.getSimpleName()), pageable);
    }

    @Override
    public <T extends IAbstractEntity> List<T> findAllOrderedByTimestamp(Class<T> clazz, boolean asc) {
        return getSession().createQuery(
                String.format("from %s order by timestamp %s", clazz.getSimpleName(), asc ? "asc" : "desc")).list();
    }
}
