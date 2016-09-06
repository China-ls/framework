package com.infinite.water.beans.propertyeditors;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.util.Date;

public class NumberDateEditor extends PropertyEditorSupport {
    private final boolean allowEmpty;
    private final int exactDateLength;

    public NumberDateEditor(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
        this.exactDateLength = -1;
    }

    public NumberDateEditor(boolean allowEmpty, int exactDateLength) {
        this.allowEmpty = allowEmpty;
        this.exactDateLength = exactDateLength;
    }

    public void setAsText(String text) throws IllegalArgumentException {
        if (this.allowEmpty && !StringUtils.hasText(text)) {
            this.setValue((Object) null);
        } else {
            if (text != null && this.exactDateLength >= 0 && text.length() != this.exactDateLength) {
                throw new IllegalArgumentException("Could not parse date: it is not exactly" + this.exactDateLength + "characters long");
            }

            if (!NumberUtils.isNumber(text)) {
                throw new IllegalArgumentException("Could not parse date: it is not exactly" + this.exactDateLength + "characters long");
            }

            this.setValue(new Date(NumberUtils.toLong(text)));
        }

    }

    public String getAsText() {
        Date value = (Date) this.getValue();
        return value != null ? value.getTime() + "" : "";
    }

}
