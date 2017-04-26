package fr.gwombat.predicadmin.service;

import java.util.List;

import fr.gwombat.predicadmin.model.entities.Congregation;

public interface CongregationService {
    
    List<Congregation> getAllCongregations();
    
    Congregation getCurrentCongregation();
    
    Congregation getCongregationByName(String name);

}
