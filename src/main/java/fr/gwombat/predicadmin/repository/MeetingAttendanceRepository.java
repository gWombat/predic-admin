package fr.gwombat.predicadmin.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.gwombat.predicadmin.model.entities.Congregation;
import fr.gwombat.predicadmin.model.entities.MeetingAttendance;

@Repository
public interface MeetingAttendanceRepository extends JpaRepository<MeetingAttendance, Long> {
    
    MeetingAttendance findByIdentifier(String identifier);
    
    List<MeetingAttendance> findByCongregationOrderByDateAsc(Congregation congregation);
    
    List<MeetingAttendance> findByCongregationAndDateBetweenOrderByDateAsc(Congregation congregation, LocalDate startDate, LocalDate endDate);

}
