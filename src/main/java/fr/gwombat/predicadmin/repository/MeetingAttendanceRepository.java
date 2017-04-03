package fr.gwombat.predicadmin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.gwombat.predicadmin.model.Congregation;
import fr.gwombat.predicadmin.model.MeetingAttendance;

@Repository
public interface MeetingAttendanceRepository extends JpaRepository<MeetingAttendance, Long> {
    
    List<MeetingAttendance> findByCongregation(Congregation congregation);

}
