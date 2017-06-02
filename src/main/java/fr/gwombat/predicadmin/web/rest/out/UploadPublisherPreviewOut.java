package fr.gwombat.predicadmin.web.rest.out;

import java.util.List;

import fr.gwombat.predicadmin.web.form.PublisherForm;
import fr.gwombat.predicadmin.web.vo.PublisherVO;

public class UploadPublisherPreviewOut {

    private List<PublisherForm> publishersToImport;
    private List<PublisherVO>   publishersData;
    private long                newPublishersCount;
    private long                publishersCount;

    public UploadPublisherPreviewOut() {
    }

    public List<PublisherForm> getPublishersToImport() {
        return publishersToImport;
    }

    public List<PublisherVO> getPublishersData() {
        return publishersData;
    }

    public void setPublishersToImport(List<PublisherForm> publishersToImport) {
        this.publishersToImport = publishersToImport;
    }

    public void setPublishersData(List<PublisherVO> publishersData) {
        this.publishersData = publishersData;
    }

    public long getNewPublishersCount() {
        return newPublishersCount;
    }

    public void setNewPublishersCount(long newPublishersCount) {
        this.newPublishersCount = newPublishersCount;
    }

    public long getPublishersCount() {
        return publishersCount;
    }

    public void setPublishersCount(long publishersCount) {
        this.publishersCount = publishersCount;
    }
}
