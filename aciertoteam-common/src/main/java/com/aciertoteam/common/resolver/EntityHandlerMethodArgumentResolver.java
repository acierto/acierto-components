package com.aciertoteam.common.resolver;

import com.aciertoteam.common.entity.AbstractEntity;
import com.aciertoteam.common.service.EntityService;
import org.apache.commons.lang3.StringUtils;
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
                && parameter.hasParameterAnnotation(Updatable.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        String[] fieldsToBind = getParameterNamesToBind(parameter);
        if (fieldsToBind != null) {
            String idParam = webRequest.getParameter(ID_PARAM);
            Class<? extends AbstractEntity> parameterType = (Class<? extends AbstractEntity>) parameter
                    .getParameterType();
            if (StringUtils.isBlank(idParam)) {

            } else {

                AbstractEntity entity = entityService.findById(parameterType, Long.valueOf(idParam));
            }

            /*
             * for (String field : parameterNamesToBind) {
             * parameterType.getField() }
             */

        }
        return null;
    }

    /**
     * Returns the names of the request parameter to find in the request.
     * Inspects the given {@link MethodParameter} for {@link Updatable} present
     * which will tell for which parameters to search.
     * 
     * @param parameter the {@link MethodParameter} potentially qualified.
     * @return
     */
    private String[] getParameterNamesToBind(MethodParameter parameter) {
        return parameter.getParameterAnnotation(Updatable.class).fields();
    }
}
