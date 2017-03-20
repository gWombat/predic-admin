package fr.gwombat.predicadmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.gwombat.predicadmin.model.Congregation;

@Repository
public interface CongregationRepository extends JpaRepository<Congregation, Long> {
    
    Congregation findByName(String name);

}
