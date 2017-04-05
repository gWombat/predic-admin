package fr.gwombat.predicadmin.web.vo.builder;

import fr.gwombat.predicadmin.support.period.Period;

/**
 * Created by gWombat.
 *
 * @since 05/04/2017
 */
public class MonthAttendanceVoBuilder {

    private Period period;

    private MonthAttendanceVoBuilder() {
    }

    public static MonthAttendanceVoBuilder begin(){
        return new MonthAttendanceVoBuilder();
    }

    public MonthAttendanceVoBuilder period(final Period period){
        this.period = period;
        return this;
    }

    public Period getPeriod() {
        return period;
    }
}


