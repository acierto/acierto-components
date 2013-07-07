package com.aciertoteam.common.annotations;

import java.lang.annotation.*;

/**
 * Used in case when method's auto-call in test shouldn't be performed. I.e. it can be because of NPE inside of method
 * or other cases.
 *
 * @author Bogdan Nechyporenko
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Skip {
}
