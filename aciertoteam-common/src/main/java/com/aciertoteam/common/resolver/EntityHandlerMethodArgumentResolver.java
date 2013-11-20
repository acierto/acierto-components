package com.aciertoteam.common.resolver;

import java.util.Arrays;
import java.util.List;
import com.aciertoteam.common.entity.AbstractEntity;
import com.aciertoteam.common.service.EntityService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author ishestiporov
 */
public class EntityHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String ID_PARAM = "id";

    @Autowired
    private EntityService entityService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return AbstractEntity.class.isAssignableFrom(parameter.getParameterType())
                && parameter.hasParameterAnnotation(BindEntity.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        BindEntity bindEntity = getParameterBindEntityAnnotation(parameter);
        if (bindEntity != null) {
            return resolveToEntity(parameter, webRequest, bindEntity);
        }
        return null;
    }

    private Object resolveToEntity(MethodParameter parameter, NativeWebRequest webRequest, BindEntity bindEntity)
            throws IllegalAccessException {

        AbstractEntity entity = getOrCreateEntity(parameter, webRequest, bindEntity);
        List<String> requiredParams = Arrays.asList(bindEntity.required());
        for (String field : bindEntity.fields()) {
            bindSingleProperty(webRequest, entity, requiredParams, field);
        }
        return entity;
    }

    private void bindSingleProperty(NativeWebRequest webRequest, AbstractEntity entity, List<String> requiredParams,
            String field) throws IllegalAccessException {
        String paramValue = webRequest.getParameter(field);
        if (canBind(field, paramValue, requiredParams)) {
            FieldUtils.writeField(entity, field, StringUtils.trim(paramValue), true);
        }
    }

    private boolean canBind(String field, String paramValue, List<String> requiredParams) {
        if (requiredParams.contains(field) && StringUtils.isBlank(paramValue)) {
            throw new MissingRequestParameterException(field, "java.lang.String");
        }
        return true;
    }

    private <T extends AbstractEntity> T getOrCreateEntity(MethodParameter parameter, NativeWebRequest webRequest,
            BindEntity bindEntity) {
        Class<T> parameterType = (Class<T>) parameter.getParameterType();
        String idParam = webRequest.getParameter(ID_PARAM);
        if (StringUtils.isBlank(idParam)) {
            return instantiateEntity(bindEntity, parameterType);
        }
        return entityService.findById(parameterType, Long.valueOf(idParam));
    }

    private <T extends AbstractEntity> T instantiateEntity(BindEntity bindEntity, Class<T> parameterType) {
        if (bindEntity.allowCreate()) {
            return BeanUtils.instantiate(parameterType);
        }
        throw new MissingRequestParameterException(ID_PARAM, "java.lang.Long");
    }

    /**
     * Returns the BindEntity annotation which hold the names of the request
     * parameter to find in the request. Inspects the given
     * {@link MethodParameter} for {@link BindEntity} present which will tell
     * for which parameters to search, which fields are required and whether
     * entity creation is allowed.
     * 
     * @param parameter the {@link MethodParameter} potentially qualified.
     * @return
     */
    private BindEntity getParameterBindEntityAnnotation(MethodParameter parameter) {
        return parameter.getParameterAnnotation(BindEntity.class);
    }
}
