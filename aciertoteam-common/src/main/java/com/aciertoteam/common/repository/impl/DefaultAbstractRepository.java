package com.aciertoteam.common.repository.impl;

import com.aciertoteam.common.entity.AbstractEntity;
import com.aciertoteam.common.interfaces.IAbstractEntity;
import com.aciertoteam.common.model.Clock;
import com.aciertoteam.common.repository.AbstractRepository;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @param <T> IAbstractEntity
 * @author Bogdan Nechyporenko
 */
@SuppressWarnings("unchecked")
public abstract class DefaultAbstractRepository<T extends IAbstractEntity> implements AbstractRepository<T> {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private Clock clock;

    @Override
    @Transactional(readOnly = true)
    public List<T> getAll() {
        return (List<T>) getSession().createCriteria(getClazz()).list();
    }

    @Override
    public List<T> getAll(int from, int to) {
        return (List<T>) getSession().createCriteria(getClazz()).setFirstResult(from).setMaxResults(to - from).list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getAllIncludingDeleted() {
        return (List<T>) getSession().createCriteria(getClazz()).list();
    }

    @Override
    @Transactional(readOnly = true)
    public T get(Long id) {
        return (T) getSession().createCriteria(getClazz()).add(Restrictions.eq("id", id)).uniqueResult();
    }

    @Override
    public void saveAll(Collection<? extends T> coll) {
        for (T t : coll) {
            save(t);
        }
    }

    @Override
    public T save(T t) {
        getSession().save(t);
        return t;
    }

    @Override
    public T findByField(String fieldName, Object value) {
        Criteria criteria = getSession().createCriteria(getClazz());
        return (T) criteria.add(Restrictions.like(fieldName, value)).uniqueResult();
    }

    @Override
    public T findByField(String fieldName, Object value, boolean onlyActive) {
        Criteria criteria = getSession(onlyActive).createCriteria(getClazz());
        return (T) criteria.add(Restrictions.like(fieldName, value)).uniqueResult();
    }

    @Override
    public List<T> findListByField(String fieldName, Object value) {
        Criteria criteria = getSession().createCriteria(getClazz());
        return criteria.add(Restrictions.like(fieldName, value)).list();
    }

    @Override
    public Collection<T> findCollectionByField(String fieldName, Object value) {
        Criteria criteria = getSession().createCriteria(getClazz());
        return criteria.add(Restrictions.like(fieldName, value)).list();
    }

    @Override
    public T saveOrUpdate(T t) {
        t.check();
        if (t.getId() == null) {
            getSession().saveOrUpdate(t);
            return t;
        } else {
            return (T) getSession().merge(t);
        }
    }

    @Override
    public void markAsDeleted(Long id) {
        T t = get(id);
        t.closeEndPeriod();
        saveOrUpdate(t);
    }

    @Override
    public void markAsDeleted(List<T> entities) {
        for (T t : entities) {
            t.closeEndPeriod();
            saveOrUpdate(t);
        }
    }

    @Override
    public void markAsDeleted(T entity) {
        entity.closeEndPeriod();
        saveOrUpdate(entity);
    }

    @Override
    public void delete(Long id) {
        getSession().delete(get(id));
    }

    public void deleteByIds(final List<Long> ids) {
        getSession().createQuery("delete from " + getClazz() + " sc where sc.id in (:ids)")
                .setParameterList("ids", ids).executeUpdate();
    }

    @Override
    public void delete(List<T> entities) {
        for (T entity : entities) {
            delete(entity);
        }
    }

    @Override
    public void deleteByFieldName(String fieldName, Object value) {
        getSession().createQuery("delete from " + getClazz().getSimpleName() + " where " + fieldName + " = " + value)
                .executeUpdate();
    }

    @Override
    public void delete(T entity) {
        getSession().delete(entity);
    }

    @Override
    public void deleteAll() {
        getSession().createQuery("delete from " + getClazz().getSimpleName()).executeUpdate();
    }

    @Override
    public List<T> getList(List<Long> ids) {
        return getSession().createCriteria(getClazz()).add(Restrictions.in("id", ids)).list();
    }

    @Override
    public Set<T> getSet(List<Long> ids) {
        return new HashSet<T>(getList(ids));
    }

    @Override
    public long count() {
        return Long.valueOf(String.valueOf(getSession().createCriteria(getClazz())
                .setProjection(Projections.rowCount()).uniqueResult()));
    }

    @Override
    public boolean isEmpty() {
        return count() == 0;
    }

    public Class getClazz() {
        ParameterizedType superClass = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class) superClass.getActualTypeArguments()[0];
    }

    protected final Session getSession() {
        return getSession(true);
    }

    protected final Session getSession(boolean applyValidThruFilter) {
        Session session = SessionFactoryUtils.getSession(sessionFactory, true);
        if (applyValidThruFilter) {
            session.enableFilter(AbstractEntity.VALID_THRU_FILTER).setParameter(AbstractEntity.NOW_PARAM,
                    getCurrentDate());
        } else {
            session.disableFilter(AbstractEntity.VALID_THRU_FILTER);
        }
        return session;
    }

    protected Date getCurrentDate() {
        return clock.getCurrentDate();
    }

    protected <T extends IAbstractEntity> List<T> executeWithPaging(String querySql, Pageable pageable) {
        return executeWithPaging(querySql, pageable, null);
    }

    /**
     * Executes query with paging parameters
     * @param querySql query to execute
     * @param pageable paging specification that can contain sort
     * @return results in list
     */
    protected <T extends IAbstractEntity> List<T> executeWithPaging(String querySql, Pageable pageable, Params params) {
        String sql = addSort(querySql, pageable);
        Query query = getSession().createQuery(sql);
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        addParams(params, query);
        return query.list();
    }

    private void addParams(Params params, Query query) {
        if (params != null) {
            params.addToQuery(query);
        }
    }

    private String addSort(String query, Pageable pageable) {
        if (pageable.getSort() != null) {
            return addOrderBy(query, pageable);
        }
        return query;
    }

    private String addOrderBy(String query, Pageable pageable) {
        StringBuilder builder = new StringBuilder(query).append(" order by ");
        for (Sort.Order order : pageable.getSort()) {
            builder.append(addOrderByProperty(order)).append(" ");
        }
        return builder.toString();
    }

    private String addOrderByProperty(Sort.Order order) {
        return String.format("%s %s", order.getProperty(), order.getDirection());
    }
}
