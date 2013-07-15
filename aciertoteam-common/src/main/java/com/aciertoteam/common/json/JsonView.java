package com.aciertoteam.common.json;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.filters.OrPropertyFilter;
import net.sf.json.util.PropertyFilter;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.AbstractView;

/**
 * @author Bogdan Nechyporenko
 */
public class JsonView extends AbstractView {

    /**
     * Default content type. Overridden as bean property.
     */
    private static final String DEFAULT_JSON_CONTENT_TYPE = "application/json;charset=UTF-8";
    private static final String FILE_UPLOAD_CONTENT_TYPE = "text/html;charset=UTF-8";
    private boolean forceTopLevelArray = false;
    private boolean coerceModelToArray = false;
    private boolean skipBindingResult = true;
    private static final String DEFAULT_MODEL_NAME = "data";
    private boolean success = true;
    /**
     * Json configuration
     */
    private JsonConfig jsonConfig = new JsonConfig();
    private static final String MESSAGE = "message";
    private static final String ERRORS = "errors";

    public JsonView(JsonConfig config) {
        setContentType(DEFAULT_JSON_CONTENT_TYPE);
        setJsonConfig(config);
    }

    public JsonView(String modelName, Object modelObject) {
        this(modelName, modelObject, false);
    }

    @SuppressWarnings("unchecked")
    public JsonView(String modelName, Object modelObject, boolean fileUpload) {
        if (fileUpload) {
            setContentType(FILE_UPLOAD_CONTENT_TYPE);
        } else {
            setContentType(DEFAULT_JSON_CONTENT_TYPE);
        }
        getAttributesMap().put(modelName, modelObject);
    }

    public JsonView(Object modelObject) {
        this(DEFAULT_MODEL_NAME, modelObject);
    }

    /**
     * Create a {@link JsonView} that renders using text/html content type, for uploads.
     * 
     * @param fileUpload if this is a fileupload view or not, if true will use {@link #FILE_UPLOAD_CONTENT_TYPE} instead
     *            of {@link #DEFAULT_JSON_CONTENT_TYPE}
     */
    public JsonView(boolean fileUpload) {
        this();
        if (fileUpload) {
            setContentType(FILE_UPLOAD_CONTENT_TYPE);
        }
    }

    public JsonView() {
        this(new JsonConfig());
    }

    public JsonConfig getJsonConfig() {
        return jsonConfig;
    }

    public boolean isForceTopLevelArray() {
        return forceTopLevelArray;
    }

    /**
     * Returns whether the JSONSerializer will ignore or not its internal property exclusions.
     */
    public boolean isIgnoreDefaultExcludes() {
        return jsonConfig.isIgnoreDefaultExcludes();
    }

    /**
     * Returns whether the JSONSerializer will skip or not any BindingResult related keys on the model.
     * <p/>
     * Models in Spring >= 2.5 will cause an exception as they contain a BindingResult that cycles back.
     */
    public boolean isSkipBindingResult() {
        return skipBindingResult;
    }

    /**
     * Sets the group of properties to be excluded.
     */
    public void setExcludedProperties(String[] excludedProperties) {
        jsonConfig.setExcludes(excludedProperties);
    }

    public void setForceTopLevelArray(boolean forceTopLevelArray) {
        this.forceTopLevelArray = forceTopLevelArray;
    }

    /**
     * Sets whether the JSONSerializer will ignore or not its internal property exclusions.
     */
    public void setIgnoreDefaultExcludes(boolean ignoreDefaultExcludes) {
        jsonConfig.setIgnoreDefaultExcludes(ignoreDefaultExcludes);
    }

    public void setJsonConfig(JsonConfig jsonConfig) {
        this.jsonConfig = jsonConfig != null ? jsonConfig : new JsonConfig();
        if (skipBindingResult) {
            PropertyFilter jsonPropertyFilter = this.jsonConfig.getJsonPropertyFilter();
            if (jsonPropertyFilter == null) {
                this.jsonConfig.setJsonPropertyFilter(new BindingResultPropertyFilter());
            } else {
                this.jsonConfig.setJsonPropertyFilter(new OrPropertyFilter(new BindingResultPropertyFilter(),
                        jsonPropertyFilter));
            }
        }
    }

    /**
     * Sets whether the JSONSerializer will skip or not any BindingResult related keys on the model.
     * <p/>
     * Models in Spring >= 2.5 will cause an exception as they contain a BindingResult that cycles back.
     */
    public void setSkipBindingResult(boolean skipBindingResult) {
        this.skipBindingResult = skipBindingResult;
    }

    /**
     * Creates a JSON [JSONObject,JSONArray,JSONNUll] from the model values.
     */
    protected JSON createJSON(Map model, HttpServletRequest request, HttpServletResponse response) {
        return defaultCreateJSON(model);
    }

    /**
     * Creates a JSON [JSONObject,JSONArray,JSONNUll] from the model values.
     */
    protected final JSON defaultCreateJSON(Map model) {
        if (skipBindingResult && jsonConfig.getJsonPropertyFilter() == null) {
            this.jsonConfig.setJsonPropertyFilter(new BindingResultPropertyFilter());
        }
        if (coerceModelToArray) {
            Collection values = model.values();

            if (values.size() != 1) {
                throw new IllegalStateException(
                        "Model must contain exactly one Collection element to coerce to json array");
            }

            return JSONSerializer.toJSON(values.iterator().next(), jsonConfig);
        }
        return JSONSerializer.toJSON(model, jsonConfig);
    }

    /**
     * Returns the group of properties to be excluded.
     */
    protected String[] getExcludedProperties() {
        return jsonConfig.getExcludes();
    }

    public void setCoerceModelToArray(boolean coerceModelToArray) {
        this.coerceModelToArray = coerceModelToArray;
    }

    public void setFailed() {
        success = false;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        if (!coerceModelToArray) {
            model.put("success", success);
        }
        response.setContentType(getContentType());
        writeJSON(model, request, response);
    }

    protected void writeJSON(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSON json = createJSON(model, request, response);
        if (forceTopLevelArray) {
            json = new JSONArray().element(json);
        }
        json.write(response.getWriter());
    }

    /**
     * Binding result property filter
     */
    private static class BindingResultPropertyFilter implements PropertyFilter {

        @Override
        public boolean apply(Object source, String name, Object value) {
            return name.startsWith(BindingResult.MODEL_KEY_PREFIX);
        }
    }

    public static CustomModelAndView createErrorModelAndView(String message) {
        return new CustomModelAndView(new JsonView(), ERRORS, Collections.singletonMap(MESSAGE, message));
    }
}
