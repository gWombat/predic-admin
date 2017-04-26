package fr.gwombat.predicadmin.service.impl;

import fr.gwombat.predicadmin.model.entities.Congregation;
import fr.gwombat.predicadmin.model.entities.Publisher;
import fr.gwombat.predicadmin.repository.PublisherRepository;
import fr.gwombat.predicadmin.service.PublisherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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

    @Override
    public Publisher save(final Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    @Override
    public List<Publisher> getByCongregation(final Congregation congregation) {
        return publisherRepository.findByCongregation(congregation);
    }

    @Override
    public void delete(final Publisher publisher) {
        publisherRepository.delete(publisher);
    }

    @Override
    public void deleteByIdentifier(String identifier) {
        final Publisher publisher = getByIdentifier(identifier);
        if(publisher != null)
            delete(publisher);
    }

}
