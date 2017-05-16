package fr.gwombat.predicadmin.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.gwombat.predicadmin.web.vuejs.ImportPublishersModelData;

@RestController
@RequestMapping("/rest/modeldata/publishers")
public class ImportPublishersResource {

    private MessageSource messageSource;

    @GetMapping
    public ImportPublishersModelData getVueJsData() {
        ImportPublishersModelData modelData = new ImportPublishersModelData(messageSource);
        return modelData;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

}
