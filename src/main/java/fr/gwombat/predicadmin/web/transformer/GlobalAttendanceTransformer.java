package fr.gwombat.predicadmin.web.transformer;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import fr.gwombat.predicadmin.model.YearAttendance;
import fr.gwombat.predicadmin.web.vo.GlobalAttendanceVO;
import fr.gwombat.predicadmin.web.vo.YearAttendanceVO;
import fr.gwombat.predicadmin.web.vo.builder.GlobalAttendanceVoBuilder;

@Component
public class GlobalAttendanceTransformer implements ViewTransformer<List<YearAttendance>, GlobalAttendanceVO> {
    
    private YearAttendanceTransformer yearAttendanceTransformer;

    @Override
    public GlobalAttendanceVO toViewObject(List<YearAttendance> years) {
        GlobalAttendanceVO globalAttendance = null;
        
        if(!CollectionUtils.isEmpty(years)){
            years.sort(Comparator.comparing(YearAttendance::getTheocraticYear).reversed());
            
            GlobalAttendanceVoBuilder builder = GlobalAttendanceVoBuilder.create();
            
            for(YearAttendance year : years){
                final YearAttendanceVO yearAttendanceVo = yearAttendanceTransformer.toViewObject(year);
                builder.addAttendance(yearAttendanceVo);
            }
            
            globalAttendance = builder.build();
        }
        
        return globalAttendance;
    }

    @Autowired
    public void setYearAttendanceTransformer(YearAttendanceTransformer yearAttendanceTransformer) {
        this.yearAttendanceTransformer = yearAttendanceTransformer;
    }

}
