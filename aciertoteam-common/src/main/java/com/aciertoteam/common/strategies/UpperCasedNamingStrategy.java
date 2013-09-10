package com.aciertoteam.common.strategies;

import org.hibernate.cfg.ImprovedNamingStrategy;

/**
 * @author Bogdan Nechyporenko
 */
public class UpperCasedNamingStrategy extends ImprovedNamingStrategy {

    @Override
    public String classToTableName(String className) {
        return upper(super.classToTableName(className));
    }

    @Override
    public String propertyToColumnName(String propertyName) {
        return upper(super.propertyToColumnName(propertyName));
    }

    @Override
    public String tableName(String tableName) {
        return upper(super.tableName(tableName));
    }

    @Override
    public String columnName(String columnName) {
        return upper(super.columnName(columnName));
    }

    @Override
    public String collectionTableName(String ownerEntity, String ownerEntityTable, String associatedEntity, String associatedEntityTable, String propertyName) {
        return upper(super.collectionTableName(ownerEntity, ownerEntityTable, associatedEntity, associatedEntityTable, propertyName));
    }

    @Override
    public String joinKeyColumnName(String joinedColumn, String joinedTable) {
        return upper(super.joinKeyColumnName(joinedColumn, joinedTable));
    }

    @Override
    public String foreignKeyColumnName(String propertyName, String propertyEntityName, String propertyTableName, String referencedColumnName) {
        return upper(super.foreignKeyColumnName(propertyName, propertyEntityName, propertyTableName, referencedColumnName));
    }

    @Override
    public String logicalColumnName(String columnName, String propertyName) {
        return upper(super.logicalColumnName(columnName, propertyName));
    }

    @Override
    public String logicalCollectionTableName(String tableName, String ownerEntityTable, String associatedEntityTable, String propertyName) {
        return upper(super.logicalCollectionTableName(tableName, ownerEntityTable, associatedEntityTable, propertyName));
    }

    @Override
    public String logicalCollectionColumnName(String columnName, String propertyName, String referencedColumn) {
        return upper(super.logicalCollectionColumnName(columnName, propertyName, referencedColumn));
    }

    private String upper(String value) {
        return value == null ? null : value.toUpperCase();
    }
}
