package fr.gwombat.predicadmin.upload.excel;

import fr.gwombat.predicadmin.exception.upload.*;
import fr.gwombat.predicadmin.web.form.AddressForm;
import fr.gwombat.predicadmin.web.form.ContactDetailForm;
import fr.gwombat.predicadmin.web.form.PublisherForm;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ExcelFileReader {

    private static final String PHONE_NUMBER_ERROR_CODE = "data.upload.file.excel.invalid.data.phone";
    private static final String ZIP_ERROR_CODE          = "data.upload.file.excel.invalid.data.zip";
    private static final String INTERNAL_ERROR_CODE     = "validation.error.internal";
    private static final String DATE_FORMAT_CODE        = "format.date";

    private MessageSource messageSource;

    public List<PublisherForm> readFile(final ExcelFileUploadConfiguration fileConfiguration) throws UploadDataException {
        Objects.requireNonNull(fileConfiguration);

        boolean skipRow = fileConfiguration.isUseHeader();
        final List<PublisherForm> publishers = new ArrayList<>(0);
        try {
            final Workbook workbook = WorkbookFactory.create(fileConfiguration.getFile()
                                                                              .getInputStream());
            Sheet sheet;
            if(fileConfiguration.isUseActiveSheet())
                sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());
            else {
                sheet = workbook.getSheet(fileConfiguration.getSheetName());
                if(sheet == null) {
                    workbook.close();
                    throw new SheetNotFoundException(fileConfiguration.getSheetName());
                }
            }

            if(sheet != null) {
                final Iterator<Row> rowIterator = sheet.rowIterator();

                int rowCount = 0;
                while(rowIterator.hasNext()) {
                    final Row currentRow = rowIterator.next();
                    rowCount++;

                    if(!skipRow) {
                        final PublisherForm publisher = readRow(currentRow, workbook, fileConfiguration);
                        publishers.add(publisher);
                    } else if(rowCount > 0)
                        skipRow = false;
                }
            }
            workbook.close();
        } catch(EncryptedDocumentException e) {
            throw new ProtectedFileException(e);
        } catch(InvalidFormatException e) {
            throw new InvalidFileFormatException(e);
        } catch(IOException e) {
            throw new UploadDataException(INTERNAL_ERROR_CODE, e);
        }
        return publishers;
    }

    private PublisherForm readRow(final Row currentRow, final Workbook workbook, final ExcelFileUploadConfiguration fileConfiguration) throws DataMappingException, IOException {
        final PublisherForm publisher = new PublisherForm();
        ContactDetailForm contactDetail = null;
        AddressForm addressForm = null;
        final Iterator<Cell> cellIterator = currentRow.cellIterator();
        int cellsCount = 0;
        while(cellIterator.hasNext() && isMappingAvailable(cellsCount, fileConfiguration.getMappings())) {
            final Cell currentCell = cellIterator.next();
            cellsCount++;
            final UploadablePublisherFields entityFieldName = fileConfiguration.getEntityFieldForindex(currentCell.getColumnIndex());

            try {
                if(entityFieldName != null) {
                    switch(entityFieldName) {
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
                            break;
                        case EMAIL:
                            if(contactDetail == null)
                                contactDetail = new ContactDetailForm();
                            contactDetail.setEmail(currentCell.getStringCellValue());
                            break;
                        case PHONE:
                            if(contactDetail == null)
                                contactDetail = new ContactDetailForm();
                            contactDetail.setPhone(extractValueAsString(currentCell, PHONE_NUMBER_ERROR_CODE));
                            break;
                        case MOBILEPHONE:
                            if(contactDetail == null)
                                contactDetail = new ContactDetailForm();
                            contactDetail.setMobilePhone(extractValueAsString(currentCell, PHONE_NUMBER_ERROR_CODE));
                            break;
                        case STREET:
                            if(addressForm == null)
                                addressForm = new AddressForm();
                            addressForm.setStreet1(currentCell.getStringCellValue());
                            break;
                        case CITY:
                            if(addressForm == null)
                                addressForm = new AddressForm();
                            addressForm.setCity(currentCell.getStringCellValue());
                            break;
                        case ZIP:
                            if(addressForm == null)
                                addressForm = new AddressForm();
                            addressForm.setZip(extractValueAsString(currentCell, ZIP_ERROR_CODE));
                            break;
                        default:
                            break;
                    }
                }
            } catch(IllegalStateException e) {
                workbook.close();
                throw new DataMappingException(getCellValue(currentCell), e);
            }
        }

        publisher.setContactDetail(contactDetail);
        publisher.setAddress(addressForm);
        return publisher;
    }
    
    private static boolean isMappingAvailable(final int index, final List<ColumnMappingItem> items){
        if(CollectionUtils.isEmpty(items))
            return false;
        return index < items.size();
    }

    /**
     * Extract the cell value as a string if it is a String or a number. It
     * Throws an exception in other cases
     *
     * @param cell the cell to extract value
     * @return the value of the cell as String
     * @throws DataMappingException if the cell value is neither String nor number
     */
    private static String extractValueAsString(final Cell cell, final String errorMessageCode) throws DataMappingException {
        Objects.requireNonNull(cell);

        if(cell.getCellTypeEnum() == CellType.STRING)
            return StringUtils.remove(cell.getStringCellValue(), ' ');
        if(cell.getCellTypeEnum() == CellType.NUMERIC)
            return String.valueOf((int) cell.getNumericCellValue());

        throw new DataMappingException(errorMessageCode, new Object[]{getCellValue(cell)});
    }

    private String convertDate(final Date date) {
        if(date == null)
            return null;
        final String datePattern = messageSource.getMessage(DATE_FORMAT_CODE, null, LocaleContextHolder.getLocale());
        final SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
        return dateFormat.format(date);
    }

    private static String getCellValue(final Cell cell) {
        if(cell == null)
            return null;

        switch(cell.getCellTypeEnum()) {
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

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

}
