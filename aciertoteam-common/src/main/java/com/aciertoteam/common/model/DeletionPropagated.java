package com.aciertoteam.common.model;

import com.aciertoteam.common.entity.AbstractEntity;

import java.util.List;

/**
 * @author ishestiporov
 */
public interface DeletionPropagated {

    List<AbstractEntity> getEntitiesForDeletionPropagation();
}
