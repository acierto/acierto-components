package com.aciertoteam.common.json;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

/**
 * @author Bogdan Nechyporenko
 */
public class CustomJsonView extends AbstractView {

    private String[] attributes;
    private Object targetObject;
    private Class targetObjectClass;
    private String encloseName;
    private boolean raw;
    private boolean noDefaultIncludes;
    private Integer total;

    public CustomJsonView(Object result, String... attributes) {
        this(result, false, attributes);
    }

    public CustomJsonView(Object result, int total, String... attributes) {
        this(result, false, attributes);
        this.total = total;
    }

    public CustomJsonView(Object result, boolean raw, String... attributes) {
        this.attributes = attributes;
        this.targetObject = result;
        this.raw = raw;
        setTargetObjectClass(result);
    }

    public CustomJsonView(Object result, boolean raw, boolean noDefaultIncludes, String... attributes) {
        this.attributes = attributes;
        this.targetObject = result;
        this.raw = raw;
        this.noDefaultIncludes = noDefaultIncludes;
        setTargetObjectClass(result);
    }

    private CustomJsonView(Object result, String encloseName, String... attributes) {
        this(result, true, true, attributes);
        this.encloseName = encloseName;
    }

    public static CustomJsonView createEnclosedCollectionView(Object result, String encloseName, String... attributes) {
        return new CustomJsonView(result, encloseName, attributes);
    }

    private static void prepareResponse(HttpServletRequest request, HttpServletResponse response,
            ByteArrayOutputStream bos) {
        String contentType = request.getContentType();
        if (contentType != null && contentType.startsWith("multipart/form-data")) {
            response.setContentType("text/html; charset=UTF-8");
        } else {
            response.setContentType("application/json; charset=UTF-8");
        }
        response.setContentLength(bos.size());
    }

    private void setTargetObjectClass(Object targetObject) {
        if (Collection.class.isAssignableFrom(targetObject.getClass())) {
            Collection collection = (Collection) targetObject;
            if (!collection.isEmpty()) {
                targetObjectClass = collection.iterator().next().getClass();
            }
        } else {
            targetObjectClass = targetObject.getClass();
        }
    }

    @Override
    protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ObjectMapper mapper = createMapper(targetObjectClass);

        ByteArrayOutputStream bos = new ByteArrayOutputStream(FileCopyUtils.BUFFER_SIZE);
        JsonGenerator generator = mapper.getJsonFactory().createJsonGenerator(bos, JsonEncoding.UTF8);

        if (!raw) {
            mapper.writeValue(generator, getResultMap(targetObject));
        } else if (StringUtils.isNotBlank(encloseName)) {
            mapper.writeValue(generator, Collections.singletonMap(encloseName, targetObject));
        } else {
            mapper.writeValue(generator, targetObject);
        }

        prepareResponse(request, response, bos);
        FileCopyUtils.copy(bos.toByteArray(), response.getOutputStream());
    }

    private ObjectMapper createMapper(Class clazz) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.getSerializationConfig().setDateFormat(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"));
        mapper.getSerializationConfig().setIntrospector(
                new AdvancedClassIntrospector(clazz, noDefaultIncludes, attributes));
        return mapper;
    }

    private Map<String, Object> getResultMap(Object callResult) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", callResult);
        result.put("success", Boolean.TRUE);
        if (total != null) {
            result.put("total", total);
        }
        return result;
    }
}
