package fr.gwombat.predicadmin.support.period;

import java.text.DecimalFormat;
import java.time.YearMonth;

final class PeriodUtils {

    public static final int PERIODE_ANNEE_MIN = 1950;
    public static final int PERIODE_ANNEE_MAX = 2999;

    public static final int PERIODE_MIN       = Integer.parseInt(PERIODE_ANNEE_MIN + "01");
    public static final int PERIODE_MAX       = Integer.parseInt(PERIODE_ANNEE_MAX + "12");

    private PeriodUtils() {
    }

    static int shiftPeriod(YearMonth yearMonthPeriod, int nbMonths) {
        yearMonthPeriod = yearMonthPeriod.plusMonths(nbMonths);
        return convertPeriodToInt(yearMonthPeriod);
    }

    static int convertPeriodToInt(YearMonth yearMonth) {
        int month = yearMonth.getMonthValue();
        int year = yearMonth.getYear();

        if (month > 12)
            month = 12;
        if (month < 1)
            month = 1;
        if (year < 100)
            year = Integer.parseInt("20" + year);
        else if (year >= 100 && year < PERIODE_ANNEE_MIN)
            return PERIODE_MIN;

        final DecimalFormat formYear = new DecimalFormat("0000");
        final DecimalFormat formMonth = new DecimalFormat("00");

        final String str = formYear.format(year) + formMonth.format(month);
        int res = Integer.valueOf(str);
        return res;
    }

    static int currentPeriodValue() {
        return convertPeriodToInt(YearMonth.now());
    }

}
