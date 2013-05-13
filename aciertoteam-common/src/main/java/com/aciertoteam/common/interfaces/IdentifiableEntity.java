package com.aciertoteam.common.interfaces;

/**
 * @author Bogdan Nechyporenko
 */
public interface IdentifiableEntity extends Identifiable {

    /**
     * Returns self object to be able to add/delete/update it implicitly by means of repository methods
     *
     * @return
     */
    IAbstractEntity getEntity();
}
