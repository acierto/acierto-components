package com.aciertoteam.common.repository.impl;

import com.aciertoteam.common.entity.AbstractEntity;
import com.aciertoteam.common.interfaces.IAbstractEntity;
import com.aciertoteam.common.repository.EntityRepository;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Bogdan Nechyporenko
 */
@SuppressWarnings("unchecked")
@Repository(value = "entityRepository")
@Transactional
public class DefaultEntityRepository extends DefaultAbstractRepository<AbstractEntity> implements EntityRepository {

    public long count(Class clazz) {
        return Long.valueOf(String.valueOf(getSession().createCriteria(clazz).
                add(Restrictions.or(Restrictions.isNull("validThru"), Restrictions.gt("validThru", new Date()))).
                setProjection(Projections.rowCount()).uniqueResult()));
    }

    @Override
    public <T> T findByField(Class clazz, String fieldName, Object value) {
        return (T) getSession().createCriteria(clazz).add(Restrictions.eq(fieldName, value)).
                add(Restrictions.or(Restrictions.isNull("validThru"), Restrictions.gt("validThru", new Date()))).
                uniqueResult();
    }

    @Override
    public <T> T findById(Class clazz, Long id) {
        return (T) getSession().createCriteria(clazz).add(Restrictions.eq("id", id)).
                add(Restrictions.or(Restrictions.isNull("validThru"), Restrictions.gt("validThru", new Date()))).
                uniqueResult();
    }

    public void deleteById(Class clazz, Long id) {
        getSession().createQuery("delete from " + clazz.getName() + " where id = :id").setParameter("id", id).executeUpdate();
    }

    @Override
    public <T> List<T> findByIds(Class clazz, List<Long> ids) {
        return getSession().createCriteria(clazz).add(Restrictions.in("id", ids)).
                add(Restrictions.or(Restrictions.isNull("validThru"), Restrictions.gt("validThru", new Date()))).
                list();
    }

    @Override
    public <T extends IAbstractEntity> List<T> findAll(Class<T> clazz) {
        return getSession().createQuery("from " + clazz.getSimpleName() + " where validThru is null or validThru > :now").
                setParameter("now", new Date()).list();
    }

    @Override
    public <T extends IAbstractEntity> List<T> findAllOrderedByTimestamp(Class<T> clazz, boolean asc) {
        return getSession().createQuery("from " + clazz.getSimpleName() + " where validThru is null or validThru > :now" +
                " order by timestamp " + (asc ? "asc" : "desc")).
                setParameter("now", new Date()).list();
    }

    @Override
    public Class getClazz() {
        return AbstractEntity.class;
    }
}
