package com.aciertoteam.common.json;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bogdan Nechyporenko
 */
public class CustomJsonView extends AbstractView {

    private String[] attributes;
    private Object targetObject;
    private boolean raw;

    public CustomJsonView(Object result, String... attributes) {
        this(result, false, attributes);
    }

    public CustomJsonView(Object result, boolean raw, String... attributes) {
        this.attributes = attributes;
        this.targetObject = result;
        this.raw = raw;
    }

    @Override
    protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ObjectMapper mapper = createMapper();

        ByteArrayOutputStream bos = new ByteArrayOutputStream(FileCopyUtils.BUFFER_SIZE);
        JsonGenerator generator = mapper.getJsonFactory().createJsonGenerator(bos, JsonEncoding.UTF8);
        mapper.writeValue(generator, raw ? targetObject : getResultMap(targetObject));
        prepareResponse(request, response, bos);
        FileCopyUtils.copy(bos.toByteArray(), response.getOutputStream());
    }

    private ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.getSerializationConfig().setDateFormat(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"));
        mapper.getSerializationConfig().setIntrospector(new AdvancedClassIntrospector(attributes));
        return mapper;
    }

    private Map<String, Object> getResultMap(Object callResult) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", callResult);
        result.put("success", Boolean.TRUE);
        return result;
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
}
