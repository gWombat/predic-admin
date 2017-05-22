package fr.gwombat.predicadmin.web.rest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.gwombat.predicadmin.exception.upload.UploadDataException;
import fr.gwombat.predicadmin.model.entities.Publisher;
import fr.gwombat.predicadmin.upload.excel.ExcelFileReader;
import fr.gwombat.predicadmin.upload.excel.ExcelFileUploadConfiguration;
import fr.gwombat.predicadmin.web.form.PublisherForm;
import fr.gwombat.predicadmin.web.transformer.PublisherTransformer;
import fr.gwombat.predicadmin.web.vo.PublisherVO;

/**
 * Created by gWombat on 17/05/2017.
 */
@RestController
@RequestMapping("/rest/publishers")
public class UploadPublishersResource {

    private PublisherTransformer publisherTransformer;
    private MessageSource        messageSource;

    @PostMapping("/upload")
    public UploadPublisherResult uploadPublishers(@ModelAttribute ExcelFileUploadConfiguration fileConfiguration) throws UploadDataException {
        final ExcelFileReader fileReader = new ExcelFileReader(messageSource);
        final List<PublisherForm> publishersToImport = fileReader.readFile(fileConfiguration);
        return new UploadPublisherResult(publishersToImport);
    }

    public class UploadPublisherResult {

        private final List<PublisherForm> publishersToImport;
        private final List<PublisherVO>   publishersData;

        public UploadPublisherResult(List<PublisherForm> publishers) {
            publishersToImport = publishers;
            publishersData = new ArrayList<>(0);
            if (!CollectionUtils.isEmpty(publishers)) {
                
                publishers.sort(Comparator.comparing(PublisherForm::getName).thenComparing(PublisherForm::getFirstName));
                
                for (PublisherForm formObject : publishers) {
                    final Publisher publisher = publisherTransformer.toEntity(formObject, null);
                    publishersData.add(publisherTransformer.toViewObject(publisher));
                }
            }
        }

        public List<PublisherForm> getPublishersToImport() {
            return publishersToImport;
        }

        public List<PublisherVO> getPublishersData() {
            return publishersData;
        }
    }

    @Autowired
    public void setPublisherTransformer(PublisherTransformer publisherTransformer) {
        this.publisherTransformer = publisherTransformer;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
