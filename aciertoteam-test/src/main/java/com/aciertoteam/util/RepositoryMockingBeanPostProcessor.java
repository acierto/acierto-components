package com.aciertoteam.util;

import static org.mockito.Mockito.mock;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author Bogdan Nechyporenko
 */
public class RepositoryMockingBeanPostProcessor implements BeanPostProcessor {

    private static final Logger log = Logger.getLogger(RepositoryMockingBeanPostProcessor.class);

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        Object newBean = bean;
        if (newBean.getClass().getName().endsWith("Repository")) {
            log.trace("Creating mock for " + beanName);
            newBean = mock(newBean.getClass());
            addHibernateTemplate(bean, newBean);
        }

        return newBean;
    }

    private void addHibernateTemplate(Object bean, Object newBean) {
        if (HibernateDaoSupport.class.isAssignableFrom(bean.getClass())) {
            HibernateTemplate mockTemplate = mock(HibernateTemplate.class);
            ((HibernateDaoSupport) newBean).setHibernateTemplate(mockTemplate);
        }
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

}
