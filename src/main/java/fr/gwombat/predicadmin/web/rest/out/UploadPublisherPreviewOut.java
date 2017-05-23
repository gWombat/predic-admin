package fr.gwombat.predicadmin.web.rest.out;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import fr.gwombat.predicadmin.model.entities.Publisher;
import fr.gwombat.predicadmin.web.form.PublisherForm;
import fr.gwombat.predicadmin.web.transformer.PublisherTransformer;
import fr.gwombat.predicadmin.web.vo.PublisherVO;

@Component
public class UploadPublisherPreviewOut {

    private PublisherTransformer      publisherTransformer;

    private final List<PublisherForm> publishersToImport;
    private final List<PublisherVO>   publishersData;
    private final int                 publishersCount;

    public UploadPublisherPreviewOut(List<PublisherForm> publishers) {
        publishersToImport = publishers;
        publishersData = new ArrayList<>(0);
        int count = 0;
        if (!CollectionUtils.isEmpty(publishers)) {
            publishers.sort(Comparator.comparing(PublisherForm::getName).thenComparing(PublisherForm::getFirstName));

            for (PublisherForm formObject : publishers) {
                final Publisher publisher = publisherTransformer.toEntity(formObject, null);
                publishersData.add(publisherTransformer.toViewObject(publisher));
            }

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

    @Autowired
    public void setPublisherTransformer(PublisherTransformer publisherTransformer) {
        this.publisherTransformer = publisherTransformer;
    }
}
