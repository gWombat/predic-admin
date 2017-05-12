package fr.gwombat.predicadmin.upload.excel;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileReader {
    
    public void readFile(){
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.getSheet("");
    }

}
