package fr.gwombat.predicadmin.upload.excel;

import java.io.IOException;
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
import fr.gwombat.predicadmin.support.util.DateConversionUtils;

public class ExcelFileReader {

    private static Logger logger = LoggerFactory.getLogger(ExcelFileReader.class);

    public void readFile(final ExcelFileUploadConfiguration fileConfiguration) {
        Assert.notNull(fileConfiguration, "The file configuration must not be null");

        boolean skipRow = fileConfiguration.isUseHeader();

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
                        final UploadablePublisherFields entityFieldName = fileConfiguration.getEntityFieldForindex(currentCell.getColumnIndex());
                        
                        // TODO add try/catch(IllegalStateException)
                        switch (entityFieldName) {
                        case NAME:
                            publisher.setName(currentCell.getStringCellValue());
                            break;
                        case FIRSTNAME:
                            publisher.setFirstName(currentCell.getStringCellValue());
                            break;
                        case BIRTHDATE:
                            publisher.setBirthDate(DateConversionUtils.asLocalDate(currentCell.getDateCellValue()));
                            break;
                        default:
                            break;
                        }
                            
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
