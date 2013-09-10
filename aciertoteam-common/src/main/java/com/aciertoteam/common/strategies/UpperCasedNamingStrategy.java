package com.aciertoteam.common.strategies;

import org.hibernate.cfg.ImprovedNamingStrategy;

/**
 * @author Bogdan Nechyporenko
 */
public class UpperCasedNamingStrategy extends ImprovedNamingStrategy {

    @Override
    public String classToTableName(String className) {
        return super.classToTableName(className.toUpperCase());
    }

    @Override
    public String propertyToColumnName(String propertyName) {
        return super.propertyToColumnName(propertyName.toUpperCase());
    }

    @Override
    public String tableName(String tableName) {
        return super.tableName(tableName.toUpperCase());
    }

    @Override
    public String columnName(String columnName) {
        return super.columnName(columnName.toUpperCase());
    }

    @Override
    public String collectionTableName(String ownerEntity, String ownerEntityTable, String associatedEntity, String associatedEntityTable, String propertyName) {
        return super.collectionTableName(ownerEntity.toUpperCase(), ownerEntityTable.toUpperCase(),
                associatedEntity.toUpperCase(), associatedEntityTable.toUpperCase(), propertyName.toUpperCase());
    }

    @Override
    public String joinKeyColumnName(String joinedColumn, String joinedTable) {
        return super.joinKeyColumnName(joinedColumn.toUpperCase(), joinedTable.toUpperCase());
    }

    @Override
    public String foreignKeyColumnName(String propertyName, String propertyEntityName, String propertyTableName, String referencedColumnName) {
        return super.foreignKeyColumnName(propertyName.toUpperCase(), propertyEntityName.toUpperCase(),
                propertyTableName.toUpperCase(), referencedColumnName.toUpperCase());
    }

    @Override
    public String logicalColumnName(String columnName, String propertyName) {
        return super.logicalColumnName(columnName.toUpperCase(), propertyName.toUpperCase());
    }

    @Override
    public String logicalCollectionTableName(String tableName, String ownerEntityTable, String associatedEntityTable, String propertyName) {
        return super.logicalCollectionTableName(tableName.toUpperCase(), ownerEntityTable.toUpperCase(),
                associatedEntityTable.toUpperCase(), propertyName.toUpperCase());
    }

    @Override
    public String logicalCollectionColumnName(String columnName, String propertyName, String referencedColumn) {
        return super.logicalCollectionColumnName(columnName.toUpperCase(), propertyName.toUpperCase(), referencedColumn.toUpperCase());
    }
}
