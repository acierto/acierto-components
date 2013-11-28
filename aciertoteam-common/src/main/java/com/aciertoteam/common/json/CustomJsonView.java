package com.aciertoteam.common.json;

import flexjson.JSONSerializer;
import org.apache.commons.io.IOUtils;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        String serialized = prepareSerializer().serialize(raw ? targetObject : getResultMap(targetObject));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.write(serialized, out);
        prepareResponse(request, response, out);
        IOUtils.write(out.toByteArray(), response.getOutputStream());
    }

    private JSONSerializer prepareSerializer() {
        JSONSerializer jsonSerializer = new JSONSerializer().exclude("*.class").include("data.id", "success.*")
                .include(getErichedAttributes());
        if (!includeAll()) {
            jsonSerializer.exclude("*");
        }
        return jsonSerializer;
    }

    private boolean includeAll() {
        return attributes != null && attributes.length > 0 && attributes[0].equals("*");
    }

    private String[] getErichedAttributes() {
        if (!raw && attributes != null) {
            List<String> enriched = new ArrayList<String>();
            for (String attribute : attributes) {
                enriched.add("data." + attribute);
            }
            return enriched.toArray(new String[enriched.size()]);
        }
        return attributes;
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
