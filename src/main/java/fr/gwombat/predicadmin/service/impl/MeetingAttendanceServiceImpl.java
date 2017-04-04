package fr.gwombat.predicadmin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.gwombat.predicadmin.model.Congregation;
import fr.gwombat.predicadmin.model.MeetingAttendance;
import fr.gwombat.predicadmin.repository.MeetingAttendanceRepository;
import fr.gwombat.predicadmin.service.MeetingAttendanceService;

@Service
@Transactional
public class MeetingAttendanceServiceImpl implements MeetingAttendanceService {

    private MeetingAttendanceRepository attendanceRepository;
    
    @Autowired
    public MeetingAttendanceServiceImpl(final MeetingAttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }
    
    @Override
    public List<MeetingAttendance> getByCongregation(final Congregation congregation) {
        return attendanceRepository.findByCongregation(congregation);
    }

}
