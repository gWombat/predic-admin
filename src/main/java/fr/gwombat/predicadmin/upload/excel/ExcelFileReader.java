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
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.Assert;

import fr.gwombat.predicadmin.exception.upload.DataMappingException;
import fr.gwombat.predicadmin.exception.upload.InvalidFileFormatException;
import fr.gwombat.predicadmin.exception.upload.ProtectedFileException;
import fr.gwombat.predicadmin.exception.upload.SheetNotFoundException;
import fr.gwombat.predicadmin.exception.upload.UploadDataException;
import fr.gwombat.predicadmin.web.form.PublisherForm;

public class ExcelFileReader {

    private static final String INTERNAL_ERROR_CODE = "validation.error.internal";
    private static final String DATE_FORMAT_CODE    = "format.date";

    private MessageSource       messageSource;

    public ExcelFileReader(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public List<PublisherForm> readFile(final ExcelFileUploadConfiguration fileConfiguration) throws UploadDataException {
        Assert.notNull(fileConfiguration, "The configuration file must not be null");

        boolean skipRow = fileConfiguration.isUseHeader();
        final List<PublisherForm> publishers = new ArrayList<>(0);
        try {
            final Workbook workbook = WorkbookFactory.create(fileConfiguration.getFile().getInputStream());
            Sheet sheet = null;
            if (fileConfiguration.isUseActiveSheet())
                sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());
            else {
                sheet = workbook.getSheet(fileConfiguration.getSheetName());
                if (sheet == null) {
                    workbook.close();
                    throw new SheetNotFoundException(fileConfiguration.getSheetName());
                }
            }

            if (sheet != null) {
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

                            try {
                                if (entityFieldName != null) {
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
                            } catch (IllegalStateException e) {
                                workbook.close();
                                throw new DataMappingException(getCellValue(currentCell), e);
                            }
                        }
                        publishers.add(publisher);
                    } else if (rowCount > 0)
                        skipRow = false;
                }
            }
            workbook.close();
        } catch (EncryptedDocumentException e) {
            throw new ProtectedFileException(e);
        } catch (InvalidFormatException e) {
            throw new InvalidFileFormatException(e);
        } catch (IOException e) {
            throw new UploadDataException(INTERNAL_ERROR_CODE, e);
        }
        return publishers;
    }

    private String convertDate(final Date date) {
        if (date == null)
            return null;
        final String datePattern = messageSource.getMessage(DATE_FORMAT_CODE, null, LocaleContextHolder.getLocale());
        final SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
        return dateFormat.format(date);
    }

    private static String getCellValue(final Cell cell) {
        if (cell == null)
            return null;

        switch (cell.getCellTypeEnum()) {
        case BLANK:
        case STRING:
            return cell.getStringCellValue();
        case BOOLEAN:
            return String.valueOf(cell.getBooleanCellValue());
        case NUMERIC:
            return String.valueOf(cell.getNumericCellValue());
        case FORMULA:
            return cell.getCellFormula();
        case ERROR:
            return String.valueOf(cell.getErrorCellValue());
        default:
            return null;
        }
    }

}
