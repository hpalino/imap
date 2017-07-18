/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.icg.imap.tax.api;

import java.util.List;

/**
 *
 * @author Hatta Palino
 */
public class Snapped {
    
    private List<SnappedDetail> snappedPoints;
    private String warningMessage;
    
    public List<SnappedDetail> getSnappedPoints() {
        return snappedPoints;
    }

    public void setSnappedPoints(List<SnappedDetail> snappedPoints) {
        this.snappedPoints = snappedPoints;
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }

    @Override
    public String toString() {
        return "Snapped{" + "snappedPoints=" + snappedPoints + ", warningMessage=" + warningMessage + '}';
    }
    
}
