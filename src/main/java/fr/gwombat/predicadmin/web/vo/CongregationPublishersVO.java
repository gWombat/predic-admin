package fr.gwombat.predicadmin.web.vo;

import java.util.List;

import fr.gwombat.predicadmin.web.vo.builder.CongregationPublishersVoBuilder;

public class CongregationPublishersVO {

    private final List<PublisherVO> publishers;
    private final int               eldersCount;
    private final int               ministerialServantsCount;
    private final int               publishersCount;

    public CongregationPublishersVO(final CongregationPublishersVoBuilder builder) {
        publishers = builder.getPublishers();
        eldersCount = builder.getNbElders();
        ministerialServantsCount = builder.getNbMinisterialServants();
        publishersCount = builder.getPublishersCount();
    }

    public List<PublisherVO> getPublishers() {
        return publishers;
    }

    public int getEldersCount() {
        return eldersCount;
    }

    public int getMinisterialServantsCount() {
        return ministerialServantsCount;
    }

    public int getPublishersCount() {
        return publishersCount;
    }

}
