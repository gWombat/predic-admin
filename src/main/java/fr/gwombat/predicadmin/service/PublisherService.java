package fr.gwombat.predicadmin.service;

import java.util.List;

import fr.gwombat.predicadmin.model.entities.Congregation;
import fr.gwombat.predicadmin.model.entities.Publisher;

public interface PublisherService {

    Publisher getByIdentifier(String identifier);

    Publisher save(Publisher publisher);

    List<Publisher> getByCongregation(Congregation congregation);

    List<Publisher> getForCurrentCongregation();

    void delete(Publisher publisher);

    void deleteByIdentifier(String identifier);

}
