package fr.gwombat.predicadmin.listener;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import fr.gwombat.predicadmin.model.Congregation;
import fr.gwombat.predicadmin.model.Publisher;
import fr.gwombat.predicadmin.repository.CongregationRepository;
import fr.gwombat.predicadmin.repository.PublisherRepository;
import fr.gwombat.predicadmin.support.Gender;

@Component
public class SetupListener {

    private static final Logger    logger = LoggerFactory.getLogger(SetupListener.class);

    private Environment            env;
    private PublisherRepository    publisherRepository;
    private CongregationRepository congregationRepository;
    private boolean                setUp  = false;

    @Autowired
    public SetupListener(final Environment env, final PublisherRepository publisherRepository, final CongregationRepository congregationRepository) {
        this.env = env;
        this.publisherRepository = publisherRepository;
        this.congregationRepository = congregationRepository;
    }

    @EventListener
    public void handleRefreshContext(ContextRefreshedEvent event) {
        if (env.acceptsProfiles("default", "dev") && !setUp)
            setUp();
    }

    private void setUp() {

        Congregation congreg = congregationRepository.findByName("Verneuil-sur-Seine");
        if (congreg == null) {
            congreg = new Congregation();
            congreg.setName("Verneuil-sur-Seine");
            congreg = congregationRepository.save(congreg);
            logger.debug("new congregation saved: " + congreg.toString());
        }

        Publisher publisher = new Publisher();
        publisher.setName("Fabbi");
        publisher.setFirstName("Guillaume");
        publisher.setGender(Gender.MALE);
        publisher.setBirthDate(LocalDate.of(1989, 5, 11));
        publisher.setBaptismDate(LocalDate.of(2008, 12, 6));
        publisher.setCongregation(congreg);
        publisher = publisherRepository.save(publisher);
        logger.debug("new publisher saved: " + publisher.toString());

        setUp = true;
    }

}
