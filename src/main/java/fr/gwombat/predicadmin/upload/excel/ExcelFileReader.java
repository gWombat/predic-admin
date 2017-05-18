package fr.gwombat.predicadmin.upload.excel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.Assert;

import fr.gwombat.predicadmin.web.form.PublisherForm;

public class ExcelFileReader {
    
    private static final String DATE_FORMAT_CODE   = "format.date";
    
    private MessageSource messageSource;
    
    public ExcelFileReader(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private static Logger logger = LoggerFactory.getLogger(ExcelFileReader.class);

    public List<PublisherForm> readFile(final ExcelFileUploadConfiguration fileConfiguration) {
        Assert.notNull(fileConfiguration, "The configuration file must not be null");

        boolean skipRow = fileConfiguration.isUseHeader();
        final List<PublisherForm> publishers = new ArrayList<>(0);
        try {
            final Workbook workbook = WorkbookFactory.create(fileConfiguration.getFile().getInputStream());
            Sheet sheet = null;
            if(fileConfiguration.isUseActiveSheet())
                sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());
            else
                sheet = workbook.getSheet(fileConfiguration.getSheetName());
            
            final Iterator<Row> rowIterator = sheet.rowIterator();

            int rowCount = 0;
            while (rowIterator.hasNext()) {
                final Row currentRow = rowIterator.next();
                rowCount++;
                
                if (!skipRow) {
                    final PublisherForm publisher = new PublisherForm();
                    final Iterator<Cell> cellIterator = currentRow.cellIterator();
                    while (cellIterator.hasNext()) {
                        final Cell currentCell = cellIterator.next();
                        final UploadablePublisherFields entityFieldName = fileConfiguration.getEntityFieldForindex(currentCell.getColumnIndex());
                        
                        // TODO add try/catch(IllegalStateException)
                        if(entityFieldName != null){
                            switch (entityFieldName) {
                            case NAME:
                                publisher.setName(currentCell.getStringCellValue());
                                break;
                            case FIRSTNAME:
                                publisher.setFirstName(currentCell.getStringCellValue());
                                break;
                            case BIRTHDATE:
                                final Date birthDate = currentCell.getDateCellValue();
                                publisher.setBirthDate(convertDate(birthDate));
                                break;
                            case BAPTISMDATE:
                                final Date baptismDate = currentCell.getDateCellValue();
                                publisher.setBaptismDate(convertDate(baptismDate));
                            default:
                                break;
                            }
                        }
                            
                    }
                    publishers.add(publisher);
                }
                else if(rowCount > 0)
                    skipRow = false;
            }
            workbook.close();
        } catch (EncryptedDocumentException | InvalidFormatException | IOException e1) {
            logger.error(e1.getMessage(), e1);
        }
        return publishers;
    }
    
    private String convertDate(final Date date){
        if(date == null)
            return null;
        final String datePattern = messageSource.getMessage(DATE_FORMAT_CODE, null, LocaleContextHolder.getLocale());
        final SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
        return dateFormat.format(date);
    }

}
