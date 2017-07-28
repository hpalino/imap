/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.icg.imap.tax.web.tagging;

import id.co.icg.imap.tax.dao.model.Attachment;
import id.co.icg.imap.tax.dao.model.TaxPerson;
import id.co.icg.imap.tax.dao.model.Attribute;
import id.co.icg.imap.tax.dao.model.AttributeMap;
import id.co.icg.imap.tax.dao.model.User;
import id.co.icg.imap.tax.dao.model.Value;
import id.co.icg.imap.tax.manager.AreaManager;
import id.co.icg.imap.tax.web.ActionBean;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;
import java.util.List;
import net.sourceforge.stripes.action.FileBean;
import id.co.icg.imap.tax.manager.TaxManager;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Fauzi Marjalih
 */
@UrlBinding("/MapTagging.html")
public class MapTaggingActionBean extends ActionBean {

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    private String searchKey;

    private String provinceCode;
    private String cityCode;
    private String districtCode;
    private String subDistrictCode;
    private String rw;
    private String rt;
    private String sector;
    private String zone;
    private String streetClass;
    private String street;
    private Integer year;
    private Double ppm;
    private String nop;
    
    private Long attributeId;
    private Long taxId;
    private String name;
    private String npwp;

    private String dateInput;
    private String description;
    private String latLng;
    private Double latitude;
    private Double longitude;
    
    
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

    @DontValidate
    public Resolution getListNpwp() {
        List<TaxPerson> taxPersons = taxManager.getListTaxPersons(npwp, name);
            return new StreamingResolution("text", jSon(taxPersons!=null?taxPersons:""));
    }

    @DontValidate
    public Resolution getListNop() {
        List<Attribute> attributes = taxManager.getListAttributes(getTaxId(), nop, null);
        return new StreamingResolution("text", jSon(attributes!=null?attributes:""));
    }

    @DontValidate
    public Resolution getListAttributeMaps() {
        List<AttributeMap> attributeMaps = taxManager.getListAttributeMaps(provinceCode, cityCode, districtCode, subDistrictCode);
        Map<Long, Object> map=new HashMap();
        Long countTaxPerson = (long)0;
        for (AttributeMap attributeMap : attributeMaps) {
            if (map.containsKey(attributeMap.getTaxId())) continue;
            countTaxPerson++;
            map.put(attributeMap.getTaxId(), 0);
        }
        return new StreamingResolution("text", "{\"countAttribute\":" + attributeMaps.size() + ",\"countTaxPerson\":" + countTaxPerson + ",\"content\":" + jSon(attributeMaps) + "}");
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
        String strResp="";
        User userSession = (User) getContext().getSession("SESSION_USER");
        this.setNpwp(npwp.replaceAll("[^0-9]", ""));
        if(npwp.length()==15){
            if(taxManager.getTaxPerson(npwp)==null){
                TaxPerson taxPerson = new TaxPerson();
                taxPerson.setName(name);
                taxPerson.setNpwp(npwp);
                taxPerson.setDescription(description);
                taxPerson.setUserInput(userSession.getUsername());
                taxPerson.setDateInput(new Date());
                Integer resp = taxManager.insertTaxPerson(taxPerson);
                if(resp==1){
                    strResp="New NPWP was successfully saved: " + npwp;
                } else {
                    setResponse("Failed to save new NPWP. Please contact your administrator.");
                    return new ForwardResolution("WEB-PAGES/Tagging/MapTagging.jsp");
                }
            }
            this.setNop(nop.replaceAll("[^0-9]", ""));
            if(nop.length()==18){
                Attribute attribute = new Attribute();
                Value value = new Value();
                attribute.setId(attributeId);
                attribute.setNop(nop);
                attribute.setAreaCode(String.join("", provinceCode, cityCode, districtCode, subDistrictCode));
                attribute.setLatitude(latitude);
                attribute.setLongitude(longitude);
                attribute.setSector(sector);
                attribute.setZone(zone);
                attribute.setRt(rt);
                attribute.setRw(rw);
                attribute.setStreet(street);
                attribute.setStreetClass(streetClass);
                attribute.setTaxId(taxManager.getTaxPerson(npwp).getId());
                attribute.setDateInput(new Date());
                attribute.setUserInput(userSession.getUsername());
                Integer resp = 0;
                // Insert new if attribute not found
                Attribute chkAttributeByNop = taxManager.getAttribute(nop, null);
                Attribute chkAttributeById = taxManager.getAttribute(null, attributeId);
                String sResp="";
                if(chkAttributeByNop==null&&chkAttributeById==null){
                    resp = taxManager.insertAttribute(attribute);
                    if(resp==1) sResp="New NOP was successfully saved: " + nop;
                    else sResp="Failed to save new NOP (" + resp + "): " + nop;
                } else {
                    // Update existing if attribute found
                    // Check first, if has more than 1 same nop, not allowed
                    if((chkAttributeByNop==null&&chkAttributeById!=null&&chkAttributeById.getTaxId().compareTo(taxId)==0)||(chkAttributeByNop!=null&&chkAttributeById!=null&&chkAttributeByNop.getNop().equals(chkAttributeById.getNop())&&chkAttributeByNop.getId().equals(chkAttributeById.getId()))){
                        attribute.setTaxId(taxId);
                        resp = taxManager.updateAttribute(attribute);
                        if(resp==1) sResp="NOP was successfully updated: " + nop;
                        else sResp="Failed to update NOP (" + resp + "): " + nop;
                    } else {
                        sResp="Failed to update NOP, there was same NOP existed: " + nop;
                    }
                }
                //Save value
                if(resp==1){
                    value.setAttributeId(taxManager.getAttribute(nop, null).getId());
                    value.setPpm(ppm);
                    value.setYear(year);
                    value.setUserInput(userSession.getUsername());
                    if(taxManager.getValue(value.getAttributeId(), year)==null){
                        taxManager.insertValue(value);
                        sResp+="<br/>Value was succcessfully saved: " + value.getPpm() + ":" + value.getYear();
                    } else {
                        taxManager.updateValue(value);
                        sResp+="<br/>Value was succcessfully updated: " + value.getPpm() + ":" + value.getYear();
                    }
                }
                //Save Attachment
                if(resp==1){
                    if(attachments!=null){
                        Integer insertAttachment = taxManager.insertAttachment(attributeId, attachments.get(0));
                        if(insertAttachment==1){
                            sResp+="<br/>Attachment was succcessfully saved: " + attachments.get(0).getFileName();
                        } else {
                            sResp+="<br/>Failed to save Attachment (" + insertAttachment + "): " + attachments.get(0).getFileName();
                        }
                    }
                }
                setResponse(sResp);
            } else {
                setResponse("Failed, please input with the correct format. The length of NOP should be 18 characters.");
            }
        } else {
            setResponse("Failed, please input with the correct format. The length of NPWP should be 15 characters.");
        }
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

    public String getDateInput() {
        return dateInput;
    }

    public void setDateInput(String dateInput) {
        this.dateInput = dateInput;
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

    public String getSubDistrictCode() {
        return subDistrictCode;
    }

    public void setSubDistrictCode(String subDistrictCode) {
        this.subDistrictCode = subDistrictCode;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Long attributeId) {
        this.attributeId = attributeId;
    }

}
