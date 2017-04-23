package fr.gwombat.predicadmin.listener;

import fr.gwombat.predicadmin.model.*;
import fr.gwombat.predicadmin.repository.CongregationRepository;
import fr.gwombat.predicadmin.repository.MeetingAttendanceRepository;
import fr.gwombat.predicadmin.repository.PublisherRepository;
import fr.gwombat.predicadmin.support.Gender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class SetupListener {

    private static final Logger logger = LoggerFactory.getLogger(SetupListener.class);

    private Environment                 env;
    private PublisherRepository         publisherRepository;
    private CongregationRepository      congregationRepository;
    private MeetingAttendanceRepository attendanceRepository;

    private boolean setUp = false;

    @Autowired
    public SetupListener(final Environment env, final PublisherRepository publisherRepository, final CongregationRepository congregationRepository) {
        this.env = env;
        this.publisherRepository = publisherRepository;
        this.congregationRepository = congregationRepository;
    }

    @EventListener
    public void handleRefreshContext(ContextRefreshedEvent event) {
        if(env.acceptsProfiles("default", "dev") && !setUp)
            setUp();
    }

    private void setUp() {

        Congregation congreg = congregationRepository.findByName("Verneuil-sur-Seine");
        if(congreg == null) {
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

        Address address = new Address();
        address.setCity("Paris");
        address.setCountry("FRANCE");
        address.setStreet1("1 rue de la gare");
        address.setZip("75002");
        publisher.setAddress(address);

        ContactDetail contactDetail = new ContactDetail();
        contactDetail.setEmail("gwombat.fake@email.com");
        contactDetail.setMobilePhone("0623456789");
        contactDetail.setPhone("0123456789");
        publisher.setContactDetail(contactDetail);

        publisher = publisherRepository.save(publisher);
        logger.debug("new publisher saved: " + publisher.toString());

        publisher = new Publisher();
        publisher.setName("Fabbi");
        publisher.setFirstName("Jérémie");
        publisher.setGender(Gender.MALE);
        publisher.setBirthDate(LocalDate.of(1992, 8, 21));
        publisher.setCongregation(congreg);

        address = new Address();
        address.setCity("Paris");
        address.setCountry("FRANCE");
        address.setStreet1("1 rue de l'église");
        address.setZip("75003");
        publisher.setAddress(address);

        contactDetail = new ContactDetail();
        contactDetail.setEmail("jeremie.fake@email.com");
        contactDetail.setMobilePhone("0632547698");
        contactDetail.setPhone("0132547698");
        publisher.setContactDetail(contactDetail);

        publisher = publisherRepository.save(publisher);

        initAttendance(congreg);

        setUp = true;
    }

    private void initAttendance(final Congregation congregation) {
        int bound = 500;

        final Map<LocalDate, MeetingAttendance> attendances = new HashMap<>(bound);

        int startYear = LocalDate.now().getYear();
        final List<Integer> years = new ArrayList<>(0);
        for(int i = startYear; i > startYear - 5; i--)
            years.add(i);

        for(int i = 0; i < bound; i++) {
            MeetingAttendance meetingAttendance = new MeetingAttendance();
            meetingAttendance.setCongregation(congregation);
            meetingAttendance.setAttendance(new Random().nextInt(120));

            int year = years.get(new Random().nextInt(years.size()));
            int month = Math.max(1, new Random().nextInt(13));
            final LocalDate date = LocalDate.of(year, month, Math.max(new Random().nextInt(25), 1));
            meetingAttendance.setDate(date);
            attendances.put(date, meetingAttendance);
        }

        attendanceRepository.save(attendances.entrySet()
                                             .stream()
                                             .map(Map.Entry::getValue)
                                             .filter(attendance -> attendance.getDate().isBefore(LocalDate.now()))
                                             .collect(Collectors.toList()));
    }

    @Autowired
    public void setAttendanceRepository(MeetingAttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }
}
