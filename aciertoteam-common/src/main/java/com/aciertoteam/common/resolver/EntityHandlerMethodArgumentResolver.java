package com.aciertoteam.common.resolver;

import com.aciertoteam.common.entity.AbstractEntity;
import com.aciertoteam.common.service.EntityService;
import com.aciertoteam.common.utils.StringToObjectConverter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * TODO create test
 *
 * @author ishestiporov
 */
@SuppressWarnings("unchecked")
public class EntityHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityHandlerMethodArgumentResolver.class);
    private static final String ID_PARAM = "id";
    @Autowired
    private EntityService entityService;
    @Autowired
    private StringToObjectConverter stringToObjectConverter;
    private EntityArgumentResolverPostProcessor postProcessor = new DefaultLoggingPostProcessor();

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

        AbstractEntity entity = getOrCreateParentEntity(parameter, webRequest, bindEntity);

        Map<String, AbstractEntity> associations = new HashMap<String, AbstractEntity>();
        bindProperties(associations, entity, webRequest, bindEntity, bindEntity.fields());
        return entity;
    }

    private void bindProperties(Map<String, AbstractEntity> associations, AbstractEntity entity, NativeWebRequest
            webRequest, BindEntity bindEntity, String... fields) throws IllegalAccessException {

        for (String field : fields) {
            bindSingleProperty(webRequest, entity, bindEntity, field, associations);
        }
    }

    private AbstractEntity getOrCreateParentEntity(MethodParameter parameter, NativeWebRequest webRequest,
                                                   BindEntity bindEntity) {
        Class<? extends AbstractEntity> parameterType = (Class<? extends AbstractEntity>) parameter.getParameterType();
        String idParam = webRequest.getParameter(ID_PARAM);
        return getAndValidateEntity(idParam, parameterType, bindEntity);
    }

    private void bindSingleProperty(NativeWebRequest webRequest, AbstractEntity entity, BindEntity bindEntity,
                                    String field, Map<String, AbstractEntity> associations) throws IllegalAccessException {

        String[] paramValues = webRequest.getParameterValues(field);
        assertCanBind(field, paramValues, bindEntity.required());
        if (field.contains(".")) {
            bindAssociationProperty(webRequest, entity, bindEntity, field, associations);
        } else {
            if (BindEntity.ALL.equals(field)) {
                bindProperties(associations, entity, webRequest, bindEntity, getFieldNames(entity));
            } else if (BindEntity.COPY.equals(field)) {
                bindProperties(associations, entity, webRequest, bindEntity, bindEntity.required());
            } else {
                Object value = stringToObjectConverter.getValue(entity, field, paramValues);
                FieldUtils.writeField(entity, field, value, true);
            }
        }
    }

    private String[] getFieldNames(AbstractEntity entity) {
        Set<String> fieldNames = new HashSet<String>();
        for (Field field : entity.getClass().getDeclaredFields()) {
            if (!field.isSynthetic() && !Modifier.isStatic(field.getModifiers())) {
                fieldNames.add(field.getName());
            }
        }
        return fieldNames.toArray(new String[fieldNames.size()]);
    }

    // TODO encapsulate logic into the object to avoid so many params
    private void bindAssociationProperty(NativeWebRequest webRequest, AbstractEntity parentEntity,
                                         BindEntity bindEntity, String field, Map<String, AbstractEntity> associations)
            throws IllegalAccessException {

        String paramValue = webRequest.getParameter(field);
        String[] splitField = StringUtils.split(field, ".");

        String association = splitField[0];
        Class<? extends AbstractEntity> associationClass = (Class<? extends AbstractEntity>) BeanUtils
                .getPropertyDescriptor(parentEntity.getClass(), association).getPropertyType();
        AbstractEntity child = associations.get(association);
        if (child == null) {
            child = getAndValidateEntity(webRequest.getParameter(String.format("%s.%s", association, ID_PARAM)),
                    associationClass, bindEntity);
            associations.put(association, child);
            FieldUtils.writeField(parentEntity, association, child, true);
        }
        String childField = splitField[1];
        FieldUtils.writeField(child, childField, StringUtils.trim(paramValue), true);
    }

    private void assertCanBind(String field, String[] paramValues, String[] requiredParams) {
        int length = paramValues.length;
        if (length == 0) {
            throw new MissingRequestParameterException(field, "java.lang.String");
        } else if (length == 1 && isRequired(field, requiredParams) && StringUtils.isBlank(paramValues[0])) {
            throw new MissingRequestParameterException(field, "java.lang.String");
        }
    }

    private boolean isRequired(String field, String[] requiredParams) {
        return ArrayUtils.contains(requiredParams, BindEntity.ALL) || ArrayUtils.contains(requiredParams, field);
    }

    private <T extends AbstractEntity> T getAndValidateEntity(String id, Class<T> clazz, BindEntity bindEntity) {
        T entity = getOrCreateEntity(id, clazz, bindEntity);
        assertNotNull(entity, clazz, id);
        postProcessor.process(entity);
        return entity;
    }

    private <T extends AbstractEntity> T getOrCreateEntity(String id, Class<T> clazz, BindEntity bindEntity) {
        if (StringUtils.isBlank(id)) {
            return instantiateEntity(bindEntity, clazz);
        }
        return entityService.findById(clazz, Long.valueOf(id));
    }

    private void assertNotNull(AbstractEntity entity, Class clazz, String id) {
        if (entity == null) {
            LOGGER.error(String.format("Request for entity by id which does not exist. Class: %s, id: %s", clazz, id));
            throw new IllegalArgumentException("Entity is not found");
        }
    }

    private <T extends AbstractEntity> T instantiateEntity(BindEntity bindEntity, Class<T> parameterType) {
        if (bindEntity.allowCreate()) {
            return BeanUtils.instantiateClass(parameterType);
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

    public void setPostProcessor(EntityArgumentResolverPostProcessor postProcessor) {
        this.postProcessor = postProcessor;
    }
}
