/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.icg.imap.tax.manager;

import id.co.icg.imap.tax.dao.model.Kpp;
import id.co.icg.imap.tax.dao.model.MasterArea;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Fauzi Marjalih
 */
public interface AreaManager {
    List<MasterArea>  getListMasterAreas(String provinceCode, String cityCode, String districtCode);
    MasterArea  getMasterArea(String areaCode);

    List<Map<String, String>>  getListProvinces();
    List<Map<String, String>>  getListCities(String provinceCode);
    List<Map<String, String>>  getListDistricts(String provinceCode, String cityCode);
    List<Map<String, String>>  getListSubDistricts(String provinceCode, String cityCode, String districtCode);
    Kpp  getKpp(Long kppId);
    List<Kpp>  getKpps();
    int removeKpp(Long kppId);
    int saveKpp(Kpp kpp);
    boolean updateKpp(Kpp kpp);
}
