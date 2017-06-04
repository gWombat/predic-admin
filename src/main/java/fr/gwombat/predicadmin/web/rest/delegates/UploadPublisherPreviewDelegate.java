package fr.gwombat.predicadmin.web.rest.delegates;

import fr.gwombat.predicadmin.exception.upload.UploadDataException;
import fr.gwombat.predicadmin.model.entities.Publisher;
import fr.gwombat.predicadmin.service.PublisherService;
import fr.gwombat.predicadmin.upload.excel.ExcelFileReader;
import fr.gwombat.predicadmin.upload.excel.ExcelFileUploadConfiguration;
import fr.gwombat.predicadmin.web.form.PublisherForm;
import fr.gwombat.predicadmin.web.rest.out.UploadPublisherPreviewOut;
import fr.gwombat.predicadmin.web.transformer.PublisherTransformer;
import fr.gwombat.predicadmin.web.vo.PublisherVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UploadPublisherPreviewDelegate {

    private PublisherService     publisherService;
    private PublisherTransformer publisherTransformer;
    private ExcelFileReader      fileReader;

    public UploadPublisherPreviewOut process(final ExcelFileUploadConfiguration fileConfiguration) throws UploadDataException {

        final List<PublisherForm> importResult = fileReader.readFile(fileConfiguration);

        final UploadPublisherPreviewOut out = new UploadPublisherPreviewOut();
        out.setPublishersToImport(importResult);

        final List<PublisherVO> publishersVo = convertPublishers(importResult);
        out.setPublishersData(publishersVo);

        if(publishersVo != null) {
            final long newPublishersCount = publishersVo.stream()
                                                        .filter(PublisherVO::isNewPublisher)
                                                        .count();
            final long publishersCount = publishersVo.size();

            out.setNewPublishersCount(newPublishersCount);
            out.setPublishersCount(publishersCount);
        }

        return out;
    }

    private List<PublisherVO> convertPublishers(final List<PublisherForm> publishers) {
        if(!CollectionUtils.isEmpty(publishers)) {
            publishers.sort(Comparator.comparing(PublisherForm::getName)
                                      .thenComparing(PublisherForm::getFirstName));

            final List<String> existingNames = getAndComputeExistingPublishersNames();
            return publishers.stream()
                             .map(p -> processPublisher(p, existingNames))
                             .collect(Collectors.toList());
        }
        return null;
    }

    private PublisherVO processPublisher(final PublisherForm publisherForm, final List<String> existingNames) {
        final PublisherVO publisherVo = publisherTransformer.toViewObject(publisherForm);
        final String publisherName = parsePublisherName(publisherVo.getFullName());
        if(existingNames != null)
            publisherVo.setNewPublisher(!existingNames.contains(publisherName));
        return publisherVo;
    }

    private List<String> getAndComputeExistingPublishersNames() {
        final List<Publisher> allPublishers = publisherService.getForCurrentCongregation();
        if(allPublishers != null) {
            return allPublishers.stream()
                                .map(publisher -> parsePublisherName(publisher.getFullName()))
                                .collect(Collectors.toList());
        }
        return null;
    }

    private static String parsePublisherName(final String fullName) {
        if(fullName == null)
            return null;
        return fullName.trim()
                       .replaceAll("[-\\s]", "")
                       .toLowerCase();
    }

    @Autowired
    public void setPublisherService(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @Autowired
    public void setPublisherTransformer(PublisherTransformer publisherTransformer) {
        this.publisherTransformer = publisherTransformer;
    }

    @Autowired
    public void setFileReader(ExcelFileReader fileReader) {
        this.fileReader = fileReader;
    }

}
