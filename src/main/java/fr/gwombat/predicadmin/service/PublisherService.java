package fr.gwombat.predicadmin.service;

import fr.gwombat.predicadmin.model.Congregation;
import fr.gwombat.predicadmin.model.Publisher;

import java.util.List;

public interface PublisherService {

    Publisher getByIdentifier(String identifier);

    Publisher save(Publisher publisher);

    List<Publisher> getByCongregation(Congregation congregation);

}
