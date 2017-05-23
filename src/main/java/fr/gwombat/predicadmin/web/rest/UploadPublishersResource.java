package fr.gwombat.predicadmin.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.gwombat.predicadmin.exception.upload.UploadDataException;
import fr.gwombat.predicadmin.upload.excel.ExcelFileReader;
import fr.gwombat.predicadmin.upload.excel.ExcelFileUploadConfiguration;
import fr.gwombat.predicadmin.web.form.PublisherForm;
import fr.gwombat.predicadmin.web.rest.out.UploadPublisherPreviewOut;
import fr.gwombat.predicadmin.web.vuejs.ImportPublishersModelData;

/**
 * Created by gWombat on 17/05/2017.
 */
@RestController
@RequestMapping("/rest/publishers")
public class UploadPublishersResource {

    private MessageSource messageSource;

    @GetMapping("/modeldata")
    public ImportPublishersModelData getVueJsData() {
        final ImportPublishersModelData modelData = new ImportPublishersModelData(messageSource);
        return modelData;
    }

    @PostMapping("/upload")
    public UploadPublisherPreviewOut uploadPublishers(@ModelAttribute ExcelFileUploadConfiguration fileConfiguration) throws UploadDataException {
        final ExcelFileReader fileReader = new ExcelFileReader(messageSource);
        final List<PublisherForm> publishersToImport = fileReader.readFile(fileConfiguration);
        return new UploadPublisherPreviewOut(publishersToImport);
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
