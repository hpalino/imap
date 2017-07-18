package id.co.icg.imap.tax.dao.model;

//import java.util.Date;

import java.util.Date;


public class MapCluster{

    private String id;
    private String name;
    private String upline;
    private String aro;
    private Date lastLogin;
    private Double latitude;
    private Double longitude;

    public MapCluster() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getUpline() {
        return upline;
    }

    public void setUpline(String upline) {
        this.upline = upline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * @return the aro
     */
    public String getAro() {
        return aro;
    }

    /**
     * @param aro the aro to set
     */
    public void setAro(String aro) {
        this.aro = aro;
    }

}
