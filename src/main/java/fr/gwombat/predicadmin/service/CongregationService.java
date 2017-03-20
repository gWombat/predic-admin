package fr.gwombat.predicadmin.service;

import java.util.List;

import fr.gwombat.predicadmin.model.Congregation;

public interface CongregationService {
    
    List<Congregation> getAllCongregations();
    
    Congregation getCongregationByName(String name);

}
