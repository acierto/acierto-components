package com.aciertoteam.common.interfaces;

import java.util.Date;

/**
 * @author Bogdan Nechyporenko
 */
public interface IAbstractEntity extends Identifiable, IdentifiableEntity {

    /**
     * The time when entity starts to be valid.
     *
     * @return
     */
    Date getValidFrom();

    /**
     * The expiration time of entity. It could be equal null in case of endless validity.
     *
     * @return
     */
    Date getValidThru();

    /**
     * The time when the entity has been created.
     *
     * @return
     */
    Date getTimestamp();

    /**
     * Checks whether the entity expiration time has been exceeded.
     *
     * @return
     */
    boolean isDeleted();

    /**
     * Set expiration time with the current time
     */
    void closeEndPeriod();

    /**
     * Executes checking of the entity before performing any actions with it, i.e. saving if some conditions
     * are not matching with expected values range.
     */
    void check();
}
