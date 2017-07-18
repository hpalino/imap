/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.icg.imap.tax.web.tagging;

import id.co.icg.imap.tax.dao.model.TaxPerson;
import id.co.icg.imap.tax.dao.model.Attribute;
import id.co.icg.imap.tax.dao.model.User;
import id.co.icg.imap.tax.dao.model.Attachment;
import id.co.icg.imap.tax.function.FunctionUtil;
import id.co.icg.imap.tax.manager.AreaManager;
import id.co.icg.imap.tax.manager.FileManager;
import id.co.icg.imap.tax.web.ActionBean;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.stripes.action.FileBean;
import org.apache.commons.io.FileUtils;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import id.co.icg.imap.tax.manager.TaxManager;
import java.util.Map;

/**
 *
 * @author Fauzi Marjalih
 */
@UrlBinding("/MapTagging.html")
public class MapTaggingActionBean extends ActionBean {

    private String keySearch;

    private String provinceCode;
    private String cityCode;
    private String districtCode;
    private String subDistrict;
    private String rw;
    private String rt;
    private String sector;
    private String zone;
    private String streetClass;
    private String street;
    private Double ppm;
    private String nop;
    
    private String name;
    private String npwp;

    private String dateTime;
    private String filename;
    private String description;
    private String latLng;
    private Double latitude;
    private Double longitude;
    
    
    @ValidateNestedProperties({
        @Validate(field = "productId"  , required = true, minlength = 4),
        @Validate(field = "description", required = true),
        @Validate(field = "amount"     , required = true),
        @Validate(field = "productType", required = true)
    })    
    private Attribute attribute;
    
    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());
    private TaxManager taxManager;
    private AreaManager areaManager;

    @SpringBean("taxManager")
    public void setMapManager(TaxManager taxManager) {
        this.taxManager = taxManager;
    }

    @SpringBean("areaManager")
    public void setAreaManager(AreaManager areaManager) {
        this.areaManager = areaManager;
    }

    private List<FileBean> attachments;
    public List<FileBean> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<FileBean> attachments) {
        this.attachments = attachments;
    }

    public Resolution listProvinces() {
        List<Map<String, String>> data = areaManager.getListProvinces();
        return new StreamingResolution("text", jSon(data));
    }

    public Resolution listCities() {
        List<Map<String, String>> data = areaManager.getListCities(provinceCode);
        return new StreamingResolution("text", jSon(data));
    }

    public Resolution listDistricts() {
        List<Map<String, String>> data = areaManager.getListDistricts(provinceCode, cityCode);
        return new StreamingResolution("text", jSon(data));
    }

    public Resolution listSubDistricts() {
        List<Map<String, String>> data = areaManager.getListSubDistricts(provinceCode, cityCode, districtCode);
        return new StreamingResolution("text", jSon(data));
    }

    public Resolution saveData() {
        User userSession = (User) getContext().getSession("SESSION_USER");
        TaxPerson tagger;
        String addResponse = "";
        setResponse("Success, data was saved." + addResponse);
        return new ForwardResolution("WEB-PAGES/Tagging/MapTagging.jsp");
    }
    
    @DefaultHandler
    @DontValidate
    public Resolution view() {
        return new ForwardResolution("WEB-PAGES/Tagging/MapTagging.jsp");
    }

    @Before
    public void init() {
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNpwp() {
        return npwp;
    }

    public void setNpwp(String npwp) {
        this.npwp = npwp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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

    public String getLatLng() {
        return latLng;
    }

    public void setLatLng(String latLng) {
        if(latLng.contains(",")){
            String[] parse = latLng.split(",");
            this.latitude = Double.valueOf(parse[0]);
            this.longitude = Double.valueOf(parse[1]);
        }
        this.latLng = latLng;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

    public String getKeySearch() {
        return keySearch;
    }

    public void setKeySearch(String keySearch) {
        this.keySearch = keySearch;
    }

    public String getRw() {
        return rw;
    }

    public void setRw(String rw) {
        this.rw = rw;
    }

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getStreetClass() {
        return streetClass;
    }

    public void setStreetClass(String streetClass) {
        this.streetClass = streetClass;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Double getPpm() {
        return ppm;
    }

    public void setPpm(Double ppm) {
        this.ppm = ppm;
    }

    public String getNop() {
        return nop;
    }

    public void setNop(String nop) {
        this.nop = nop;
    }

}
