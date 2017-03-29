package fr.gwombat.predicadmin.repository;

import fr.gwombat.predicadmin.model.Congregation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.gwombat.predicadmin.model.Publisher;

import java.util.List;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    Publisher findByIdentifier(String identifier);

    List<Publisher> findByCongregation(Congregation congregation);
    
}
