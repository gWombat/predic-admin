package fr.gwombat.predicadmin.web.transformer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import fr.gwombat.predicadmin.model.entities.Publisher;
import fr.gwombat.predicadmin.support.Privilege;
import fr.gwombat.predicadmin.web.vo.CongregationPublishersVO;
import fr.gwombat.predicadmin.web.vo.PublisherVO;
import fr.gwombat.predicadmin.web.vo.builder.CongregationPublishersVoBuilder;

@Component
public class CongregationPublishersTransformer implements ViewTransformer<List<Publisher>, CongregationPublishersVO> {

    private PublisherTransformer publisherTransformer;
    
    @Override
    public CongregationPublishersVO toViewObject(List<Publisher> entities) {
        final CongregationPublishersVoBuilder builder = CongregationPublishersVoBuilder.create();
        
        if(!CollectionUtils.isEmpty(entities)){
            for(Publisher publisher : entities){
                checkAndUpdatePrivilegesCount(builder, publisher);
                
                final PublisherVO publisherVo = publisherTransformer.toViewObject(publisher);
                builder.addPublisher(publisherVo);
            }
        }
        
        return builder.build();
    }
    
    private static void checkAndUpdatePrivilegesCount(final CongregationPublishersVoBuilder builder, Publisher publisher){
        if(publisher != null && publisher.getPrivilege() != null){
            if(publisher.getPrivilege() == Privilege.ELDER)
                builder.incrementEldersCount();
            else
                builder.incrementMinisterialServantsCount();
        }
    }

    @Autowired
    public void setPublisherTransformer(PublisherTransformer publisherTransformer) {
        this.publisherTransformer = publisherTransformer;
    }

}
