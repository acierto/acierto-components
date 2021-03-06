package com.aciertoteam.common.json;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Bogdan Nechyporenko
 */
public class ClearJsonView extends JsonView {

    private Map data;

    @SuppressWarnings("unchecked")
    @Override
    protected void renderMergedOutputModel(Map arg0, HttpServletRequest arg1, HttpServletResponse arg2)
            throws Exception {
        arg0.clear();
        arg0.putAll(data);
        super.renderMergedOutputModel(arg0, arg1, arg2);
    }

    public void setData(Map data) {
        this.data = data;
    }

    public Map getData() {
        return data;
    }
}
