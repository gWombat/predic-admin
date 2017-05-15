package fr.gwombat.predicadmin.upload.excel;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Iterator;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import fr.gwombat.predicadmin.model.entities.Publisher;

public class ExcelFileReader {

    private static Logger logger = LoggerFactory.getLogger(ExcelFileReader.class);

    public void readFile(final ExcelFileUploadConfiguration fileConfiguration) {
        Assert.notNull(fileConfiguration, "The file configuration must not be null");

        boolean skipRow = fileConfiguration.isUseHeader();

        //for (ColumnMappingItem item : fileConfiguration.getMappings())
        //    logger.debug(item.toString());

        try {
            final Workbook workbook = WorkbookFactory.create(fileConfiguration.getInputStream());
            final Sheet sheet = workbook.getSheet(fileConfiguration.getSheetName());
            final Iterator<Row> rowIterator = sheet.rowIterator();

            int rowCount = 0;

            while (rowIterator.hasNext()) {
                final Row currentRow = rowIterator.next();
                rowCount++;
                
                if (!skipRow) {
                    Publisher publisher = new Publisher();
                    final Iterator<Cell> cellIterator = currentRow.cellIterator();
                    while (cellIterator.hasNext()) {
                        final Cell currentCell = cellIterator.next();
                        String entityFieldName = fileConfiguration.getEntityFieldByindex(currentCell.getColumnIndex());
                        
                        
                        // TODO add try/catch(IllegalStateException)
                        // TODO replace string field name by enum
                        if(entityFieldName.equalsIgnoreCase("name"))
                            publisher.setName(currentCell.getStringCellValue());
                        else if(entityFieldName.equalsIgnoreCase("firstname"))
                            publisher.setFirstName(currentCell.getStringCellValue());
                        else if(entityFieldName.equalsIgnoreCase("birthdate"))
                            publisher.setBirthDate(Instant.ofEpochMilli(currentCell.getDateCellValue().getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
                            
                    }
                    logger.debug(publisher.toString());
                }
                else if(rowCount > 0)
                    skipRow = false;
                    
            }

            workbook.close();

        } catch (EncryptedDocumentException | InvalidFormatException | IOException e1) {
            logger.error(e1.getMessage(), e1);
        }
    }

}
