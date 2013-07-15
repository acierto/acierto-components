package com.aciertoteam.dbunit;

import javax.sql.DataSource;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;

/**
 * @author Bogdan Nechyporenko
 */
public class HsqlJdbcDatabaseTester extends DataSourceDatabaseTester {

    private static final String[] DEFAULT_TABLE_TYPE = { "VIEW", "TABLE" };

    public HsqlJdbcDatabaseTester(DataSource dataSource) {
        super(dataSource);
    }

    public IDatabaseConnection getConnection() throws Exception {
        IDatabaseConnection connection = super.getConnection();

        DatabaseConfig config = connection.getConfig();
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqlDataTypeFactory());
        config.setProperty(DatabaseConfig.PROPERTY_TABLE_TYPE, DEFAULT_TABLE_TYPE);
        config.setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, false);

        return connection;
    }
}
