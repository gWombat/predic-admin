package fr.gwombat.predicadmin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.gwombat.predicadmin.model.entities.Congregation;
import fr.gwombat.predicadmin.model.entities.Publisher;
import fr.gwombat.predicadmin.repository.PublisherRepository;
import fr.gwombat.predicadmin.service.CongregationService;
import fr.gwombat.predicadmin.service.PublisherService;

@Service
@Transactional
public class PublisherServiceImpl implements PublisherService {

    private PublisherRepository publisherRepository;
    private CongregationService congregationService;

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
    public List<Publisher> getForCurrentCongregation() {
        return getByCongregation(congregationService.getCurrentCongregation());
    }

    @Override
    public void delete(final Publisher publisher) {
        publisherRepository.delete(publisher);
    }

    @Override
    public void deleteByIdentifier(String identifier) {
        final Publisher publisher = getByIdentifier(identifier);
        if (publisher != null)
            delete(publisher);
    }

    @Autowired
    public void setCongregationService(CongregationService congregationService) {
        this.congregationService = congregationService;
    }

    @Autowired
    public void setPublisherRepository(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }
}
