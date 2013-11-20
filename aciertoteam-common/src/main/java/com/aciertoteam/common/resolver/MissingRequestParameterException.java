package com.aciertoteam.common.resolver;

/**
 * Exception thrown when required request parameter is missing.
 * 
 * @author ishestiporov
 */
public class MissingRequestParameterException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String parameterName;
    private final String parameterType;

    public MissingRequestParameterException(String parameterName, String parameterType) {
        this.parameterName = parameterName;
        this.parameterType = parameterType;
    }

    @Override
    public String getMessage() {
        return String.format("Required %s parameter '%s' is not present", parameterType, parameterName);
    }
}
