/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.icg.imap.tax.manager.impl;


import id.co.icg.imap.tax.dao.model.Attachment;
import id.co.icg.imap.tax.dao.model.Value;
import id.co.icg.imap.tax.dao.model.Attribute;
import id.co.icg.imap.tax.dao.model.AttributeMap;
import id.co.icg.imap.tax.dao.model.TaxPerson;
import id.co.icg.imap.tax.dao.model.User;
import id.co.icg.imap.tax.manager.AreaManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import id.co.icg.imap.tax.web.BaseActionBeanContext;
import java.sql.Types;
import java.util.ArrayList;
import org.springframework.dao.EmptyResultDataAccessException;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import net.sourceforge.stripes.action.FileBean;
import id.co.icg.imap.tax.manager.TaxManager;


@Service("taxManager")
public class TaxManagerImpl implements TaxManager {

    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());

    private BaseActionBeanContext ctx;
    public BaseActionBeanContext getContext() {
        return this.ctx;
    }


    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Resource(name = "jdbcTemplateProd")
    private JdbcTemplate jdbcTemplateProd;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setJdbcTemplateProd(JdbcTemplate jdbcTemplateProd) {
        this.jdbcTemplateProd = jdbcTemplateProd;
    }

    @Resource(name = "areaManager")
    private AreaManager     areaManager;

    public void setAreaManager(AreaManager areaManager) {
        this.areaManager = areaManager;
    }

    public List<TaxPerson> getListTaxPersons(String npwp, String name) {
        String query, filter="";
        if(npwp!=null){
            filter += " npwp LIKE '%" + npwp + "%' ";
        }
        if(name!=null){
            filter += (filter!=""?"OR":"") + " name LIKE '%" + name + "%' ";
        }
        if(filter!=""){
            query  = 
                " SELECT * " +
                " FROM tax_person " +
                " WHERE " + filter;
            return jdbcTemplateProd.query(query, new Object[]{}, new RowMapper() {
                @Override
                public TaxPerson mapRow(ResultSet rs, int i) throws SQLException {
                        TaxPerson a = new TaxPerson();
                        a.setId(rs.getLong("id"));
                        a.setName(rs.getString("name"));
                        a.setNpwp(rs.getString("npwp"));
                        a.setDescription(rs.getString("description"));
                        a.setUserInput(rs.getString("user_input"));
                        a.setDateInput(new java.util.Date(rs.getTimestamp("date_input").getTime()));
                    return a;
                }
            });
        } else {
            return null;
        }
    }

    public List<Attribute> getListAttributes(Long taxId, String nop, String areaCode) {
        String query;
        query  = 
            " SELECT * " +
            " FROM attribute " +
            " WHERE tax_id=? OR nop LIKE ? OR area_code LIKE ?";
        return jdbcTemplateProd.query(query, new Object[]{taxId, "%" + nop + "%", "%" + areaCode + "%"}, new RowMapper() {
            @Override
            public Attribute mapRow(ResultSet rs, int i) throws SQLException {
                Attribute a = new Attribute();
                a.setId(rs.getLong("id"));
                a.setTaxId(rs.getLong("tax_id"));
                a.setNop(rs.getString("nop"));
                a.setAreaCode(rs.getString("area_code"));
                a.setLatitude(rs.getDouble("latitude"));
                a.setLongitude(rs.getDouble("longitude"));
                a.setStreet(rs.getString("street"));
                a.setStreetClass(rs.getString("street_class"));
                a.setZone(rs.getString("zone"));
                a.setSector(rs.getString("sector"));
                a.setMasterArea(areaManager.getMasterArea(rs.getString("area_code")));
                a.setRt(rs.getString("rt"));
                a.setRw(rs.getString("rw"));
                a.setAttribute1(rs.getString("attribute1"));
                a.setAttribute2(rs.getString("attribute2"));
                a.setAttribute3(rs.getString("attribute3"));
                a.setUserInput(rs.getString("user_input"));
                a.setDateInput(new java.util.Date(rs.getTimestamp("date_input").getTime()));

                List<Value> values = getListValues(a.getId());
                if(values==null) values = new ArrayList<Value>();
                a.setValues(values);
                return a;
            }
        });
    }

    public List<AttributeMap> getListAttributeMaps(String provinceCode, String cityCode, String districtCode, String subDistrictCode) {
        String query;
        query  = 
            " SELECT id, tax_id, nop, area_code, latitude, longitude " +
            " FROM attribute " +
            " WHERE area_code LIKE ?";
        return jdbcTemplateProd.query(query, new Object[]{("%" + (provinceCode!=null?provinceCode:"") + (cityCode!=null?cityCode:"") + (districtCode!=null?districtCode:"") + (subDistrictCode!=null?subDistrictCode:"") + "%")}, new RowMapper() {
            @Override
            public AttributeMap mapRow(ResultSet rs, int i) throws SQLException {
                AttributeMap a = new AttributeMap();
                a.setId(rs.getLong("id"));
                a.setTaxId(rs.getLong("tax_id"));
                a.setNop(rs.getString("nop"));
                a.setAreaCode(rs.getString("area_code"));
                a.setLatitude(rs.getDouble("latitude"));
                a.setLongitude(rs.getDouble("longitude"));
                return a;
            }
        });
    }

    public List<Attachment> getListAttachments(Long attributeId, String fileName) {
        String query;
        query  = 
            " SELECT * " +
            " FROM attachment " +
            " WHERE attribute_id=? or file_name LIKE ?";
        return jdbcTemplateProd.query(query, new Object[]{attributeId, "%" + fileName + "%"}, new RowMapper() {
            @Override
                public Attachment mapRow(ResultSet rs, int i) throws SQLException {
                    Attachment a = new Attachment();
                    a.setId(rs.getLong("id"));
                    a.setFileName(rs.getString("file_name"));
                    a.setFileNameOrigin(rs.getString("file_name_origin"));
                    a.setContentType(rs.getString("file_content_type"));
                    a.setFileSize(rs.getLong("file_size"));
                    a.setDateUpload(new java.util.Date(rs.getTimestamp("date_upload").getTime()));
                    return a;
            }
        });
    }

    public List<Value> getListValues(Long attributeId) {
        String query;
        query  = 
            " SELECT * " +
            " FROM value " +
            " WHERE attribute_id=? ";
        return jdbcTemplateProd.query(query, new Object[]{attributeId}, new RowMapper() {
            @Override
                public Value mapRow(ResultSet rs, int i) throws SQLException {
                    Value a = new Value();
                    a.setId(rs.getLong("id"));
                    a.setAttributeId(rs.getLong("attribute_id"));
                    a.setPpm(rs.getDouble("ppm"));
                    a.setYear(rs.getInt("year"));
                    a.setUserInput(rs.getString("user_input"));
                    a.setDateInput(new java.util.Date(rs.getTimestamp("date_input").getTime()));
                    return a;
            }
        });
    }

    public TaxPerson getTaxPerson(String npwp) {
        String query;
        query  = 
            " SELECT * " +
            " FROM tax_person" +
            " WHERE npwp = ? ";
        try {
            return jdbcTemplateProd.queryForObject(query, new Object[]{npwp}, new RowMapper<TaxPerson>() {
                @Override
                public TaxPerson mapRow(ResultSet rs, int i) throws SQLException {
                    TaxPerson a = new TaxPerson();
                    a.setId(rs.getLong("id"));
                    a.setName(rs.getString("name"));
                    a.setNpwp(rs.getString("npwp"));
                    a.setDescription(rs.getString("description"));
                    a.setUserInput(rs.getString("user_input"));
                    a.setDateInput(new java.util.Date(rs.getTimestamp("date_input").getTime()));
                    List<Attribute> attributes = getListAttributes(rs.getLong("id"), null, null);
                    if(attributes==null) attributes = new ArrayList<Attribute>();
                    a.setAttributes(attributes);
                    return a;
                }
            });
        } catch(EmptyResultDataAccessException e){
            return null;
        }
    }
    
    public Attachment getAttachment(Long attributeId) {
        String query;
        query  = 
            " SELECT * " +
            " FROM attachment " +
            " WHERE attribute_id = ? ";
        try {
            return jdbcTemplateProd.queryForObject(query, new Object[]{attributeId}, new RowMapper<Attachment>() {
                @Override
                public Attachment mapRow(ResultSet rs, int i) throws SQLException {
                    Attachment a = new Attachment();
                    a.setId(rs.getLong("id"));
                    a.setFileName(rs.getString("file_name"));
                    a.setFileNameOrigin(rs.getString("file_name_origin"));
                    a.setContentType(rs.getString("file_content_type"));
                    a.setFileSize(rs.getLong("file_size"));
                    a.setDateUpload(new java.util.Date(rs.getTimestamp("date_upload").getTime()));
                    return a;
                }
            });
        } catch(EmptyResultDataAccessException e){
            return null;
        }
    }
    
    public Value getValue(Long attributeId, Integer year) {
        String query;
        query  = 
            " SELECT * " +
            " FROM value " +
            " WHERE attribute_id = ? AND year=?";
        try {
            return jdbcTemplateProd.queryForObject(query, new Object[]{attributeId, year}, new RowMapper<Value>() {
                @Override
                public Value mapRow(ResultSet rs, int i) throws SQLException {
                    Value a = new Value();
                    a.setId(rs.getLong("id"));
                    a.setAttributeId(rs.getLong("attribute_id"));
                    a.setPpm(rs.getDouble("ppm"));
                    a.setYear(rs.getInt("year"));
                    a.setUserInput(rs.getString("user_input"));
                    a.setDateInput(new java.util.Date(rs.getTimestamp("date_input").getTime()));
                    return a;
                }
            });
        } catch(EmptyResultDataAccessException e){
            return null;
        }
    }
    
    
    public Attribute getAttribute(String nop, Long attributeId) {
        String query, filter="";
        if(nop!=null){
            filter += " nop='" + nop + "' ";
        }
        if(attributeId!=null){
            filter += (!"".equals(filter)?" OR":" ") + " id='" + attributeId + "' ";
        }
        if(!"".equals(filter)){
            query  = 
                " SELECT * " +
                " FROM attribute " +
                " WHERE 1 AND (" + filter + ")";
            try {
                return jdbcTemplateProd.queryForObject(query, new Object[]{}, new RowMapper<Attribute>() {
                    @Override
                    public Attribute mapRow(ResultSet rs, int i) throws SQLException {
                        Attribute a = new Attribute();
                        a.setId(rs.getLong("id"));
                        a.setTaxId(rs.getLong("tax_id"));
                        a.setNop(rs.getString("nop"));
                        a.setAreaCode(rs.getString("area_code"));
                        a.setStreet(rs.getString("street"));
                        a.setStreetClass(rs.getString("street_class"));
                        a.setZone(rs.getString("zone"));
                        a.setSector(rs.getString("sector"));
                        a.setMasterArea(areaManager.getMasterArea(rs.getString("area_code")));
                        a.setRt(rs.getString("rt"));
                        a.setRw(rs.getString("rw"));
                        a.setAttribute1(rs.getString("attribute1"));
                        a.setAttribute2(rs.getString("attribute2"));
                        a.setAttribute3(rs.getString("attribute3"));
                        a.setUserInput(rs.getString("user_input"));
                        a.setDateInput(new java.util.Date(rs.getTimestamp("date_input").getTime()));

                        List<Attachment> attachments = getListAttachments(a.getId(),null);
                        if(attachments==null) attachments = new ArrayList<Attachment>();
                        a.setAttachments(attachments);

                        List<Value> values = getListValues(a.getId());
                        if(values==null) values = new ArrayList<Value>();
                        a.setValues(values);
                        return a;
                    }
                });
            } catch(EmptyResultDataAccessException e){
                return null;
            }
        } else return null;
    }
    
    public Integer insertTaxPerson(TaxPerson taxPerson) {
        if(taxPerson!=null){
            String tmpNpwp = taxPerson.getNpwp().replaceAll("[^0-9]", "");
            if (tmpNpwp.length()==15){
                if(getTaxPerson(tmpNpwp)==null){
                    taxPerson.setNpwp(tmpNpwp);
                    String query = "INSERT INTO tax_person("
                            + "name, npwp, description, user_input) "
                            + "VALUES(?, ?, ?, ?)";
                    int[] arg = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};
                    return jdbcTemplateProd.update(query, new Object[]{
                        taxPerson.getName(), tmpNpwp, 
                        taxPerson.getDescription(), taxPerson.getUserInput()}, arg);
                } else {
                    logger.info(taxPerson.getUserInput()+ ":EXISTED_NPWP:" + taxPerson.getNpwp());
                    return 97;
                }
            } else {
                logger.info(taxPerson.getUserInput()+ ":INVALID_NPWP:" + taxPerson.getNpwp());
                return 98;
            }
        } else return 99;
    }

    public Integer insertAttribute(Attribute attribute) {
        if(attribute!=null){
            String tmpNop = attribute.getNop().replaceAll("[^0-9]", "");
            if (tmpNop.length()==18){
                if(getAttribute(tmpNop, null)==null){
                    attribute.setNop(tmpNop);
                    String query = "INSERT INTO attribute("
                            + "tax_id, nop, area_code, latitude, longitude, "
                            + "street, street_class, zone, sector, rt, rw, "
                            + "attribute1, attribute2, attribute3, user_input) "
                            + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    int[] arg = {   Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.DOUBLE, Types.DOUBLE,
                                    Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, 
                                    Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR };
                    return jdbcTemplateProd.update(query, new Object[]{
                        attribute.getTaxId(), tmpNop, attribute.getAreaCode(), attribute.getLatitude(), attribute.getLongitude(),
                        attribute.getStreet(), attribute.getStreetClass(), attribute.getZone(), attribute.getZone(), attribute.getRt(), attribute.getRw(), 
                        attribute.getAttribute1(), attribute.getAttribute2(), attribute.getAttribute3(), attribute.getUserInput()}, arg);
                } else {
                    logger.info(attribute.getUserInput()+ ":EXISTED_NOP:" + attribute.getNop());
                    return 97;
                }
            } else {
                logger.info(attribute.getUserInput()+ ":INVALID_NOP:" + attribute.getNop());
                return 98;
            }
        } else return 99;
    }

    public Integer updateAttribute(Attribute attribute) {
        if(attribute!=null){
            String tmpNop = attribute.getNop().replaceAll("[^0-9]", "");
            if (tmpNop.length()==18){
                if(getAttribute(tmpNop, attribute.getId())!=null){
                    attribute.setNop(tmpNop);
                    String query = "UPDATE attribute SET "
                            + "nop=?, area_code=?, latitude=?, longitude=?, "
                            + "street=?, street_class=?, zone=?, sector=?, rt=?, rw=?, "
                            + "attribute1=?, attribute2=?, attribute3=?, user_input=?, date_input=? "
                            + "WHERE tax_id=? AND id=?";
                    int[] arg = {   Types.VARCHAR, Types.VARCHAR, Types.DOUBLE, Types.DOUBLE,
                                    Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, 
                                    Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, 
                                    Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.BIGINT, Types.BIGINT};
                    return jdbcTemplateProd.update(query, new Object[]{
                        tmpNop, attribute.getAreaCode(), attribute.getLatitude(), attribute.getLongitude(),
                        attribute.getStreet(), attribute.getStreetClass(), attribute.getZone(), attribute.getSector(),
                        attribute.getRt(),attribute.getRw(), attribute.getAttribute1(), attribute.getAttribute2(), 
                        attribute.getAttribute3(), attribute.getUserInput(), new Date(), attribute.getTaxId(), attribute.getId()}, arg);
                } else {
                    logger.info(attribute.getUserInput()+ ":NOP_NOT_EXIST:" + attribute.getNop());
                    return 97;
                }
            } else {
                logger.info(attribute.getUserInput()+ ":INVALID_NOP:" + attribute.getNop());
                return 98;
            }
        } else return 99;
    }

    public Integer insertValue(Value value) {
        if(value!=null){
            if(getValue(value.getAttributeId(), value.getYear())==null){
                String query = "INSERT INTO value("
                        + "attribute_id, ppm, year, user_input) "
                        + "VALUES(?, ?, ?, ?)";
                int[] arg = {   Types.BIGINT, Types.DOUBLE, Types.INTEGER, Types.VARCHAR };
                return jdbcTemplateProd.update(query, new Object[]{
                    value.getAttributeId(), value.getPpm(), value.getYear(), value.getUserInput()}, arg);
            } else {
                logger.info(value.getUserInput()+ ":EXISTED_VALUE:" + value.getAttributeId() + ":" + value.getYear());
                return 98;
            }
        } else return 99;
    }

    public Integer updateValue(Value value) {
        if(value!=null){
            if(getValue(value.getAttributeId(), value.getYear())!=null){
                String query = "UPDATE value SET "
                        + "ppm=?, user_input=? "
                        + "WHERE attribute_id=? AND year=?";
                int[] arg = {Types.DOUBLE, Types.VARCHAR, Types.BIGINT, Types.INTEGER};
                return jdbcTemplateProd.update(query, new Object[]{
                    value.getPpm(), value.getUserInput(), value.getAttributeId(), value.getYear()}, arg);
            } else {
                logger.info(value.getUserInput()+ ":VALUE_NOT_EXISTED:" + value.getAttributeId() + ":" + value.getYear());
                return 98;
            }
        } else return 99;
    }

    public Integer insertAttachment(Long attributeId, FileBean fileBean) {
        if(fileBean!=null&&attributeId!=null){
            User userSession = (User) getContext().getSession("SESSION_USER");
            try {
                Date date = new Date();
                Attachment attachment = new Attachment();
                attachment.setAttributeId(attributeId);
                attachment.setFileName(date.getTime() + "_" + fileBean.getFileName());
                attachment.setFileNameOrigin(fileBean.getFileName());
                attachment.setContentType(fileBean.getContentType());
                attachment.setFileSize(fileBean.getSize());
                attachment.setUserUpload(userSession.getUsername());
                attachment.setDateUpload(date);
                
                String uploadDir = System.getProperty("catalina.base") + "/uploads";
                if (!new File(uploadDir).exists()) {
                    new File(uploadDir).mkdir();
                }
                File file = new File(uploadDir, attachment.getFileName());
                fileBean.save(file);
                
                String query = "INSERT INTO attachment("
                        + "attribute_id, file_name, file_name_origin, "
                        + "content_type, file_size, user_upload, date_upload) "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?)";
                int[] arg = {Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BIGINT, Types.VARCHAR, Types.TIMESTAMP};
                return jdbcTemplateProd.update(query, new Object[]{
                    attachment.getAttributeId(), attachment.getFileName(), attachment.getFileNameOrigin(),
                    attachment.getContentType(), attachment.getFileSize(), attachment.getUserUpload(), attachment.getDateUpload()}, arg);

            } catch (IOException ex) {
                logger.info(userSession.getUsername()+ ":FILE_SAVE_FAILED:" + fileBean.getFileName());
                return 98;
            }
        } else return 99;
    }

}
