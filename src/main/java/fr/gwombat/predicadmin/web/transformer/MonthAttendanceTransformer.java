package fr.gwombat.predicadmin.web.transformer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.gwombat.predicadmin.model.MeetingAttendance;
import fr.gwombat.predicadmin.model.MonthAttendance;
import fr.gwombat.predicadmin.web.vo.MeetingAttendanceVO;
import fr.gwombat.predicadmin.web.vo.MonthAttendanceVO;
import fr.gwombat.predicadmin.web.vo.builder.MonthAttendanceVoBuilder;

@Component
public class MonthAttendanceTransformer implements ViewTransformer<MonthAttendance, MonthAttendanceVO> {
    
    private MeetingAttendanceTransformer meetingAttendanceTransformer;
    
    @Autowired
    public MonthAttendanceTransformer(final MeetingAttendanceTransformer meetingAttendanceTransformer) {
        this.meetingAttendanceTransformer = meetingAttendanceTransformer;
    }

    @Override
    public MonthAttendanceVO toViewObject(MonthAttendance entity) {
        MonthAttendanceVO attendanceVo = null;
        if(entity != null){
            MonthAttendanceVoBuilder builder = MonthAttendanceVoBuilder.begin()
                    .period(entity.getPeriod());
            
            final List<MeetingAttendance> originalAttendances = entity.getAttendances();
            if(originalAttendances != null){
                for(MeetingAttendance originalAttendance : originalAttendances){
                    final MeetingAttendanceVO meetingAttendanceVo = meetingAttendanceTransformer.toViewObject(originalAttendance);
                    builder = builder.addAttendance(meetingAttendanceVo);
                }
            }
            
            attendanceVo = builder.build();
            
        }
        return attendanceVo;
    }

}
