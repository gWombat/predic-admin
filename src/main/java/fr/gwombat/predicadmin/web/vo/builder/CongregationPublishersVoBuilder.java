package fr.gwombat.predicadmin.web.vo.builder;

import java.util.ArrayList;
import java.util.List;

import fr.gwombat.predicadmin.web.vo.CongregationPublishersVO;
import fr.gwombat.predicadmin.web.vo.PublisherVO;

public class CongregationPublishersVoBuilder {

    private List<PublisherVO> publishers;
    private int               nbElders;
    private int               nbMinisterialServants;

    private CongregationPublishersVoBuilder() {
        publishers = new ArrayList<>(0);
    }

    public static CongregationPublishersVoBuilder create() {
        return new CongregationPublishersVoBuilder();
    }

    public CongregationPublishersVoBuilder addPublisher(final PublisherVO publisher) {
        if (publisher != null)
            publishers.add(publisher);
        return this;
    }
    
    public CongregationPublishersVoBuilder nbElders(final int nbElders){
        this.nbElders = nbElders;
        return this;
    }
    
    public CongregationPublishersVoBuilder incrementEldersCount(){
        this.nbElders++;
        return this;
    }
    
    public CongregationPublishersVoBuilder incrementMinisterialServantsCount(){
        this.nbMinisterialServants++;
        return this;
    }
    
    public CongregationPublishersVoBuilder nbMinisterialAssistants(final int nbMinisterialAssistants){
        this.nbMinisterialServants = nbMinisterialAssistants;
        return this;
    }
    
    public CongregationPublishersVO build(){
        return new CongregationPublishersVO(this);
    }

    public List<PublisherVO> getPublishers() {
        return publishers;
    }

    public int getNbElders() {
        return nbElders;
    }

    public int getNbMinisterialServants() {
        return nbMinisterialServants;
    }
    
    public int getPublishersCount(){
        return publishers.size();
    }

}
