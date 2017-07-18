/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.icg.imap.tax.api;

import java.io.Serializable;

/**
 *
 * @author Hatta Palino
 */
public class SnappedDetail implements Serializable {
    
    private Integer originalIndex;
    private String  placeId;
    private SnappedLocation location;

    public Integer getOriginalIndex() {
        return originalIndex;
    }

    public void setOriginalIndex(Integer originalIndex) {
        this.originalIndex = originalIndex;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public SnappedLocation getLocation() {
        return location;
    }

    public void setLocation(SnappedLocation location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "SnappedDetail{" + "originalIndex=" + originalIndex + ", placeId=" + placeId + ", location=" + location + '}';
    }
    
}
