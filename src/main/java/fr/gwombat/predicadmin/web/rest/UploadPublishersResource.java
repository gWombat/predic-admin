package fr.gwombat.predicadmin.web.rest;

import fr.gwombat.predicadmin.upload.excel.ExcelFileReader;
import fr.gwombat.predicadmin.upload.excel.ExcelFileUploadConfiguration;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Guillaume Fabbi on 17/05/2017.
 */
@RestController
@RequestMapping("/rest/publishers")
public class UploadPublishersResource {

    @PostMapping("/upload")
    public String uploadPublishers(@ModelAttribute ExcelFileUploadConfiguration fileConfiguration){
        /*logger.debug("Uploading file: " + file.getName());
        logger.debug("Uploading file: " + file.getContentType());
        logger.debug("Uploading file: " + file.getOriginalFilename());
        logger.debug("Uploading file: " + file.getSize());

        ExcelFileUploadConfiguration fileConfiguration = new ExcelFileUploadConfiguration();
        try {
            // TODO export to form data
            fileConfiguration.setInputStream(file.getInputStream());
            fileConfiguration.setSheetName("Feuille1");
            fileConfiguration.setUseHeader(true);
            fileConfiguration.addMappingItem(new ColumnMappingItem("D", UploadablePublisherFields.NAME));
            fileConfiguration.addMappingItem(new ColumnMappingItem("E", UploadablePublisherFields.FIRSTNAME));
            fileConfiguration.addMappingItem(new ColumnMappingItem("F", UploadablePublisherFields.BIRTHDATE));
            */
        ExcelFileReader fileReader = new ExcelFileReader();
        fileReader.readFile(fileConfiguration);
        /*} catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/

        return "redirect:/publishers";
    }
}
