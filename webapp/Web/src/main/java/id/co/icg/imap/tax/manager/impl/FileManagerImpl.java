/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.icg.imap.tax.manager.impl;

import id.co.icg.imap.tax.function.FileUtil;
import org.springframework.stereotype.Service;
import id.co.icg.imap.tax.manager.FileManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


@Service("fileManager")
public class FileManagerImpl implements FileManager {

    private final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());

    public FileUtil generateExcel(List<Map<String, Object>> data, String fileName) {
        final FileUtil fileUtil = new FileUtil();
        fileUtil.setName(fileName);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Sheet1");

        int rowNum = 0;
        int colNum = 0;
        Map<String, Object> m = data.get(0);
        Row r = sheet.createRow(rowNum++);
        for (Map.Entry<String, Object> entry : m.entrySet()) {
            String key=entry.getKey();
            Cell cell = r.createCell(colNum++);
            cell.setCellValue(key);
        }
        
        for (Map<String, Object> map : data) {
            Row row = sheet.createRow(rowNum++);
            colNum = 0;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                Object value=entry.getValue();
                Cell cell = row.createCell(colNum++);
                if (value instanceof String) {
                    cell.setCellValue((String) value);
                } else if (value instanceof Integer) {
                    cell.setCellValue((Integer) value);
                } else if (value instanceof Long) {
                    cell.setCellValue((Long) value);
                } else if (value instanceof Double) {
                    cell.setCellValue((Double) value);
                }
            }
        }
        try {
            workbook.write(out);
            workbook.close();
        } catch (IOException ex) {
            logger.error(ex);
        }
        fileUtil.setBytes(out.toByteArray());
        return fileUtil;
    }

}
