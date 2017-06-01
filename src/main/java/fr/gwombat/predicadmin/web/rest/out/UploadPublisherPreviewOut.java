package fr.gwombat.predicadmin.web.rest.out;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import fr.gwombat.predicadmin.web.form.PublisherForm;
import fr.gwombat.predicadmin.web.transformer.PublisherTransformer;
import fr.gwombat.predicadmin.web.vo.PublisherVO;

public class UploadPublisherPreviewOut {

    private final List<PublisherForm> publishersToImport;
    private List<PublisherVO>         publishersData = null;
    private final int                 publishersCount;

    public UploadPublisherPreviewOut(List<PublisherForm> publishers, final PublisherTransformer publisherTransformer) {
        publishersToImport = publishers;

        int count = 0;
        if (!CollectionUtils.isEmpty(publishers)) {
            publishers.sort(Comparator.comparing(PublisherForm::getName)
                    .thenComparing(PublisherForm::getFirstName));

            publishersData = publishers.stream()
                    .map(p -> publisherTransformer.toEntity(p, null))
                    .map(p -> publisherTransformer.toViewObject(p))
                    .collect(Collectors.toList());

            count = publishers.size();
        }
        publishersCount = count;
    }

    public List<PublisherForm> getPublishersToImport() {
        return publishersToImport;
    }

    public List<PublisherVO> getPublishersData() {
        return publishersData;
    }

    public int getPublishersCount() {
        return publishersCount;
    }
}
