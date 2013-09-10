package com.aciertoteam.common.strategies;

import org.hibernate.cfg.ImprovedNamingStrategy;

/**
 * @author Bogdan Nechyporenko
 */
public class UpperCasedNamingStrategy extends ImprovedNamingStrategy {

    @Override
    public String classToTableName(String className) {
        return super.classToTableName(upper(className));
    }

    @Override
    public String propertyToColumnName(String propertyName) {
        return super.propertyToColumnName(upper(propertyName));
    }

    @Override
    public String tableName(String tableName) {
        return super.tableName(upper(tableName));
    }

    @Override
    public String columnName(String columnName) {
        return super.columnName(upper(columnName));
    }

    @Override
    public String collectionTableName(String ownerEntity, String ownerEntityTable, String associatedEntity, String associatedEntityTable, String propertyName) {
        return super.collectionTableName(upper(ownerEntity), upper(ownerEntityTable),
                upper(associatedEntity), upper(associatedEntityTable), upper(propertyName));
    }

    @Override
    public String joinKeyColumnName(String joinedColumn, String joinedTable) {
        return super.joinKeyColumnName(upper(joinedColumn), upper(joinedTable));
    }

    @Override
    public String foreignKeyColumnName(String propertyName, String propertyEntityName, String propertyTableName, String referencedColumnName) {
        return super.foreignKeyColumnName(upper(propertyName), upper(propertyEntityName),
                upper(propertyTableName), upper(referencedColumnName));
    }

    @Override
    public String logicalColumnName(String columnName, String propertyName) {
        return super.logicalColumnName(upper(columnName), upper(propertyName));
    }

    @Override
    public String logicalCollectionTableName(String tableName, String ownerEntityTable, String associatedEntityTable, String propertyName) {
        return super.logicalCollectionTableName(upper(tableName), upper(ownerEntityTable),
                upper(associatedEntityTable), upper(propertyName));
    }

    @Override
    public String logicalCollectionColumnName(String columnName, String propertyName, String referencedColumn) {
        return super.logicalCollectionColumnName(upper(columnName), upper(propertyName), upper(referencedColumn));
    }

    private String upper(String value) {
        return value == null ? null : value.toUpperCase();
    }
}
