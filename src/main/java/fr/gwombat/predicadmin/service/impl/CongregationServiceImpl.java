package fr.gwombat.predicadmin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.gwombat.predicadmin.model.Congregation;
import fr.gwombat.predicadmin.repository.CongregationRepository;
import fr.gwombat.predicadmin.service.CongregationService;

@Service
@Transactional
public class CongregationServiceImpl implements CongregationService {

    private final CongregationRepository congregationRepository;

    @Autowired
    public CongregationServiceImpl(CongregationRepository congregationRepository) {
        this.congregationRepository = congregationRepository;
    }

    @Override
    public List<Congregation> getAllCongregations() {
        return congregationRepository.findAll();
    }
    
    @Override
    public Congregation getCurrentCongregation() {
        return getAllCongregations().get(0);
    }

    @Override
    public Congregation getCongregationByName(final String name) {
        return congregationRepository.findByName(name);
    }

}
