/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.icg.imap.tax.manager.impl;


import id.co.icg.imap.tax.dao.model.Kpp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import id.co.icg.imap.tax.manager.AreaManager;
import id.co.icg.imap.tax.web.BaseActionBeanContext;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DataAccessException;


@Service("areaManager")
public class AreaManagerImpl implements AreaManager {

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

    public List<Map<String, String>> getListProvinces() {
        String query;
        query  = 
            " SELECT DISTINCT province_code, province" +
            " FROM master_area ";
        return jdbcTemplate.query(query, new Object[]{}, new RowMapper() {
            @Override
            public HashMap<String, Object> mapRow(ResultSet rs, int i) throws SQLException {
                HashMap<String, Object> r = new HashMap<String, Object>();
                r.put("provinceCode",rs.getString("province_code"));
                r.put("province",rs.getString("province"));
                return r;
            }
        });
    }

    public List<Map<String, String>> getListCities(String provinceCode) {
        String query;
        query  = 
            " SELECT DISTINCT city_code, city" +
            " FROM master_area " +
            " WHERE province_code=? ";
        return jdbcTemplate.query(query, new Object[]{provinceCode}, new RowMapper() {
            @Override
            public HashMap<String, Object> mapRow(ResultSet rs, int i) throws SQLException {
                HashMap<String, Object> r = new HashMap<String, Object>();
                r.put("cityCode",rs.getString("city_code"));
                r.put("city",rs.getString("city"));
                return r;
            }
        });
    }

    public List<Map<String, String>> getListDistricts(String provinceCode, String cityCode) {
        String query;
        query  = 
            " SELECT DISTINCT district_code, district" +
            " FROM master_area " +
            " WHERE province_code=? AND city_code=?";
        return jdbcTemplate.query(query, new Object[]{provinceCode, cityCode}, new RowMapper() {
            @Override
            public HashMap<String, Object> mapRow(ResultSet rs, int i) throws SQLException {
                HashMap<String, Object> r = new HashMap<String, Object>();
                r.put("districtCode",rs.getString("district_code"));
                r.put("district",rs.getString("district"));
                return r;
            }
        });
    }

    public List<Map<String, String>> getListSubDistricts(String provinceCode, String cityCode, String districtCode) {
        String query;
        query  = 
            " SELECT DISTINCT sub_district_code, sub_district" +
            " FROM master_area " +
            " WHERE province_code=? AND city_code=? AND district_code=?";
        return jdbcTemplate.query(query, new Object[]{provinceCode, cityCode, districtCode}, new RowMapper() {
            @Override
            public HashMap<String, Object> mapRow(ResultSet rs, int i) throws SQLException {
                HashMap<String, Object> r = new HashMap<String, Object>();
                r.put("subDistrictCode",rs.getString("sub_district_code"));
                r.put("subDistrict",rs.getString("sub_district"));
                return r;
            }
        });
    }

    @Override
    public Kpp getKpp(Long kppId) {
        String query = "select * from master_kpp where id=?";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{kppId}, new RowMapper<Kpp>() {
                @Override
                public Kpp mapRow(ResultSet rs, int i) throws SQLException {
                    Kpp kpp = new Kpp();
                    kpp.setId(rs.getLong("id"));
                    kpp.setKpp(rs.getString("kpp"));
                    return kpp;
                }
            });
        } catch (DataAccessException e) {
            logger.info("kpp_not_found:" + kppId);
            return null;
        }
    }

    @Override
    public List<Kpp> getKpps() {
        String query = " SELECT id, kpp FROM master_kpp ";
        try {
            return jdbcTemplate.query(query, new Object[]{}, new RowMapper() {
                @Override
                public Kpp mapRow(ResultSet rs, int i) throws SQLException {
                    Kpp kpp = new Kpp();
                    kpp.setId(rs.getLong("id"));
                    kpp.setKpp(rs.getString("kpp"));
                    return kpp;
                }
            });
        } catch (DataAccessException e) {
            logger.error(e.toString());
            return null;
        }
    }

    @Override
    public int removeKpp(Long kppId) {
        String query;
        Kpp kpp = getKpp(kppId);
        if(kpp!=null){
            query = "DELETE FROM master_kpp WHERE id=?";
            int[] arg = {Types.BIGINT};
            try {
                jdbcTemplate.update(query, new Object[]{kpp.getId()}, arg);
                return 0;
            } catch (DataAccessException e){
                logger.error(e.toString());
                return 4;
            }
        } else return 1;
    }

    @Override
    public int saveKpp(Kpp kpp) {
        String query;
        if(kpp.getKpp()!=null){
            query = " INSERT INTO master_kpp(kpp) "
                    + " VALUES(?)";
            int[] arg = {Types.VARCHAR};
            try {
                jdbcTemplate.update(query, new Object[]{kpp.getKpp()}, arg);
                return 0;
            } catch (DataAccessException e){
                logger.error(e.toString());
                return 2;
            }
        } else return 1;
    }

    @Override
    public boolean updateKpp(Kpp kpp) {
        String query;
        if(getKpp(kpp.getId())!=null){
            query = "UPDATE master_kpp SET kpp=? WHERE id=?";
            int[] arg = {Types.VARCHAR, Types.BIGINT};
            try {
                jdbcTemplate.update(query, new Object[]{kpp.getKpp(), kpp.getId()}, arg);
                return true;
            } catch (DataAccessException e) {
                logger.error(e.toString());
                return false;
            }
        } else {
            return false;
        }
    }

}
