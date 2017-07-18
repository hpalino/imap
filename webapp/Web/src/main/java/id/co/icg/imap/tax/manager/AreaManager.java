/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.icg.imap.tax.manager;

import id.co.icg.imap.tax.dao.model.Kpp;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Fauzi Marjalih
 */
public interface AreaManager {
    List<Map<String, String>>  getListProvinces();
    List<Map<String, String>>  getListCities(String provinceCode);
    List<Map<String, String>>  getListDistricts(String provinceCode, String cityCode);
    List<Map<String, String>>  getListSubDistricts(String provinceCode, String cityCode, String districtCode);
    Kpp  getKpp(Long kppId);
    List<Kpp>  getKpps();
}
