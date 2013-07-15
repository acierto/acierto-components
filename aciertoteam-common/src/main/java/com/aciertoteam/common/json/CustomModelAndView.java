package com.aciertoteam.common.json;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

/**
 * @author Bogdan Nechyporenko
 */
public class CustomModelAndView extends ModelAndView {

    /**
     * Creates a generic JsonView with a default name
     * 
     * @param modelObject
     * @see JsonView
     */
    public CustomModelAndView(Object modelObject) {
        super(new JsonView(modelObject));
    }

    /**
     * Creates a Model And View with the provided JsonView
     * 
     * @param jsonView
     * @see JsonView
     */
    public CustomModelAndView(JsonView jsonView) {
        super(jsonView);
    }

    /**
     * Creates a generic JsonView with a specific name
     * 
     * @param viewName
     * @param modelObject
     * @see JsonView
     */
    public CustomModelAndView(String viewName, Object modelObject) {
        super(new JsonView(viewName, modelObject));
    }

    /**
     * Creates new ModelAndView given a View object and a model. <emphasis>Note:
     * the supplied model data is copied into the internal storage of this
     * class. You should not consider to modify the supplied Map after supplying
     * it to this class</emphasis>
     * 
     * @param view View object to render
     * @param model Map of model names (Strings) to model objects (Objects).
     *        Model entries may not be <code>null</code>, but the model Map may
     *        be <code>null</code> if there is no model data.
     */
    public CustomModelAndView(View view, Map model) {
        setView(view);
        if (model != null) {
            getModelMap().addAllAttributes(model);
        }
    }

    /**
     * Convenient constructor to take a single model object.
     * 
     * @param view View object to render
     * @param modelName name of the single entry in the model
     * @param modelObject the single model object
     */
    public CustomModelAndView(View view, String modelName, Object modelObject) {
        setView(view);
        addObject(modelName, modelObject);
    }

    public CustomModelAndView() {
        this(false);
    }

    public CustomModelAndView(boolean fileUpload) {
        super(new JsonView("success", true, fileUpload));
    }

    public static CustomModelAndView createClearModelAndView() {
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("success", Boolean.TRUE);
        ClearJsonView clearJsonView = new ClearJsonView();
        clearJsonView.setData(ret);
        return new CustomModelAndView(clearJsonView);
    }

    public static CustomModelAndView createErrorModelAndView(Object model) {
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("success", Boolean.FALSE);
        ret.put("errors", model);
        ClearJsonView clearJsonView = new ClearJsonView();
        clearJsonView.setData(ret);
        return new CustomModelAndView(clearJsonView);
    }
}
