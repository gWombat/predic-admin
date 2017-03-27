package fr.gwombat.predicadmin.service;

import fr.gwombat.predicadmin.model.Publisher;

public interface PublisherService {

    Publisher getByIdentifier(String identifier);

    Publisher save(Publisher publisher);

}
