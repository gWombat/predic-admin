package fr.gwombat.predicadmin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.gwombat.predicadmin.model.Publisher;
import fr.gwombat.predicadmin.repository.PublisherRepository;
import fr.gwombat.predicadmin.service.PublisherService;

@Service
public class PublisherServiceImpl implements PublisherService {

    private PublisherRepository publisherRepository;
    
    @Autowired
    public PublisherServiceImpl(final PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public Publisher getByIdentifier(String identifier) {
        return publisherRepository.findByIdentifier(identifier);
    }

}
