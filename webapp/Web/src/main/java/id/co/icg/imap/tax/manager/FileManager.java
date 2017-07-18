/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.icg.imap.tax.manager;

import id.co.icg.imap.tax.function.FileUtil;
import java.util.List;
import java.util.Map;


/**
 *
 * @author Fauzi Marjalih
 */
public interface FileManager {
    public FileUtil generateExcel(List<Map<String, Object>> data, String fileName);
}
