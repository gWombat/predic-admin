package fr.gwombat.predicadmin.web.vo;

import fr.gwombat.predicadmin.support.period.Period;
import fr.gwombat.predicadmin.web.vo.builder.MonthAttendanceVoBuilder;

public class MonthAttendanceVO {

    private final Period period;

    public MonthAttendanceVO(MonthAttendanceVoBuilder builder) {
        this.period = builder.getPeriod();
    }

    public Period getPeriod() {
        return period;
    }

}
