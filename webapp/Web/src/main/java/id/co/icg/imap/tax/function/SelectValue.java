/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package id.co.icg.imap.tax.function;

import java.io.Serializable;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author Hatta Palino
 */
public class SelectValue implements Serializable {
    private Object value;
    private String label;

    public SelectValue(Object value, String label) {
        this.value = value;
        this.label = label;
    }
    
    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getLabel() {
        return StringEscapeUtils.unescapeHtml(label);
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    
}
