package com.aciertoteam.i18n.dto;

import java.util.Date;
import com.aciertoteam.common.entity.AbstractEntity;
import com.aciertoteam.common.interfaces.Identifiable;
import org.apache.commons.lang3.ObjectUtils;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Bogdan Nechyporenko
 */
public class AbstractDTO implements Identifiable {

    private static final long serialVersionUID = 1L;

    @JsonProperty
    private Long id;

    @JsonProperty("valid_from")
    private Date validFrom;

    @JsonProperty("valid_thru")
    private Date validThru;

    @JsonProperty
    private Date timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidThru() {
        return validThru;
    }

    public void setValidThru(Date validThru) {
        this.validThru = validThru;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public static void setBasicFields(AbstractDTO dto, AbstractEntity entity) {
        dto.setId(entity.getId());
        dto.setTimestamp(entity.getTimestamp());
        dto.setValidFrom(entity.getValidFrom());
        dto.setValidThru(entity.getValidThru());
    }

    @Override
    public String getStringId() {
        return ObjectUtils.toString(id);
    }
}
