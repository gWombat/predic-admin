package fr.gwombat.predicadmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.gwombat.predicadmin.model.Publisher;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    Publisher findByIdentifier(String identifier);
    
}
