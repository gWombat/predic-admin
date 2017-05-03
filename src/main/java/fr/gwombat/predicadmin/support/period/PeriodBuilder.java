package fr.gwombat.predicadmin.support.period;

import java.time.YearMonth;
import java.time.temporal.TemporalAccessor;

public final class PeriodBuilder {

    private YearMonth yearMonth;

    public static PeriodBuilder create() {
        return new PeriodBuilder();
    }

    private PeriodBuilder() {
        yearMonth = YearMonth.now();
    }

    public PeriodBuilder month(int month) {
        yearMonth = yearMonth.withMonth(month);
        return this;
    }

    public PeriodBuilder year(int year) {
        yearMonth = yearMonth.withYear(year);
        return this;
    }

    public PeriodBuilder date(TemporalAccessor date) {
        yearMonth = YearMonth.from(date);
        return this;
    }

    public PeriodBuilder yearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
        return this;
    }

    public Period build() {
        return new Period(this);
    }

    YearMonth getYearMonth() {
        return yearMonth;
    }

}
