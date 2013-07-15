package com.aciertoteam.repository;

import java.net.MalformedURLException;
import com.aciertoteam.common.repository.EntityRepository;
import com.aciertoteam.dbunit.HsqlJdbcDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author Bogdan Nechyporenko
 */
public abstract class AbstractDbUnitRepositoryTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier(value = "transactionManager")
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private HsqlJdbcDatabaseTester databaseTester;

    @Autowired
    private EntityRepository entityRepository;

    private TransactionTemplate transactionTemplate;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Before
    public void setUp() throws Exception {
        preDatabaseTesterSetup();
        databaseTester.onSetup();
        preSetUp();
        onSetUp();
    }

    protected void preDatabaseTesterSetup() throws DataSetException, MalformedURLException {
        // do nothing by default
    }

    protected void preSetUp() {
        // do nothing by default
    }

    protected void onSetUp() {
        // do nothing by default
    }

    @After
    public void tearDown() throws Exception {
        databaseTester.onTearDown();
    }

    public EntityRepository getEntityRepository() {
        return entityRepository;
    }

    protected TransactionTemplate getTransactionTemplate() {
        if (transactionTemplate == null) {
            transactionTemplate = new TransactionTemplate(platformTransactionManager);
        }
        return transactionTemplate;
    }

    public HsqlJdbcDatabaseTester getDatabaseTester() {
        return databaseTester;
    }
}
