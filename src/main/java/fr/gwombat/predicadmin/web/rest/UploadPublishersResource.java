package fr.gwombat.predicadmin.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.gwombat.predicadmin.exception.upload.UploadDataException;
import fr.gwombat.predicadmin.upload.excel.ExcelFileUploadConfiguration;
import fr.gwombat.predicadmin.web.rest.delegates.UploadPublisherPreviewDelegate;
import fr.gwombat.predicadmin.web.rest.out.UploadPublisherPreviewOut;
import fr.gwombat.predicadmin.web.vuejs.ImportPublishersModelData;

/**
 * Created by gWombat on 17/05/2017.
 */
@RestController
@RequestMapping("/rest/publishers")
public class UploadPublishersResource {

    private MessageSource                  messageSource;
    private UploadPublisherPreviewDelegate uploadDelegate;

    @GetMapping("/modeldata")
    public ImportPublishersModelData getVueJsData() {
        final ImportPublishersModelData modelData = new ImportPublishersModelData(messageSource);
        return modelData;
    }

    @PostMapping("/upload/preview")
    public UploadPublisherPreviewOut uploadPublishers(@ModelAttribute ExcelFileUploadConfiguration fileConfiguration) throws UploadDataException {
        final UploadPublisherPreviewOut out = uploadDelegate.process(fileConfiguration);
        return out;
    }
    
    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    public void setUploadDelegate(UploadPublisherPreviewDelegate uploadDelegate) {
        this.uploadDelegate = uploadDelegate;
    }
}
